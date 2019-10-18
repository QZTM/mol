package com.mol.expert.mapper.newMysql.itemAndItemType;

import com.mol.expert.base.BaseMapper;
import com.mol.expert.entity.itemAndItemType.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {

    List<Item> selectItemList();

    List<Item> selectItemsByTypeId(Map paramMap);

    //List<PurchaseOrderDetail> Historical_price(String id,String Data_last_year);
}
