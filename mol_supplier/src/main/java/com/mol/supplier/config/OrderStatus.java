package com.mol.supplier.config;


public class OrderStatus {
    /**
     * 订单状态
     */
    /**
     * 正在询价   正在采集报价
     */
    public static final Integer waitingQuote=1;
    /**
     * 采购结束
     */
    public static final Integer PurchaseClosure=2;
    /**
     * 采购废止   废止
     */
    public static final Integer Purchaseabolish=3;
    /**
     *   等待议价
     */
    public static final Integer UnderReview=4;
    /**
     * 进度页面状态/--------------------------------
     */

    /**
     * 等待审核结果
     */
    public static final Integer EndOfBargaining=6;

    /**
     *  通过
     */
    public static final Integer pass=7;
    /**
     *  淘汰
     */
    public static final Integer refuse=8;



}
