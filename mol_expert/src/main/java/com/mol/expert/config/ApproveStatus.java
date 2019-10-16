package com.mol.expert.config;

/**
 * 审批实例状态常量类
 * 0:新形成
 * 1：等待审批
 * 2：最终通过
 * 3：中间环节驳回
 * 4：最终驳回
 */
public class ApproveStatus {
    /**
     * 新形成
     */
    public static final Integer newApprove = 0;

    /**
     * 等待审批
     */
    public static final Integer waitApprove = 1;

    /**
     * 最终通过
     */
    public static final Integer finalSuccess = 2;

    /**
     * 中间环节驳回
     */
    public static final Integer middleFail = 3;

    /**
     * 最终驳回
     */
    public static final Integer finalFail = 4;

    /**
     * 正在询价
     */
    public static final Integer onEnquiry=5;

    /**
     * 采购废止
     */
    public static final Integer abolishEnuiry=6;

    /**
     * 采购完成
     */

    /**
     * 采购结束
     */
    public static final Integer overEnuiry=7;
}
