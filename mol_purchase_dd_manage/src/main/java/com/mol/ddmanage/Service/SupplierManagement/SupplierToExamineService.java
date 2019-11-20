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
    public String AgreeOrRefuseLogic(String pk_supplier,String process,String statu)
    {
        if (statu.equals("1"))//基础供应商
        {
            supplierToExamineMapper.AgreeOrRefuse_normal(pk_supplier,process, DataUtil.GetNowSytemTime());
        }
        else if (statu.equals("2"))//战略供应商
        {
            supplierToExamineMapper.AgreeOrRefuse_strategy(pk_supplier,process, DataUtil.GetNowSytemTime());
        }
        else if (statu.equals("3"))//单一供应商
        {
            supplierToExamineMapper.AgreeOrRefuse_single(pk_supplier,process, DataUtil.GetNowSytemTime());
        }

        return "提交成功";
    }
}
