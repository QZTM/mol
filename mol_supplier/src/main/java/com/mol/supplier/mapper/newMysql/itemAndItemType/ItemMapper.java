package com.mol.supplier.mapper.newMysql.itemAndItemType;

import com.mol.base.BaseMapper;
import com.mol.supplier.entity.itemAndItemType.Item;

import java.util.List;
import java.util.Map;

public interface ItemMapper extends BaseMapper<Item> {

    List<Item> selectItemList();

    List<Item> selectItemsByTypeId(Map paramMap);

    //List<PurchaseOrderDetail> Historical_price(String id,String Data_last_year);
}
