package com.cesco.api.cesnetapi.res.models;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SalesReceiptDtlPrepaidCard {

    /**매장코드(KICC 관리코드) */
    String sp_code;
    /**포스번호 */
    String pos_no;
    /**가맹점 관리코드*/
    String erp_code;
    /**매출 구분 */
    String sale_flag;
    /**사용금액 */
    String appr_amt;
    /**선불카드코드 */
    String prepaid_code;
    /**입력구분 */
    String wcc;
    /**승인처리구분 */
    String proc_flag;
    /**원거래 승인일자 */
    String org_appr_date;
    /**카드데이터 */
    String card_data;
    /**승인번호 */
    String appr_no;
    /**결제수단 구분 */
    String payment_flag;
    /**영업일자 */
    String sale_date;
    /**승인구분(0: EasyPOS) */
    String appr_flag;
    /**메시지 */
    String msg;
    /**선불카드 매출내역 순번 */
    String prepaid_slip_no;
    /**본부코드(KICC 관리코드) */
    String hd_code;
    /**가맹점명 */
    String sp_name;
    /**카드번호 */
    String card_no;
    /**선불카드명 */
    String prepaid_name;
    /**승인일시 */
    String appr_datetime;
    String sale_sp_code;
    /**사업자 번호 */
    String biz_no;
    /**카드길이 */
    String card_len;
    /**영수증 번호 */
    String bill_no;

}
