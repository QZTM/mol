package com.mol.pay.entity;

import com.alipay.api.domain.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Map;

/**
 * 支付宝形成支付订单需要的实体类
 */
@Data
public class AlipayCreateInfoBuilder implements Serializable {

    private static AlipayCreateInfoBuilder alipayCreateInfoBuilder = new AlipayCreateInfoBuilder();

    private  AlipayCreateInfoBuilder(){

    }


    public static AlipayCreateInfoBuilder getBuilder() {
        if (alipayCreateInfoBuilder == null) {
            synchronized (AlipayCreateInfoBuilder.class) {
                if (alipayCreateInfoBuilder == null) {
                    alipayCreateInfoBuilder = new AlipayCreateInfoBuilder();
                }
            }
        }
        return alipayCreateInfoBuilder;
    }


    /**
     *
     * @param body              商品名称
     * @param subject           商品说明或备注
     * @param outTradeNo        订单编号
     * @param timeoutExpress    订单超时时间
     * @param productCode       产品编号
     * @param totalAmount       支付金额
     * @return
     */
    public AlipayCreateInfo builder(String body, String subject, String outTradeNo, String timeoutExpress, String productCode, String totalAmount, Map callbackParamMap){
        AlipayCreateInfo alipayCreateInfo = new AlipayCreateInfo();
        alipayCreateInfo.setBody(body);
        alipayCreateInfo.setSubject(subject);
        alipayCreateInfo.setOutTradeNo(outTradeNo);
        alipayCreateInfo.setTimeoutExpress(timeoutExpress);
        alipayCreateInfo.setProductCode(productCode);
        alipayCreateInfo.setTotalAmount(totalAmount);
        alipayCreateInfo.setCallbackParamMap(callbackParamMap);
        return alipayCreateInfo;
    }

}
