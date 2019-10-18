package com.mol.ddmanage.Service.SupplierManagement;

import com.mol.ddmanage.Ben.SupplierManagement.SupplierListben;
import com.mol.ddmanage.mapper.SupplierManagement.SupplierListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SupplierListService
{
    @Resource
    SupplierListMapper supplierListMapper;
   public List<SupplierListben> SupplierListShow(String supplier_type)
    {
      List<SupplierListben> supplierListbens=supplierListMapper.SupplierListbenShow(supplier_type);
        for (int n=0;n<supplierListbens.size();n++)
        {
            supplierListbens.get(n).setNumber(String.valueOf(n));
            supplierListbens.get(n).setIndustry(supplierListbens.get(n).getIndustry_first());
        }
      return supplierListbens;
    }
}
