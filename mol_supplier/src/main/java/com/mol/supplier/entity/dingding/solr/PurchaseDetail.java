package com.mol.supplier.entity.dingding.solr;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * fy_purchase_detail表
 */
@Data
@Table(name = "fy_purchase_detail")
public class PurchaseDetail {

    @Id
    private String id;
    private String fyPurchaseId;
    private String pkMaterial;
    private int goodsQuantity;
    //最终选中的报价
    private String quoteId;
    //最终选中的推荐专家
    private String expertId;

}
