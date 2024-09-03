package com.cesco.api.cesnetapi.cm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 기본 Response
 * @since 2021-08-10
 * @author 조선호
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class CesResponse {
    
    /**
     * 헤더
     */
    CesResponseHeader header;

    /**
     * 데이터
     */
    Object body;

    /**
     * 정상일 경우 생성자
     * @param body body
     */
    public CesResponse(Object body) {
        
        this.header = new CesResponseHeader();
        this.body = body;
    }

    /**
     * 오류일 경우 생성자
     * @param errorMsg 에러메시지
     */
    public CesResponse(String errorMsg) {
        
        this.header = new CesResponseHeader(errorMsg);
        this.body = null;
    }
}
