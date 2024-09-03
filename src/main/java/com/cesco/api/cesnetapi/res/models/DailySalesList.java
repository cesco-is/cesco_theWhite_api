package com.cesco.api.cesnetapi.res.models;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DailySalesList {
    public List<DailySales> data;
    String ret_code;
    String s_code;
}
