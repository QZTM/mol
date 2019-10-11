package com.mol.purchase.entity.dingding.Pay;

import lombok.Data;

import javax.persistence.Id;

@Data
public class pui_supplier_deposit
{
@Id
        private String orderId;   //订单id
        private  String supplierId;//供应商id
        private  String Money;      //金额
        private  String CreadTime;//创建日期
        private  String returnMoney;//是否还换押金
        private  String returnMoneyTime;//退还日期
        private   String payType;//支付方式如支付宝或者微信
        private String tradeNo; //支付宝或者微信生成的订单号

}
