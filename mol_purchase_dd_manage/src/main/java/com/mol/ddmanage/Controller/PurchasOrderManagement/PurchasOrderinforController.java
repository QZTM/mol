package com.mol.ddmanage.Controller.PurchasOrderManagement;


import com.mol.ddmanage.Ben.PurchasOrderManagement.PlannedPurchaseben;
import com.mol.ddmanage.Ben.PurchasOrderManagement.PurchasOrderinforben;
import com.mol.ddmanage.Service.PurchasOrderManagement.PurchasOrderinforService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/PurchasOrderinfor")
public class PurchasOrderinforController//订单详情信息
{
    @Resource
    PurchasOrderinforService purchasOrderinforService;
    @RequestMapping("/PlannedPurchaseben")//预计订单明细
    public ArrayList<PlannedPurchaseben> plannedPurchasebens(@RequestParam String PurchasId)
    {
        return   purchasOrderinforService.plannedPurchasebensLogic(PurchasId);
    }

    @RequestMapping("/OrderDetailedinfor")//供应商报价明细
    public ArrayList<ArrayList<PurchasOrderinforben>> OrderDetailedinfor(@RequestParam String PurchasId)
    {
     return  purchasOrderinforService.OrderDetailedinforLogic(PurchasId);
    }


}
