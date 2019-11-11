package com.mol.supplier.mapper.itemAndItemType;

import com.mol.base.BaseMapper;
import com.mol.supplier.entity.itemAndItemType.ItemType;

import java.util.List;
import java.util.Map;

public interface ItemTypeMapper extends BaseMapper<ItemType> {

    List<ItemType> selectItemTypeList();
    List<ItemType> selectItemTypeListOfFirst();
    List<ItemType> getItemTypeByParentId(Map paraMap);
    ItemType getItemTypeById(Map paraMap);
    ItemType getItemTypeByMap(Map paraMap);
    String getItemTypeNameByKey(String key);



}
