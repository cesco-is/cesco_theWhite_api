package com.cesco.api.cesnetapi.config.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * CESCO HEADER
 * @author 조선호
 * @since 2021-08-05
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class CescoRequestHeader {
    
    /** 시스템 ID */
    String systemId;
    /** 메뉴 ID */
    String menuId;
    /** 기능 ID */
    String functionId;
    /** 사용자 식별 ID */
    String userId;
    /** 사용자 IP */
    String userIpAddress;
    /** 트랜잭션 ID */
    String transactionId;
}
