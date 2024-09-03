package com.cesco.api.cesnetapi.res.models;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SalesReceiptDtlCorpDe {
    /**매장코드(KICC 관리코드) */
    String sp_code;
    /**포스번호 */
    String pos_no;
    /**가맹점 관리코드*/
    String erp_code;
    /**매출 구분 */
    String sale_flag;
    /**유효기간 */
    String valid_term;
    /**입력구분 */
    String wcc;
    /**승인처리구분 */
    String proc_flag;
    /**카드데이터 */
    String card_data;
    /**원거래 승인일자 */
    String org_appr_date;
    /**분담률 */
    String allot_rate;
    /**승인번호 */
    String appr_no;
    /**결제수단 구분 */
    String payment_flag;
    /**잔여포인트 */
    String remain_point;
    /**제휴사 코드 */
    String corp_code;
    /**영업일자 */
    String sale_date;
    /**분담구분(0: '%', 1: '원') */
    String allot_flag;
    /**제휴 할인 금액 */
    String corp_dc_amt;
    /**제휴사명 */
    String corp_name;
    /**승인구분(0: EasyPOS) */
    String appr_flag;
    /**메시지 */
    String msg;
    /**본부코드(KICC 관리코드) */
    String hd_code;
    /**가용 포인트 */
    String usable_point;
    /**가맹점명 */
    String sp_name;
    /**카드번호 */
    String card_no;
    /**순번 */
    String corp_slip_no;
    /**승인일시 */
    String appr_datetime;
    /**사용 포인트 */
    String use_point;
    /** */
    String sale_sp_code;
    /**사업자 번호 */
    String biz_no;
    /**발생 포인트 */
    String sale_point;
    /**카드길이 */
    String card_len;
    /**영수증 번호 */
    String bill_no;
}
