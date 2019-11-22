package com.mol.ddmanage.Service.SupplierManagement;

import com.mol.ddmanage.Util.DataUtil;
import com.mol.ddmanage.mapper.SupplierManagement.SupplierToExamineMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class SupplierToExamineService
{
    @Resource
    SupplierToExamineMapper supplierToExamineMapper;
    public Map AgreeOrRefuseLogic(String pk_supplier,String process,String supplier_type)
    {
        Map map=new HashMap();
        map.put("statu","提交成功");
        Map SupplierType=supplierToExamineMapper.SupplierType(pk_supplier);
        if (SupplierType!=null)
        {
            if (supplier_type.equals("1"))//基础供应商
            {
                supplierToExamineMapper.AgreeOrRefuse_normal(pk_supplier,process, DataUtil.GetNowSytemTime());
            }
            else if (supplier_type.equals("2"))//战略供应商
            {
                supplierToExamineMapper.AgreeOrRefuse_strategy(pk_supplier,process, DataUtil.GetNowSytemTime());
            }
            else if (supplier_type.equals("3"))//单一供应商
            {
                supplierToExamineMapper.AgreeOrRefuse_single(pk_supplier,process, DataUtil.GetNowSytemTime());
            }
        }
        return map;
    }
}
