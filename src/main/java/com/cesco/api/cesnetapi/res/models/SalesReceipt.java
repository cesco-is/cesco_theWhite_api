package com.cesco.api.cesnetapi.res.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SalesReceipt {
    /**본부코드 */
    String hd_code;
    /**매장코드 */
    String sp_code;
    /**가맹점명 */
    String sp_name;
    /**사업자번호 */
    String biz_no;
    /**가맹점관리코드 */
    String erp_code;
    /**영업일자 */
    String sale_date;
    /**포스번호 */
    String pos_no;
    /**영수증번호 */
    String bill_no;
    /**매출구분 */
    String sale_flag;
    /**최소구분 */
    String cancel_flag;
    /**주문일시 */
    String order_datetime;
    /**결제일시 */
    String serve_datetime;
    /**원거래번호 */
    String org_sale_no;
    /**총매출액 */
    String total_amt;
    /**순매출액 */
    String sale_amt;
    /**NET매출액 */
    String net_amt;
    /**할인총액 */
    String total_dc_amt;
    /**부가세액 */
    String vat_amt;
    /**봉사료액 */
    String service_amt;
    /**현금금액 */
    String cash_amt;
    /**카드금액 */
    String card_amt;
    /**외상금액 */
    String tick_amt;
    /**포인트금액 */
    String point_amt;
    /**상품권금액 */
    String gift_amt;
    /**선불카드 금액 */
    String prepaid_card_amt;
    /**OK캐쉬백금액 */
    String ocb_amt;
    /**제휴할인 금액 */
    String corp_dc_amt;
    /**일반할인 금액 */
    String normal_dc_ant;
    /**서비스 할인 금액 */
    String service_dc_amt;
    /**쿠폰할인 금액 */
    String coupon_dc_amt;
    /**고객할인 금액 */
    String cust_dc_amt;
    /**적립포인트 */
    String cust_accum_point;
    /**사용포인트 */
    String cust_use_point;
    /**고객수 */
    String cust_cnt;
    /**교환권금액 */
    String exchange_amt;
    /**직원카드 금액 */
    String co_amt;
    /**전자화폐 금액 */
    String emoney_amt;
    /**에누리 금액 */
    String enuri_amt;
    /**프로모션할인 금액 */
    String promotion_dc_amt;
    /**고객코드 */
    String cust_no;
    /**고객카드넘버 */
    String cust_card_no;
    /** */
    String sale_sp_code;
}
