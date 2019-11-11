package com.mol.pay.entity;

import com.alipay.api.domain.*;
import lombok.Data;
import java.io.Serializable;

/**
 * 支付信息载体
 */
@Data
public class AlipayCreateInfo implements Serializable {
    //商品名称
    private String body;
    private String businessParams;
    private String disablePayChannels;
    private String enablePayChannels;
    private ExtUserInfo extUserInfo;
    private ExtendParams extendParams;
    private String goodsType;
    private InvoiceInfo invoiceInfo;
    private String merchantOrderNo;
    private String outTradeNo;
    private String passbackParams;
    private String productCode;
    private String promoParams;
    private RoyaltyInfo royaltyInfo;
    private String sellerId;
    private SettleInfo settleInfo;
    private String specifiedChannel;
    private String storeId;
    private SubMerchant subMerchant;
    //商品说明或备注
    private String subject;
    private String timeExpire;
    private String timeoutExpress;
    //支付金额
    private String totalAmount;
    //回调地址
    private String callbackUrl;
}
