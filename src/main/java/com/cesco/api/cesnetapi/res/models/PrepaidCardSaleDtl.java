package com.cesco.api.cesnetapi.res.models;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PrepaidCardSaleDtl {
    /**영업일자 */
    String sale_date;
    /**포스번호 */
    String pos_no;
    /**순번 */
    String seq;
    /**카드번호 */
    String appr_card_no;
    /**선불카드명 */
    String prepaid_name;
    /**카드데이터 */
    String card_no;
    /**판매수량 */
    String qty;
    /**판매총액 */
    String sale_amt;
    /**판매구분 */
    String sale_flag;
    /**결제구분(0:현금, 1:카드) */
    String payment_flag;
    /**승인번호(현금인 경우 ‘-‘표시) */
    String appr_no;
}