package com.cesco.api.cesnetapi.res.models;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PrepaidCardSaleListDtlList {
    public List<PrepaidCardSaleDtl> saleDetailData;
    String ret_code;
}
