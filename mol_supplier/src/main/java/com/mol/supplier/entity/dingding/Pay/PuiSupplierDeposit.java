package com.mol.supplier.entity.dingding.Pay;

import lombok.Data;

import javax.persistence.Id;

@Data
public class PuiSupplierDeposit {
        /**
         * 订单id
         */
        @Id
        private String orderId;   //订单id
        /**
         * 供应商id
         */
        private  String supplierId;//供应商id
        /**
         * 支付金额
         */
        private  String Money;      //金额
        /**
         * 创建日期
         */
        private  String CreadTime;//创建日期
        /**
         * 是否退还
         */
        private  String returnMoney;//是否还换押金
        /**
         * 退还日期
         */
        private  String returnMoneyTime;//退还日期
        /**
         * 支付方式，支付宝1       微信2
         */
        private   String payType;//支付方式如支付宝或者微信
        /**
         * 支付平台生成的订单号码
         */
        private String tradeNo; //支付宝或者微信生成的订单号
        /**
         * 支付目的1，战略供应商服务费，2.单一供应商服务费，3.供应商缴纳专家评审费用，4.供应商缴纳合同费用
         */
        private String payFor;

        /**
         * 订单状态，1为未支付未过期，2为未支付已过期，4为被用户取消，5为支付正常完成
         */
        private String status;

}
