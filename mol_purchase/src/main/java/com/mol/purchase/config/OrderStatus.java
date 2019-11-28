package com.mol.purchase.config;


public class OrderStatus {
    /**
     * 订单状态
     */
    /**
     * 正在询价   正在采集报价
     */
    public static final Integer WAITING_QUOTE=1;
    /**
     * 采购结束
     */
    public static final Integer PURCHASE_CLOSURE=2;
    /**
     * 采购废止   废止
     */
    public static final Integer PURCHASE_ABOLISH=3;
    /**
     *   等待议价
     */
    public static final Integer UNDER_REVIEW=4;
    /**
     * 进度页面状态/--------------------------------
     */

    /**
     * 等待审核结果
     */
    public static final Integer END_OF_BARGAINING=6;

    /**
     *  通过
     */
    public static final Integer PASS=7;
    /**
     *  淘汰
     */
    public static final Integer REFUSE=8;

    /**
     * 单独供应商未完成支付专家费用
     */
    public static final Integer SINGLE_SUPPLIER_PAY_NOT=0;

    /**
     * 单独供应商已完成支付专家费用
     */
    public static final Integer SINGLE_SUPPLIER_PAY_YES=0;



    /**
     * 所有供应商未完成订单支付
     */
    public static final Integer ALL_SUPPLIER_PAY_NOT=0;

    /**
     * 所有供应商已完成订单支付
     */
    public static final Integer ALL_SUPPLIER_PAY_YES=1;


}
