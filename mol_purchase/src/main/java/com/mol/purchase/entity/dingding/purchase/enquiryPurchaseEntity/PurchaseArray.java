package com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity;

import lombok.Data;

@Data
public class PurchaseArray {

    private String fyPurchaseId;
    private String materialId;
    private String typeName;
    private String brandName;
    private String itemName;
    private String norms;
    private int count;
    private String unit;

    //历史平均价格
    private Double avgPriceHistory;

    //报价
    private String quote;
    //总价
    private String sum;
    //周期
    private String supplyCycle;

}
