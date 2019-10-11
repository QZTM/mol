package com.mol.purchase.mapper.fyOracle.dingding.purchase;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StrategyOraclMapper {


    String getOraclId(String itemName);
}
