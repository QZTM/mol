package com.mol.supplier.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StrategyOraclMapper {


    String getOraclId(String itemName);
}
