package com.mol.ddmanage.mapper.SupplierManagement;

public interface SupplierToExamineMapper
{
    void AgreeOrRefuse (String pk_supplier,String supstate_single,String last_update_time);//提交表单同意或者拒绝
}
