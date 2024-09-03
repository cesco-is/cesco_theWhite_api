package com.cesco.api.cesnetapi.res.models;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SalesReceiptDtlCashDe {
    /**본부 코드 */
    String hd_code;
    /**매장 코드 */
    String sp_code;
    /**사업자번호 */
    String biz_no;
    /**가맹점 관리코드 */
    String erp_code;
    /**영업일자 */
    String sale_date;
    /**포스번호 */
    String pos_no;
    /**영수증 번호 */
    String bill_no;
    /**순번 */
    String cash_slip_no;
    /**인식번호 */
    String identity_no;
    /**인식번호 형태 */
    String identity_type;
    /**거래자 구분 */
    String trade_flag;
    /**거래자 구분명 */
    String trade_flag_nm;
    /**가맹점명 */
    String sp_name;
    /** */
    String sale_sp_code;
    /**승인금액 */
    String appr_amt;
    /**승인구분 */
    String appr_flag;
    /**승인번호 */
    String appr_no;
    /**승인일시 */
    String appr_datetime;
    /**부가세 */
    String vat_amt;
    /**봉사료 */
    String service_amt;
    /**매출구분 */
    String sale_flag;
}
