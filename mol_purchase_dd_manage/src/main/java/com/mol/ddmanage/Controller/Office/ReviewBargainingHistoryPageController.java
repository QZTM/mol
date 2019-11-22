package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Ben.Office.ReviewPeolpesben;
import com.mol.ddmanage.Service.Office.ReviewBargainingHistoryPageService;
import com.mol.ddmanage.config.Dingding_config;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/Office/Title_Details_contorller")//审核议价历史详情页
public class ReviewBargainingHistoryPageController
{
    @Resource
    ReviewBargainingHistoryPageService title_details_service;

    @RequestMapping("/ReviewPeolpes")//获取这个公司对一个采购订单的所有审批人,并且获取当前审批进度
    public ArrayList<ReviewPeolpesben> ReviewPeolpes(@RequestParam String purchase_id)
    {
      return title_details_service.ReviewPeolpes( purchase_id,Dingding_config.CorpId);
    }
}
