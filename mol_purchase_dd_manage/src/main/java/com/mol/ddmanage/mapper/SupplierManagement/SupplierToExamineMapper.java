package com.mol.ddmanage.mapper.SupplierManagement;

import java.util.Map;

public interface SupplierToExamineMapper
{
    Map SupplierType (String pk_supplier);//查看这个供应商是什么类型
    void AgreeOrRefuse_normal (String pk_supplier,String supstate_normal,String last_update_time);//基础供应商同意或者拒绝
    void AgreeOrRefuse_strategy (String pk_supplier,String supstate_strategy,String last_update_time);//战略供应商同意或者拒绝
    void AgreeOrRefuse_single (String pk_supplier,String supstate_single,String last_update_time);//单一来源提交表单同意或者拒绝
}
