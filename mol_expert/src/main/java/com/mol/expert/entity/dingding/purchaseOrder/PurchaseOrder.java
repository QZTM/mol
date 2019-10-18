package com.mol.expert.entity.dingding.purchaseOrder;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * nc65采购订单实体类(对应表头)
 */
@Data
@Table(name = "po_order")
public class PurchaseOrder {
    @Id
    @Column(name = "pk_order")
    private String id;

    //订单编号
    private String vbillcode;

    //采购组织ID
    @Column(name = "pk_org_v")
    private String pkOrgVId;
    @Transient
    private String orgName;


    //制单人ID
    @Column(name = "billmaker")
    private String billmaker;
    @Transient
    private String billmakerName;

    //订单类型ID
    private String ctrantypeid;
    @Transient
    private String ctrantypeName;

    //订单类型code
    private String vtrantypecode;

    //制单日期
    private String dmakedate;

    //订单日期
    private String dbilldate;

    //创建时间
    private String creationtime;

    //审批日期
    private String taudittime;

    //审批人ID
    @Column(name = "approver")
    private String approverId;
    @Transient
    private String approveName;

    //创建人ID
    @Column(name = " pk_supplier ")
    private String creatorId;
    @Transient
    private String creatorName;

   //供应商ID
    @Column(name = "pk_supplier")
    private String  pkSupplierId;
    @Transient
    private String supplierName;

    //对方订单号
    private String vcoopordercode;

    //备注
    private String vmemo;

    @Transient
    private List<PurchaseOrderDetail> purchaseOrderDetailList;












}
