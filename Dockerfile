###################################################
# Gradle Build (cache)
###################################################
FROM gradle:7.1.1-jdk11 AS cache

WORKDIR /app
ENV GRADLE_USER_HOME /cache
COPY build.gradle settings.gradle ./
# 빌드가 실패함. 소스가 없으므로
RUN gradle build --no-daemon || return 0

###################################################
# Gradle Build (build)
###################################################
FROM gradle:7.1.1-jdk11 AS build

WORKDIR /app
COPY --from=cache /cache /home/gradle/.gradle
COPY . .
RUN gradle build --no-daemon -i

###################################################
# Alpine Linux with OpenJDK JRE Spring boot
###################################################
FROM azul/zulu-openjdk-alpine:11-jre

# curl (헬스체크용) tzdata (시간대 맞추기용) 설치
RUN apk add --update curl tzdata bash
RUN apk --no-cache add unzip

# jennifer agent 설정
ENV AGENT_VERSION=5.4.2.3
ENV AGENT_HOME=/opt/jennifer/agent.java
ENV AGENT_CONF_FILE=${AGENT_HOME}/conf/jennifer.conf

COPY ci/jennifer-agent/jennifer-agent-java-${AGENT_VERSION}.zip /
RUN unzip -oq /jennifer-agent-java-${AGENT_VERSION}.zip -d /opt/jennifer
COPY ci/jennifer-agent/jennifer.conf ${AGENT_CONF_FILE}
COPY ci/jennifer-agent/jennifer.impl.custom-1.0.1.jar ${AGENT_HOME}

# 시간대 맞춰주기
RUN cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo "Asia/Seoul" > /etc/timezone

# 빌드 스테이지에서 있던 jar 파일을 복사 한다.
COPY --from=build /app/build/libs/app.jar /app.jar

# 헬스 체크 설정
HEALTHCHECK \
 --interval=10s \
 --timeout=10s \
 --start-period=5s \
 --retries=10 \
 CMD curl --fail --silent --url http://localhost:8080/health | grep true || exit 1

# 노출 포트 설정
EXPOSE 8080

COPY ./ci/entrypoint.sh /usr/local/bin/
RUN ln -s usr/local/bin/entrypoint.sh / # backwards compat

ENTRYPOINT [ "bash", "entrypoint.sh" ]