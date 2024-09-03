package com.cesco.api.cesnetapi.res.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SalesDtl {
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
    /**시간대 */
    String time_slot;
    /**요일 */
    String Sale_day;
    /**영수건수 */
    String bill_qty;
    /**총매출액 */
    String total_amt;
    /**순매출액 */
    String sale_amt;
    /**NET 매출액 */
    String net_amt;
    /**영수단가 */
    String bill_amt;
}