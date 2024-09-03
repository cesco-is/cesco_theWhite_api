package com.cesco.api.cesnetapi.res.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemListList {
    public List<ItemList> data;
    String ret_code;
}
