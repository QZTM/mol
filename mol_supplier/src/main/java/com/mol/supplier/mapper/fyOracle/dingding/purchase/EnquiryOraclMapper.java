package com.mol.supplier.mapper.fyOracle.dingding.purchase;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnquiryOraclMapper {
    String getOraclId(String itemName);
}
