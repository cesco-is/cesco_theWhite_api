package com.cesco.api.cesnetapi.config;

import org.springframework.http.HttpStatus;

/**
 * ApiException
 * 
 * 전역 에러처리를 위한 데이터 구조체
 * @since   2021-07-26
 * @author  조선호
 * @version 2107.1
 */
public class ApiException extends Exception {
    
    /** httpstatus */
    private final HttpStatus status;

    /**
     * 에러처리
     * @param   status HttpStatus 상태값
     * @param   errorMsg 에러메시지
     * @see     HttpStatus
     */
    public ApiException(HttpStatus status, String errorMsg) {
        super(errorMsg);
        this.status = status;
    }

    /**
     * httpstatus 리턴
     * @return  HttpStatus
     * @see     HttpStatus
     */
    public HttpStatus getHttpStatus() {
        return this.status;
    }
}
