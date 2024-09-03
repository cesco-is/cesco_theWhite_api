package com.cesco.api.cesnetapi.res.models;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SalesReceiptDtlCardDe {
    /**본부코드 */
    String hd_code;
    /**매장코드 */
    String sp_code;
    /**영업일자 */
    String sale_date;
    /**포스번호 */
    String pos_no;
    /**영수증 번호 */
    String bill_no;
    /**순번 */
    String card_slip_no;
    /**카드번호 */
    String card_no;
    /**승인금액 */
    String appr_amt;
    /**할부개월수 */
    String installment;
    /**승인번호 */
    String appr_no;
    /**승인일시 */
    String appr_datetime;
    /**승인구분 */
    String appr_flag;
    /**카드사코드 */
    String issuer_code;
    /**카드사 명 */
    String issuer_name;
    /**매입사코드 */
    String acquirer_code;
    /**매입사명 */
    String acquirer_name;
    /**제휴할인금액 */
    String corp_dc_amt;
    /**부가세 */
    String vat_amt;
    /**봉사료 */
    String service_amt;
    /**매출구분 */
    String sale_flag;
    /**가맹점 관리코드 */
    String erp_code;
    /**가맹점명 */
    String sp_name;
    /** */
    String sale_sp_code;
    /**사업자번호 */
    String biz_no;
}
