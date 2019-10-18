package com.mol.ddmanage.mapper.SupplierManagement;

import com.mol.ddmanage.Ben.SupplierManagement.SupplierListben;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierListMapper
{
    List<SupplierListben> SupplierListbenShow(@Param(value = "supplier_type") String supplier_type);
}
