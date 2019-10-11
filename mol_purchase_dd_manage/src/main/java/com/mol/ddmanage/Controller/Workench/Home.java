package com.mol.ddmanage.Controller.Workench;

import com.mol.ddmanage.Ben.Purchase_track_ben;
import com.mol.ddmanage.Ben.Supplier_Review_ben;
import com.mol.ddmanage.Service.Get_Purchase_inforService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/Purchase_infor")
public class Home
{
    @Resource
    Get_Purchase_inforService get_purchase_inforService;
    @RequestMapping("/Purchase_bar_infor")
  public Map Purchase_bar_infor(@RequestParam String number)//首页订单状态栏信息
  {
      return get_purchase_inforService.Purchase_bar_infor_service(number);
  }
  @RequestMapping("/Purchase_track")//获取采购订单实时跟踪信息
    public ArrayList<Purchase_track_ben> Purchase_track()
  {
      return get_purchase_inforService.Purchase_track_Service();
  }

  @RequestMapping("/Get_Supplier_Review") //获取供应商审核进程
    public ArrayList<Supplier_Review_ben> Get_Supplier_Review()
  {
      get_purchase_inforService.Get_this_month_Supplier_number();
      return get_purchase_inforService.Get_Supplier_Review_Service();
  }
}
