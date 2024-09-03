package com.cesco.api.cesnetapi.res.models;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class FranchieseList {
    public List<Franchises> data;
    String ret_code;
}
