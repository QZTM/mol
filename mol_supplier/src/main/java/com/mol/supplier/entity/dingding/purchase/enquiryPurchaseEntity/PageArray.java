package com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity;

import lombok.Data;

import java.util.List;

@Data
public class PageArray {

    private String applyCause;
    private List<PurchaseArray> purchaseArray;
    private int quoteSellerNum;
    private int supplierSellerNum;
    private String remarks;
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
    //电子合同
    private String electronicContract;
}
