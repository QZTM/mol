package com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity;

import lombok.Data;

@Data
public class PurchaseDetail {

    private String id;
    private String fyPurchaseId;
    private String pkMaterial;
    private int goodsQuantity;
    //最终选中的报价
    private String quoteId;

}
