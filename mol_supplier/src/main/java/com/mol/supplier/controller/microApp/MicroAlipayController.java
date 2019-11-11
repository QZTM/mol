package com.mol.supplier.controller.microApp;

import com.mol.pay.Alipay;
import com.mol.pay.entity.AlipayCreateInfo;
import com.mol.pay.entity.AlipayCreateInfoBuilder;
import com.mol.supplier.config.Constant;
import com.mol.supplier.entity.dingding.Pay.PuiSupplierDeposit;
import com.mol.supplier.mapper.dingding.Pay.PayMapper;
import com.mol.supplier.service.microApp.MicroAlipayService;
import com.mol.supplier.service.microApp.MicroUserService;
import entity.ServiceResult;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import util.TimeUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 处理前端页面请求的支付宝支付
 */
@RequestMapping("/pay/alipay")
@Controller
@Log
public class MicroAlipayController {


    @Autowired
    private MicroAlipayService microAlipayService;

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private MicroUserService microUserService;

    private static final String payCallBackUrl = Constant.domain+"/pay/alipay/callback";


    @RequestMapping("/getCreateInfo")
    @ResponseBody
    public ServiceResult getAlipayInfo(@RequestParam Map paramap, HttpServletRequest request){
        System.out.println(paramap.toString());
        AlipayCreateInfo createPayInfo = AlipayCreateInfoBuilder.getBuilder().builder("申请成为战略供应商服务费", "申请成为战略供应商", TimeUtil.getNow(TimeUtil.payOrderFormat) + "", "5m", "", "0.01");
        createPayInfo.setCallbackUrl(payCallBackUrl);
        try {
            Map payInfoMap = microAlipayService.getAlipayInfo(createPayInfo).get();
            log.info("/getCreateInfo:"+payInfoMap.get(Alipay.MAPKEY_PAYINFO));
            return ServiceResult.success((String)payInfoMap.get(Alipay.MAPKEY_OUTTRADENO),(String)payInfoMap.get(Alipay.MAPKEY_PAYINFO));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据自定义订单id获取支付状态，(走的是阿里的查询订单支付状态接口)
     * @param orderid           自定义支付订单id
     * @return
     */
    @RequestMapping("/getOrderStatus")
    @ResponseBody
    public ServiceResult getOrderStatus(String orderid){
        boolean ifSuccess = false;
        try {
            ifSuccess = microAlipayService.getOrderStatus(orderid).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(ifSuccess){
            return ServiceResult.successMsg("支付成功");
        }
        return ServiceResult.failureMsg("支付失败");
    }

    /**
     * 根据自定义订单id获取支付状态，（走的是查询本地数据库的订单状态）
     * @param orderid           自定义支付订单id
     * @return
     */
    @RequestMapping("/getOrderStatus2")
    @ResponseBody
    public ServiceResult getOrderStatus2(String orderid){
        Example example = new Example(PuiSupplierDeposit.class);
        example.and().andEqualTo("orderId",orderid);
        PuiSupplierDeposit puiSupplierDeposit = payMapper.selectOneByExample(example);
        if(puiSupplierDeposit != null && puiSupplierDeposit.getStatus() == PuiSupplierDeposit.ORDER_STATUS_SUCCESS){
            return ServiceResult.successMsg("支付成功");
        }
        return ServiceResult.failureMsg("支付失败");
    }


    @RequestMapping("/getJsapiAuth")
    public ServiceResult<Map> getDDJsapiAuthInfo(HttpServletRequest request){
        Map authInfoMap = new HashMap();

        return ServiceResult.success("获取成功",authInfoMap);
    }


    @RequestMapping(value = "/callback",method = RequestMethod.POST)
    @ResponseBody
    public String payCallback(@RequestParam Map map, HttpSession session){
        System.out.println(map.toString());

        String orderStatus = (String)map.get("trade_status");
        String orderId = (String)map.get("out_trade_no");
        //订单支付成功事件：
        if("TRADE_SUCCESS".equals(orderStatus)){
            //根据自定义id去数据库查询，如有有数据则更改状态，如果没有这新插入数据
            log.info("支付宝支付回调事件——支付成功--orderId:"+orderId);
            PuiSupplierDeposit puiSupplierDeposit = new PuiSupplierDeposit();
            puiSupplierDeposit.setOrderId(orderId);
            puiSupplierDeposit.setStatus(PuiSupplierDeposit.ORDER_STATUS_SUCCESS);
            puiSupplierDeposit.setTradeNo((String)map.get("trade_no"));
            PuiSupplierDeposit puiSupplierDepositGet = payMapper.selectByPrimaryKey(puiSupplierDeposit);
            if(puiSupplierDepositGet == null){
                puiSupplierDeposit.setMoney((String)map.get("buyer_pay_amount"));
                puiSupplierDeposit.setPayType(PuiSupplierDeposit.ORDER_PAY_TYPE_ALIPAY);
                puiSupplierDeposit.setCreadTime(TimeUtil.getNowDateTime());
                puiSupplierDeposit.setReturnMoney("0");
                puiSupplierDeposit.setSupplierId(microUserService.getSupplierFromSession(session).getPkSupplier());
                puiSupplierDeposit.setPayFor(PuiSupplierDeposit.ORDER_PAY_FOR_STRATEGY_SUPPLIER_SERVICE);
                payMapper.insertSelective(puiSupplierDeposit);
            }else{
                payMapper.updateByPrimaryKeySelective(puiSupplierDeposit);
            }
        }

        System.out.println("success");
        return "success";
    }

}
