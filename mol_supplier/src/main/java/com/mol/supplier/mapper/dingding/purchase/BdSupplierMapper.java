package com.mol.supplier.mapper.dingding.purchase;

import com.mol.supplier.entity.dingding.purchaseOrder.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface BdSupplierMapper {

    String getSupplierNameById(String id);

    Supplier getSupplierById(String id);

    String  getSupplierId(@Param("map") Map<String, String> map);

}
