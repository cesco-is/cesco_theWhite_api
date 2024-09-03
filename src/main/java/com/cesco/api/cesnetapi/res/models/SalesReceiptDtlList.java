package com.cesco.api.cesnetapi.res.models;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SalesReceiptDtlList {
    /**일별영수증 상세 매출 전송 카운트 */
    int header_cnt;
    int detail_cnt;
    int cash_cnt;
    int card_cnt;
    /** 일별영수증 상세 매출 전송 */
    public List<SalesReceiptDtlDe> sale_header;
    /** 일별영수증 상세 매출 세부내역 전송 */
    public List<SalesReceiptDtlMappDe> sale_detail;
    /** 일별영수증 상세 매출 현금내역 전송 */
    public List<SalesReceiptDtlCashDe> cash_slip;
    /** 일별영수증 상세 매출 카드내역 전송 */
    public List<SalesReceiptDtlCardDe> card_slip;
    
    public List<SalesReceiptDtlCorpDe> corp_slip;

    public List<SalesReceiptDtlPrepaidCard> prepaid_slip;
    String ret_code;
}
