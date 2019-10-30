package com.mol.ddmanage.Service.SupplierManagement;

import com.mol.ddmanage.Ben.SupplierManagement.SetSupplierinforben;
import com.mol.ddmanage.mapper.SupplierManagement.SetSupplierinforMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SetSupplierinforService
{
    @Resource
    SetSupplierinforMapper setSupplierinforMapper;
    public SetSupplierinforben LandingSupplierinforLogic(String pk_supplier)//加载供应商信息处理
    {
        SetSupplierinforben setSupplierinforben=setSupplierinforMapper.LandingSupplierinforMapper(pk_supplier);
        if (setSupplierinforben.getSupprop()!=null)
        {
            if (setSupplierinforben.getSupprop().equals("0"))
            {
                setSupplierinforben.setSupprop("外部单位");
            }
            else if (setSupplierinforben.getSupprop().equals("1"))
            {
                setSupplierinforben.setSupprop("内部单位");
            }
        }
        if (setSupplierinforben.getMoney()==null)
        {
            setSupplierinforben.setMoney("0");
        }


        return setSupplierinforben;
    }
}
