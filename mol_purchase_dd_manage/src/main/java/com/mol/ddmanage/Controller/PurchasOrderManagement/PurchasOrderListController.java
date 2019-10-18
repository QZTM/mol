package com.mol.ddmanage.Controller.PurchasOrderManagement;

import com.mol.ddmanage.Ben.PurchasOrderManagement.PurchasOrderListben;
import com.mol.ddmanage.Service.PurchasOrderManagement.PurchasOrderListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/PurchasOrderManagement/PurchasOrderList")
public class PurchasOrderListController
{
    @Resource
    PurchasOrderListService purchasOrderListService;
    @RequestMapping("/ShowList")
    public ArrayList<PurchasOrderListben> ShowList(@RequestParam String buy_channel_id)
    {
        return purchasOrderListService.PurchasOrderListShow(buy_channel_id);
    }
}
