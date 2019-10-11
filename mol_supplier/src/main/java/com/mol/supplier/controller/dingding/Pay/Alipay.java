package com.mol.supplier.controller.dingding.Pay;

import com.mol.supplier.service.dingding.Pay.AlipayService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("Alipay")
@RestController
public class Alipay {
    @Resource
    AlipayService alipayService;
    //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
    @RequestMapping("Pay_app")
    @ResponseBody
    public Map Pay_deposit()//手机支付押金
    {
        Map map=new HashMap(1);
        map=alipayService.Pay_deposit("1234");
        return map;
    }

    @RequestMapping("Check_Alipay")
    @ResponseBody
    public void Check_Alipay(@RequestParam Map map)//查询是否支付成功
    {
        boolean bool= alipayService.Check_Alipay_success(map.get("order_id").toString(),map.get("supplier_id").toString());
    }

    @RequestMapping("Return_money")
    @ResponseBody
    public Map Return_money()//退还押金
    {
        Map map=new HashMap(1);
        map=alipayService.Return_money("20190731095223687");
        return map;
    }

    @RequestMapping("Check_Return_money")
    @ResponseBody
    public void Check_Return_money()//查询是否退款
    {
        alipayService.Check_Return_money_success("20190731095223687");
    }

}
