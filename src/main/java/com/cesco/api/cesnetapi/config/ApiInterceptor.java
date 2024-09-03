package com.cesco.api.cesnetapi.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cesco.api.cesnetapi.config.models.CescoRequestHeader;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * ApiInterceptor
 * 
 * HandlerInterceptor 이용하여 request 가 실행되기 전에 값을 체크하고 
 * 로그를 남김
 * @since   2021-07-26
 * @author  조선호
 * @version 2107.1
 */
@Slf4j
public class ApiInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        // 헤더 정보 셋팅
        CescoRequestHeader cescoHeader = new CescoRequestHeader(
                request.getHeader("sys-id"), 
                request.getHeader("men-id"), 
                request.getHeader("fun-id"), 
                request.getHeader("us-id"), 
                request.getHeader("cl-ip"), 
                request.getHeader("tran-id"));
        String requestURI = String.format("(%s) %s", request.getMethod(), request.getRequestURI()); // URI
        String queryStr = "";

        // query string 있을 경우
        if (request.getQueryString() != null) {
            queryStr = request.getQueryString();
        }

        // 헤더 메시지 처리
        String headerMessage = String.format("(%s)", cescoHeader.toString());

        // 전체 메시지
        String message = "";

        if (queryStr.length() > 0) {
            message += String.format("'QueryString': '%s'", queryStr);
        }

        // 헤더 메시지
        if (headerMessage.length() > 0) {
            message += headerMessage;
        }

        // logger 남기기
        log.debug("{} > {}", requestURI, message);
        
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
