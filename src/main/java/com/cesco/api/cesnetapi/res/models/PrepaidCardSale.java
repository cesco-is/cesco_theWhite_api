package com.cesco.api.cesnetapi.res.models;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PrepaidCardSale {
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
    /**선불카드판매총건수 */
    String prepaid_sale_cnt;
    /**선불카드판매총금액 */
    String prepaid_sale_amt;
    /**선불카드판매정상총건수 */
    String prepaid_sale_normal_cnt;
    /**선불카드판매정상총금액 */
    String prepaid_sale_normal_amt;
    /**선불카드판매취소총건수 */
    String prepaid_sale_cancel_cnt;
    /**선불카드판매취소총금액 */
    String prepaid_sale_cancel_amt;
    /**선불카드충전총건수 */
    String prepaid_charge_cnt;
    /**선불카드충전총금액 */
    String prepaid_charge_amt;
    /**선불카드충전정상총건수 */
    String prepaid_charge_normal_cnt;
    /**선불카드충전정상총금액 */
    String prepaid_charge_normal_amt;
    /**선불카드충전취소총건수 */
    String prepaid_charge_cancel_cnt;
    /**선불카드충전취소총금액 */
    String prepaid_charge_cancel_amt;
}
