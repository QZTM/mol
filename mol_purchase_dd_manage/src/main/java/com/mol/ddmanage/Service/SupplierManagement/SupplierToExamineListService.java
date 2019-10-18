package com.mol.ddmanage.Service.SupplierManagement;

import com.mol.ddmanage.Ben.SupplierManagement.SupplierToExamineListben;
import com.mol.ddmanage.mapper.SupplierManagement.SupplierToExamineListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierToExamineListService
{
    @Resource
    SupplierToExamineListMapper supplierToExamineListMapper;
    public List<SupplierToExamineListben>SupplierToExamineListbenShow(String supplier_type)
    {
       List<SupplierToExamineListben> supplierToExamineListbenss= new  ArrayList<>();
       for (int n=0;n<3;n++)
       {
           List<SupplierToExamineListben> supplierToExamineListbens=supplierToExamineListMapper.SupplierToExamineListbenShow(String.valueOf(n),supplier_type);
           for (SupplierToExamineListben supplier :supplierToExamineListbens)
           {
               supplierToExamineListbenss.add(supplier);
           }
       }

       for (int n=0;n<supplierToExamineListbenss.size();n++)//添加序号
       {
           supplierToExamineListbenss.get(n).setNumber(String.valueOf(n));
           supplierToExamineListbenss.get(n).setIndustry(supplierToExamineListbenss.get(n).getIndustry_first());
       }
        return supplierToExamineListbenss;
    }
}
