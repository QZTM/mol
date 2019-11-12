package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Ben.Office.TimeProcessExperreCommendben;
import com.mol.ddmanage.Ben.PurchasOrderManagement.PurchasOrderinforben;
import com.mol.ddmanage.Service.Office.TimeProcessService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/TimeProcessController")//时间轴
public class TimeProcessController
{
    @Resource
    TimeProcessService timeProcessService;
    @RequestMapping("/ExperreCommend")//获取专家对供应商报价的推荐
    public ArrayList<TimeProcessExperreCommendben> ExperreCommend(@RequestParam String purchase_id, @RequestParam String supplier_id)
    {
      return timeProcessService.ExperreCommendLogic(purchase_id,supplier_id);//purchase_id,supplier_id  "1184295019067084800","1178121287646588928"
    }

    @RequestMapping("/SupperlierQuoteFinally")//审核议价 最终确定的多公司报价
    public ArrayList<ArrayList<PurchasOrderinforben>> SupperlierQuoteFinally(@RequestParam String PurchasId)
    {
        return  timeProcessService.SupperlierQuoteFinallyLogic(PurchasId);
    }

    @RequestMapping("/PurchasOver")//订单审配流程结束
    public ArrayList<ArrayList<PurchasOrderinforben>> PurchasOver(@RequestParam String PurchasId)
    {
        return  timeProcessService.PurchasOverLogic(PurchasId);
    }

}
