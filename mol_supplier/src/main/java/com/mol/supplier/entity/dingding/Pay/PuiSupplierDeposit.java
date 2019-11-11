package com.mol.supplier.entity.dingding.Pay;

import lombok.Data;

import javax.persistence.Id;

/**
 * 支付订单记录实体类
 */
@Data
public class PuiSupplierDeposit {

        /**
         * 订单状态常量，1为未支付未过期，2为未支付已过期，4为被用户取消，5为支付正常完成
         */
        public static final String ORDER_STATUS_UNPAID_UNEXPIRED = "1";
        public static final String ORDER_STATUS_UNPAID_EXPIRED = "2";
        public static final String ORDER_STATUS_CANCELLED = "4";
        public static final String ORDER_STATUS_SUCCESS = "5";

        /**
         * 支付目的常量（1，战略供应商服务费，2.单一供应商服务费，3.供应商缴纳专家评审费用，4.供应商缴纳合同费用）
         */
        public static final String ORDER_PAY_FOR_STRATEGY_SUPPLIER_SERVICE = "1";
        public static final String ORDER_PAY_FOR_SINGON_SUPPLIER_SERVICE = "2";
        public static final String ORDER_PAY_FOR_EXPERT_REVIEW_FEE = "3";
        public static final String ORDER_PAY_FOR_CONTRACT_FEE = "4";

        /**
         * 支付方式常量 支付宝1    微信2
         */
        public static final String ORDER_PAY_TYPE_ALIPAY = "1";
        public static final String ORDER_PAY_TYPE_WEIXIN = "2";

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
