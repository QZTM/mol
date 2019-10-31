package com.mol.supplier.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnquiryOraclMapper {
    String getOraclId(String itemName);
}
