package com.mol.supplier.service.microApp;

import com.alipay.api.AlipayApiException;
import com.mol.pay.Alipay;
import com.mol.pay.entity.AlipayCreateInfo;
import com.mol.supplier.entity.dingding.Pay.PuiSupplierDeposit;
import com.mol.supplier.mapper.dingding.Pay.PayMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import tk.mybatis.mapper.entity.Example;
import util.TimeUtil;

import java.util.Map;

@Service
@Log
public class MicroAlipayService {

    @Autowired
    private PayMapper payMapper;


    /**
     * 异步调用支付宝的接口获取支付信息主体
     * @param info
     * @return
     */
    @Async
    public ListenableFuture<Map> getAlipayInfo(AlipayCreateInfo info){

        Map payInfo = null;
        try {
            payInfo = Alipay.getPayInfo(info);
        } catch (AlipayApiException e) {
            log.warning("获取创建支付宝支付信息时出错！"+e.getMessage());
            e.printStackTrace();
        }
        return new AsyncResult<>(payInfo);
    }


    /**
     * 异步调用支付宝的接口查询订单状态
     * @param orderid           自定义的订单id
     * @return
     */
    @Async
    public ListenableFuture<Boolean> getOrderStatus(String orderid){
        boolean ifSuccess = Alipay.ifOrderSuccess(orderid);
        return new AsyncResult<>(ifSuccess);
    }

    /**
     * 保存支付订单
     * @param createPayInfo         payinfo
     * @param supplierid            付款方id
     * @return
     */
    public int savePayOrder(AlipayCreateInfo createPayInfo,String supplierid,String payType,String payFor){
        log.info("MicroAlipayService.....savePayOrder....");
        PuiSupplierDeposit puiSupplierDeposit = this.changeCreatePayInfo2PuiSupplierDeposit(createPayInfo);
        puiSupplierDeposit.setSupplierId(supplierid);
        puiSupplierDeposit.setCreadTime(TimeUtil.getNowDateTime());
        puiSupplierDeposit.setReturnMoney("0");
        puiSupplierDeposit.setPayType(payType);
        puiSupplierDeposit.setPayFor(payFor);
        puiSupplierDeposit.setStatus(PuiSupplierDeposit.ORDER_STATUS_UNPAID_UNEXPIRED);
        //验证支付订单：
        Example example = new Example(PuiSupplierDeposit.class);
        example.and().andEqualTo("orderId",puiSupplierDeposit.getOrderId());
        PuiSupplierDeposit puiSupplierDeposit1 = payMapper.selectOneByExample(example);
        if(puiSupplierDeposit1 != null){
            throw new RuntimeException("保存支付宝要支付的订单信息是出错！");
        }
        return payMapper.insertSelective(puiSupplierDeposit);
    }


    public PuiSupplierDeposit changeCreatePayInfo2PuiSupplierDeposit(AlipayCreateInfo info){
        PuiSupplierDeposit puiSupplierDeposit = new PuiSupplierDeposit();

        if(info.getOutTradeNo() != null){
            puiSupplierDeposit.setOrderId(info.getOutTradeNo());
        }

        if(info.getBody()!=null){
            puiSupplierDeposit.setItemName(info.getBody());
        }

        if(info.getSubject()!=null){
            puiSupplierDeposit.setItemSubject(info.getSubject());
        }

        if(info.getTotalAmount()!=null){
            puiSupplierDeposit.setMoney(info.getTotalAmount());
        }
        return puiSupplierDeposit;
    }


}
