package com.mol.expert.config;

public class ExpertStatus {

    /**
     推荐订单的状态
     */
    //采纳
    public static final Integer EXPERT_ORDER_OKADOPT=1;

    //未采纳
    public static final Integer EXPERT_ORDER_NOTADOPTED=0;

    //等待审核
    public static final Integer EXPERT_ORDER_WAITADOPTED=2;


    /**
     * 佣金分配状态
     */
    //分配
    public static final Integer EXPERT_MONEY_DISTRIBUTION=1;

    //未分配
    public static final Integer EXPERT_MONEY_NOTDISTRIBUTION=0;

}
