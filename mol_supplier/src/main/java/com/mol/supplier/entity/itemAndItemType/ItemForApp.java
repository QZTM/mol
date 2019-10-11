package com.mol.supplier.entity.itemAndItemType;

import lombok.Data;

import java.util.List;

@Data
public class ItemForApp {

    private String name;
    private List<Item> itemList;

}
