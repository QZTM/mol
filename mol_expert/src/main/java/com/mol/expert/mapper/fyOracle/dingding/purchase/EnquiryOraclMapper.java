package com.mol.expert.mapper.fyOracle.dingding.purchase;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnquiryOraclMapper {
    String getOraclId(String itemName);
}
