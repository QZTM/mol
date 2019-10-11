package com.mol.supplier.entity.dingding.purchaseOrder;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 钉钉供应商
 */
@Table(name = "bd_supplier@ncdb")
@Data
public class Supplier {
    @Id
    @Column(name = "pk_supplier")
    private String pkSupplier;

    //营业执照号码
    private String buslicensenum;

    //供应商编码
    private String code;

    //对应客户
    @Column(name = "corcustomer")
    private String corcustomerId;

    //企业地址
    @Column(name = "corpaddress")
    private String corpaddressId;

    //e-mail地址
    private String email;

    //法人
    private String legalbody;

    //供应商名称
    private String name;

    //地区分类id
    @Column(name = "pk_areacl")
    private String pkAreaclId;

    //国家/地区id
    @Column(name = "pk_country")
    private String pkCountryId;

    //所属集团
    @Column(name = "pk_group")
    private String pkGroupId;

    //所属组织
    @Column(name = "pk_org")
    private String pkOrgId;

    //上级供应商
    private String pkSupplierMain;

    //供应商简称
    private String shortname;

    //供应商类型0=外部单位，1=内部单位，
    @Column(name = "supprop")
    private String suppropId;

    //供应商状态0=潜在，1=核准，
    private Integer supstate;

    //供应商提供的电话
    private String telePhone;


}
