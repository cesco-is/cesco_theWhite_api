package com.cesco.api.cesnetapi.res.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DailySales {
    // String s_code;
    // String other_sp_fg;
    /** 본부코드*/
    String hd_code;
    /**매장코드 */
    String sp_code;
    /**가맹점명 */
    String sp_name;
    /**사업자번호 */
    String biz_no;
    /**가맹점 관리코드 */
    String erp_code;
    /**영업일자 */
    String sale_date;
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
    /**영수건수 */
    String bill_qty;
    /**정상건수 */
    String normal_qty;
    /**정상매출액 */
    String normal_amt;
    /**반품건수 */
    String return_qty;
    /**반품 매출액 */
    String return_amt;
    /**봉사료액 */
    String service_amt;
    /**현금건수 */
    String cash_qty;
    /**현금금액 */
    String cash_amt;
    /**카드건수 */
    String card_qty;
    /**카드금액 */
    String card_amt;
    /**외상건수 */
    String tick_qty;
    /**외상금액 */
    String tick_amt;
    /**포인트건수 */
    String point_qty;
    /**포인트금액 */
    String point_amt;
    /**상품권건수 */
    String gift_qty;
    /**상품권금액 */
    String gift_amt;
    /**선불카드건수 */
    String prepaid_card_qty;
    /**선불카드금액 */
    String prepaid_card_amt;
    /**OK캐쉬백건수 */
    String ocb_qty;
    /**OK캐쉬백금액 */
    String ocb_amt;
    /**제휴할인건수 */
    String corp_dc_qty;
    /**제휴할인금액 */
    String corp_dc_amt;
    /**일반할인건수 */
    String normal_dc_qty;
    /**일반할인금액 */
    String normal_dc_ant;
    /**서비스할인건수 */
    String service_dc_qty;
    /**서비스할인금액 */
    String service_dc_amt;
    /**쿠포할인건수 */
    String coupon_dc_qty;
    /**쿠폰할인금액 */
    String coupon_dc_amt;
    /**고객할인건수 */
    String cust_dc_qty;
    /**고객할인금액 */
    String cust_dc_amt;
    /**적립포인트 */
    String cust_accum_point;
    /**사용포인트 */
    String cust_use_point;
    /**고객수 */
    String cust_cnt;
    /**중복할인건수 */
    String du_dc_qty;
    /**중복할인금액 */
    String du_dc_amt;
    /**교환권건수 */
    String exchange_qty;
    /**교환권금액 */
    String exchange_amt;
    /**직원카드검수 */
    String co_qty;
    /**직원카드금액 */
    String co_amt;
    /**전자화폐건수 */
    String emoney_qty;
    /**전자화폐금액 */
    String emoney_amt;
    /**에누리건수 */
    String enuri_qty;
    /**에누리금액 */
    String enuri_amt;
    /**현금취소건수 */
    String cash_cancel_qty;
    /**현금취소금액 */
    String cash_cancel_amt;
    /**카드취소건수 */
    String card_cancel_qty;
    /**카드취소금액 */
    String card_cancel_amt;
    /**외상취소건수 */
    String tick_cancel_qty;
    /**외상취소금액 */
    String tick_cancel_amt;
    /**포인트취소건수 */
    String point_cancel_qty;
    /**포인트취소금액 */
    String point_cancel_amt;
    /**상품권취소건수 */
    String gift_cancel_qty;
    /**상품권취소금액 */
    String gift_cancel_amt;
    /**선불카드취소건수 */
    String prepaid_cancel_qty;
    /**선불카드취소금액 */
    String prepaid_cancel_amt;
    /**OK캐쉬백취소건수 */
    String ocb_cancel_qty;
    /**OK캐쉬백취소금액 */
    String ocb_cancel_amt;
    /**교환권취소건수 */
    String exchange_cancel_qty;
    /**교환권취소금액 */
    String exchange_cancel_amt;
    /**직원카드취소건수 */
    String co_cancel_qty;
    /**직원카드취소금액 */
    String co_cancel_amt;
    /**전자화폐취소건수 */
    String emoney_cancel_qty;
    /**전자화폐취소금액 */
    String emoney_cancel_amt;
    /**프로모션 할인건수 */
    String promotion_dc_qty;
    /**프로모션 할인금액 */
    String promotion_dc_amt;
}
