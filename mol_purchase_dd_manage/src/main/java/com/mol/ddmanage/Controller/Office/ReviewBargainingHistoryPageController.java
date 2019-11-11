package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Ben.Office.ReviewPeolpesben;
import com.mol.ddmanage.Service.Office.ReviewBargainingHistoryPageService;
import com.mol.ddmanage.config.Dingding_config;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/Office/Title_Details_contorller")//审核议价历史详情页
public class ReviewBargainingHistoryPageController
{
    @Resource
    ReviewBargainingHistoryPageService title_details_service;
/*    @RequestMapping("/MaterialList")
    public List<Title_Details_Material> MaterialList(@RequestParam String purchase_id)
    {
        List<Title_Details_Material> title_details_materials=title_details_service.MaterialListService(purchase_id);
        return title_details_materials;
    }

    @RequestMapping("/SuppelierQuote")//供应商报价
    public List<Title_Details_suppelier_quote_ben> SuppelierQuote(@RequestParam String purchase_id)
    {
        List<Title_Details_suppelier_quote_ben> title_details_suppelier_quote_bens=title_details_service.SuppelierquoteService(purchase_id);
        return title_details_suppelier_quote_bens;
    }*/

    @RequestMapping("/ReviewPeolpes")//获取这个公司对一个采购订单的所有审批人,并且获取当前审批进度
    public ArrayList<ReviewPeolpesben> ReviewPeolpes(/*@RequestParam String purchase_id*/)
    {
      return title_details_service.ReviewPeolpes( Dingding_config.CorpId);
    }
}
