package com.mol.supplier.service.microApp;

import com.alipay.api.AlipayApiException;
import com.mol.pay.Alipay;
import com.mol.pay.entity.AlipayCreateInfo;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;

@Service
@Log
public class MicroAlipayService {

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



    @Async
    public ListenableFuture<Boolean> getOrderStatus(String orderid){
        boolean ifSuccess = Alipay.ifOrderSuccess(orderid);
        return new AsyncResult<>(ifSuccess);
    }


}
