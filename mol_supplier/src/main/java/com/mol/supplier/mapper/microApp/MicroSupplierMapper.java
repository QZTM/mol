package com.mol.supplier.mapper.microApp;

import com.mol.base.BaseMapper;
import com.mol.supplier.entity.MicroApp.Supplier;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MicroSupplierMapper extends BaseMapper<Supplier> {

    public Integer getVersionByPkSupplier(String pkSupplier);


}
