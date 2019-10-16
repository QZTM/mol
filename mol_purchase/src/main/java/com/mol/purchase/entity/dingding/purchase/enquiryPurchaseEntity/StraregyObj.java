package com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity;

import lombok.Data;


@Data
public class StraregyObj {

    private String id;
    private String buyChannelId;
    private String pkMarbasclass;
    private String goodsType;
    private String goodsBrand;
    private String goodsName;
    private String goodsSpecs;
    private String goodsBranch;
    private String goodsQuantity;
    private String goodsDetail;
    private String  createTime;
    private String Status;
    private String title;
    private String staffId;
    private String applyCause;
    private String remarks;
    private String orgId;
    //订单号
    private String orderNumber;
    //报价商家数
    private String quoteSellerNum;
    //零配件供应商数
    private String supplierSellerNum;
    //供应商id
    private String pkSupplier;
    //pdf_url
    private String reqfileurl;
    //订单报价的公司数量
    private String quoteCounts;
    //截止时间
    private String deadLine;
    //供货周期
    private String supplyCycle;
    //支付方式
    private String payMent;
    //技术支持电话
    private String technicalSupportTelephone;
    //专家评审
    private String expertReview;
    //评审奖励
    private String expertReward;
    //参与议价人员列表
    private String negotiatePerson;
    //议价完成时添加说明
    private String negotiatedExplain;
    //审批人建议
    private String approverProposal;
}
