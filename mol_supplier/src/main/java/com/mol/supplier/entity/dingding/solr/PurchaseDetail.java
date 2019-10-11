package com.mol.supplier.entity.dingding.solr;

import lombok.Data;

/**
 * fy_purchase_detail表
 */
@Data
public class PurchaseDetail {

    private String id;

    private String fyPurchaseId;

    private String goodsId;

    private int goodsQuantity;

}
