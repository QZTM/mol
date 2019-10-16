package com.mol.expert.mapper.newMysql.dingding.purchase;

import com.mol.expert.entity.dingding.purchaseOrder.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface BdSupplierMapper {

    String getSupplierNameById(String id);

    Supplier getSupplierById(String id);

    String  getSupplierId(@Param("map") Map<String, String> map);

}
