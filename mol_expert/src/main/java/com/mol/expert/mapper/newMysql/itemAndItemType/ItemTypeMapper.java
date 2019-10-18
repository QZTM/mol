package com.mol.expert.mapper.newMysql.itemAndItemType;

import com.mol.expert.base.BaseMapper;
import com.mol.expert.entity.itemAndItemType.ItemType;

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
