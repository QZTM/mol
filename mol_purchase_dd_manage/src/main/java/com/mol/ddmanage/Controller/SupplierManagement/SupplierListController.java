package com.mol.ddmanage.Controller.SupplierManagement;

import com.mol.ddmanage.Ben.SupplierManagement.SupplierListben;
import com.mol.ddmanage.Service.SupplierManagement.SupplierListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/SupplierMangement/SupplierList")
public class SupplierListController
{
    @Resource
    SupplierListService supplierListService;
    @RequestMapping("/ShowList")
    public List<SupplierListben> ShowList(@RequestParam String supplier_type)//遍历出供应商 //supplier_type 0全部供应商 1基础供应商 2战略供应商 3单一供应商
    {
      return supplierListService.SupplierListShow(supplier_type);
    }
}
