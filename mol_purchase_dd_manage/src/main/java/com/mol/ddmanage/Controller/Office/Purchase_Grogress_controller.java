package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Ben.Office.Purchase_Grogress_list_ben;
import com.mol.ddmanage.Service.Office.Purchase_Grogress_Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/Office/Purchase_Grogress")
@RestController
public class Purchase_Grogress_controller
{
    @Resource
    Purchase_Grogress_Service purchase_grogress_service;
    @RequestMapping("/ShowList")//采购进度列表
    public List<Purchase_Grogress_list_ben> Purchase_Grogress_ShowList(@RequestParam String time1,@RequestParam String time2,@RequestParam String status)
    {
        return purchase_grogress_service.PurchaseGrogressList(time1,time2,status);
    }

}
