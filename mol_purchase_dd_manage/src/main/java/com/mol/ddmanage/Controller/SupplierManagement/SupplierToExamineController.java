package com.mol.ddmanage.Controller.SupplierManagement;

import com.mol.ddmanage.Service.SupplierManagement.SupplierToExamineService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/SupplierToExamine")
public class SupplierToExamineController
{
    @Resource
    SupplierToExamineService supplierToExamineService;
    @RequestMapping("/AgreeOrRefuse")//提交供应商审核
    public String AgreeOrRefuse(@RequestParam String pk_supplier,@RequestParam String supstate_single)
    {
        return supplierToExamineService.AgreeOrRefuseLogic(pk_supplier,supstate_single);
    }
}
