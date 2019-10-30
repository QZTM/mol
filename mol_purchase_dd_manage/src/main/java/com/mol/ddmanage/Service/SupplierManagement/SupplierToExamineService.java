package com.mol.ddmanage.Service.SupplierManagement;

import com.mol.ddmanage.Util.DataUtil;
import com.mol.ddmanage.mapper.SupplierManagement.SupplierToExamineMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SupplierToExamineService
{
    @Resource
    SupplierToExamineMapper supplierToExamineMapper;
    public String AgreeOrRefuseLogic(String pk_supplier,String supstate_single)
    {
        supplierToExamineMapper.AgreeOrRefuse(pk_supplier,supstate_single, DataUtil.GetNowSytemTime());
        return "提交成功";
    }
}
