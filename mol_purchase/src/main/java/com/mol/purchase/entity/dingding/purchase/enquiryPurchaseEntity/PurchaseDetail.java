package com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity;

import lombok.Data;

@Data
public class PurchaseDetail {

    private String id;
    private String fyPurchaseId;
    private String pkMaterial;
    private int goodsQuantity;
    //最终选中的报价
    private String quoteId;
    //最终选中的推荐专家
    private String expertId;
}
