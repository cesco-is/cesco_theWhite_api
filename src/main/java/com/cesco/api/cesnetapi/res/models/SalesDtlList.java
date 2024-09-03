package com.cesco.api.cesnetapi.res.models;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SalesDtlList {
    public List<SalesDtl> data;
    String ret_code;
}
