def lastTagName = ""
def dockerImage
def regName = "cesdocker.cesco.biz:12000"
def imageName = regName + "/api/imagename"
def regUri = "http://" + regName

pipeline {
    agent any
    stages{
        stage('Git Clone Repository') {
            steps {
                // git clone
                git (
                    branch: 'master',
                    credentialsId: 'git-melong0124-1',
                    url: 'git-url'
                )
                
                // tag settings
                script {
                    lastTagName = sh(returnStdout:  true, script: "git tag --sort=-creatordate | head -n 1").trim()
                }
                
                echo "last tag name : ${lastTagName}"
            }
        }
        
        stage('Container Image Build & Push') {
            steps {
                withDockerRegistry([url: regUri, credentialsId: "cesdocker-hub"]) {
                    // docker build
                    sh "docker build . -t ${imageName}:${lastTagName}"
                    
                    // docker latest Tag
                    sh "docker tag ${imageName}:${lastTagName} ${imageName}:latest"
                    
                    // docker image push
                    sh "docker push ${imageName}:${lastTagName}"
                    sh "docker push ${imageName}:latest"
                    
                    // docker image delete
                    sh "docker rmi ${imageName}:${lastTagName}"
                    sh "docker rmi ${imageName}:latest"
                }
            }
        }
        
        stage('Remote Server Deploy') {
            steps([$class: 'BapSshPromotionPublisherPlugin']) {
                sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                        sshPublisherDesc(
                            configName: "cesnet2-server",
                            verbose: true,
                            transfers: [
                                sshTransfer(
                                        cleanRemote: true,
                                        excludes: '',
                                        execCommand: '',
                                        execTimeout: 120000,
                                        flatten: false,
                                        makeEmptyDirs: true,
                                        noDefaultExcludes: false,
                                        patternSeparator: '[, ]+',
                                        remoteDirectory: '/deployname',
                                        remoteDirectorySDF: false,
                                        removePrefix: 'ci/',
                                        sourceFiles: 'ci/docker-compose.production.yaml'),
                                        
                                        // Server Deploy
                                        sshTransfer(execCommand: """
                                            cd /docker-data/jenkins-data/deployname
                                            docker stack deploy -c ./docker-compose.production.yaml deployname --with-registry-auth
                                        """)
                            ]
                        )
                    ]
                )
            }
        }
    }
}
