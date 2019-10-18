package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Ben.Office.Purchase_Grogress_list_ben;
import com.mol.ddmanage.Service.Office.Purchase_Grogress_Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/Office/Purchase_Grogress")
@RestController
public class Purchase_Grogress_controller
{
    @Resource
    Purchase_Grogress_Service purchase_grogress_service;
    @RequestMapping("/ShowList")
    public List<Purchase_Grogress_list_ben> Purchase_Grogress_ShowList()
    {
        return purchase_grogress_service.PurchaseGrogressList();
    }

}
