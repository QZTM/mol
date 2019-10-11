package com.mol.supplier.entity.dingding.purchaseOrder;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 采购订单详情类（对应订单详情）
 */
@Table(name = "po_order_b")
@Data
public class PurchaseOrderDetail {
    @Id
    @Column(name = "pk_order_b")
    private String id;

    //对应po_order表ID
    private String pkOrder;

    //物料ID
    private String pkMaterial;
    //物料名称
    @Transient
    private String materialName;

    //主数量
    private Integer nnum;

    //主无税单价
    private BigDecimal norigprice;

    //主含税单价
    private BigDecimal norigtaxprice;
}
