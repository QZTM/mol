package com.mol.purchase.mapper.newMysql.dingding.purchase;

import com.mol.base.BaseMapper;
import com.mol.purchase.entity.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface BdSupplierMapper extends BaseMapper<Supplier> {

    String getSupplierNameById(String id);

    Supplier getSupplierById(String id);

    String  getSupplierId(@Param("map") Map<String, String> map);

}
