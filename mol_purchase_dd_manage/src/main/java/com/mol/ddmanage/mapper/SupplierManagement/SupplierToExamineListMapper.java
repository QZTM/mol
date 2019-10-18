package com.mol.ddmanage.mapper.SupplierManagement;

import com.mol.ddmanage.Ben.SupplierManagement.SupplierToExamineListben;

import java.util.List;

public interface SupplierToExamineListMapper
{
    List<SupplierToExamineListben> SupplierToExamineListbenShow(String n,String supplier_type);
}
