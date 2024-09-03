package com.cesco.api.cesnetapi.cm.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CesResponseHeader {
    
    /**
     * 응답메시지 (고객 오픈용)
     */
    @JsonProperty("respMsg")
    String responseMessage;

    /**
     * 성공시
     */
    public CesResponseHeader() {
        
        responseMessage = "";
    }
}
