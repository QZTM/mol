package com.mol.supplier.mapper.newMysql.dingding.purchase;

import com.mol.base.BaseMapper;
import com.mol.supplier.entity.dingding.purchaseOrder.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface BdSupplierMapper extends BaseMapper<com.mol.supplier.entity.MicroApp.Supplier> {

    String getSupplierNameById(String id);

    Supplier getSupplierById(String id);

    String  getSupplierId(@Param("map") Map<String, String> map);

}
