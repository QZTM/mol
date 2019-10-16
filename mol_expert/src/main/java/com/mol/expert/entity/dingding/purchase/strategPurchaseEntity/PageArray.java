package com.mol.expert.entity.dingding.purchase.strategPurchaseEntity;

import com.mol.expert.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseArray;
import lombok.Data;

import java.util.List;

@Data
public class PageArray {

    private String applyCause;
    private List<PurchaseArray> purchaseArray;
    private String singleSource;
    private String pkSupplier;
    private String telePhone;
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

}
