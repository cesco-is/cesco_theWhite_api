package com.cesco.api.cesnetapi.res.models;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SalesReceiptDtlMappDe {
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
    String detail_no;
    /**상품코드 */
    String item_code;
    /**상품명 */
    String item_name;
    /**매출수량 */
    String qty;
    /**매출금액 */
    String total_amt;
    /**순매출액 */
    String sale_amt;
    /**NET 매출액 */
    String net_amt;
    /**할인총액 */
    String total_dc_amt;
    /**부가세액 */
    String vat_amt;
    /**포인트 금액 */
    String point_amt;
    /**제휴 할인 금액 */
    String corp_dc_amt;
    /**일반 할인 금액 */
    String normal_dc_amt;
    /**서비스 할인 금액 */
    String service_dc_amt;
    /**쿠폰 할인 금액 */
    String coupon_dc_amt;
    /**고객 할인 금액 */
    String cust_dc_amt;
    /**프로모션 할인 금액 */
    String promotion_dc_amt;
    /**상품 매출 포인트 */
    String cust_accum_point;
    /**상품 사용 포인트 */
    String cust_use_point;
    /**매출 구분 */
    String sale_flag;
    /**상품 주문 시작 */
    String order_datetime;
    /**상품 제공 시작 */
    String serve_datetime;
    /**상품 판매가 */
    String item_price;
    /**부가 메뉴 포함 여부 */
    String sub_menu_type;
    /**부가 메뉴 여부 */
    String sub_menu_flag;
    /**부가 메뉴 부모 순번 */
    String parent_detail_no;
    /**부가 메뉴 수량 */
    String sub_menu_count;
    /**현금 금액 */
    String cash_amt;
    /**카드 금액 */
    String card_amt;
    /**외상 금액 */
    String tick_amt;
    /**상품권 금액 */
    String gift_amt;
    /**선불카드 금액 */
    String prepaid_card_amt;
    /**OK캐쉬백 금액 */
    String ocb_amt;
    /**직원 카드 금액 */
    String co_amt;
    /**교환권 금액 */
    String exchange_amt;
    /**전자화폐 금액 */
    String emoney_amt;
    /**에누리 금액 */
    String enuri_amt;
    /**수수료 금액 */
    String charge_amt;
    /**과세 유무 */
    String item_tax_flag;
    /**가맹점 관리 코드 */
    String erp_code;
    /** */
    String erp_item_code;
    /** */
    String sale_sp_code;
    /**바코드 번호 */
    String barcode;
    /**가맹점명 */
    String sp_name;
    /**사업자 번호 */
    String biz_no;
}
