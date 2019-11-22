package com.mol.ddmanage.Controller.SupplierManagement;

import com.mol.ddmanage.Service.SupplierManagement.SupplierToExamineService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/SupplierToExamine")
public class SupplierToExamineController
{
    @Resource
    SupplierToExamineService supplierToExamineService;
    @RequestMapping("/AgreeOrRefuse")//提交供应商审核
    public Map AgreeOrRefuse(@RequestParam String pk_supplier, @RequestParam String process, @RequestParam String supplier_type)// 供应商id 意见1 同意 4不同意 supplier_type 1基础供应商 2战略供应商 3单一供应商
    {
        return supplierToExamineService.AgreeOrRefuseLogic(pk_supplier,process,supplier_type);
    }
}
