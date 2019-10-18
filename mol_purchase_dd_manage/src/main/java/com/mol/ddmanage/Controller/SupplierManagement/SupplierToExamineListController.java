package com.mol.ddmanage.Controller.SupplierManagement;

import com.mol.ddmanage.Ben.SupplierManagement.SupplierToExamineListben;
import com.mol.ddmanage.Service.SupplierManagement.SupplierToExamineListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/SupplierMangement/SupplierToExamineList")
public class SupplierToExamineListController
{
    @Resource
    SupplierToExamineListService supplierToExamineListService;
    @RequestMapping("/ShowList")
    public List<SupplierToExamineListben> ShowList(@RequestParam String supplier_type)//遍历供应商审核 2代表审核中 4代表审核未通过
    {
        return supplierToExamineListService.SupplierToExamineListbenShow(supplier_type);
    }
}
