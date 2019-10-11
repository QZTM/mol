package com.mol.purchase.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 供应商实体类
 */
@Data
@Table(name = "bd_supplier")
public class Supplier implements Serializable {

    @Id
    private String pkSupplier;
    //供应商编码
    private String code;
    //供应商名称
    private String name;
    //对应客户
    private String corcustomer;
    //供应商简称
    private String shortname;
    //法人姓名
    private String legalbodyName;
    //法人身份证号码
    private String legalbodyIdcardNumber;
    //e-mail地址
    private String email;
    //所属集团
    private String pkGroup;
    //所属组织
    private String pkOrg;
    //上级供应商
    private String pkSupplierMain;

    //供应商类型0=外部单位，1=内部单位，
    private String supprop;

    //供应商状态0=潜在，1=核准，(2019-09-28更改为三种供应商单独的状态)
    //private Integer supstate;

    private Integer supstateNormal;
    private Integer supstateStrategy;
    private Integer supstateSingle;



    //公司提供的电话
    private String telephone;

    //营业执照
    private String buslicensenum;

    private String ddOrgId;

    //供应商属性，0为未设置，1为普通供应商，2为战略采购供应商，3为单一供应商，初始注册时为0
    //(2019-09-28更改为三种供应商单独标识)
    private Integer ifAttrNormal;
    private Integer ifAttrStrategy;
    private Integer ifAttrSingle;
    //营业执照图片
    private byte[] businessLicenceImg;
    //所在地所在省编码
    private String locationProvinceJsIndex;
    //所在地所在市编码
    private String locationCityJsIndex;
    //所在地所在区县编码
    private String locationAreaJsIndex;
    //注册地
    private String registeredAddress;
    //行业类别第一级类别
    private String industryFirst;
    //行业类别第二级类别
    private String industrySecond;
    //行业类别第三级类别
    private String industryThird;
    //经营范围
    private String businessScope;
    //法人身份证正面照片
    private byte[] legalbodyCardFrontImg;
    //法人身份证反面照片
    private byte[] legalbodyCardBackImg;
    //战略供应商协议
    private byte[] strategySupplierProtocol;
    //单一来源供应商协议
    private byte[] singleSupplierProtocol;
    //补充材料1
    private byte[] additionalOne;
    //补充材料2
    private byte[] additionalTwo;
    //补充材料3
    private byte[] additionalThree;
    //补充材料4
    private byte[] additionalFour;
    //认证手机号码
    private String authPhone;
    //注册时间：
    private String registTime;
    //最后修改时间
    private String lastUpdateTime;
    //最后修改人
    private String lastUpdateUser;


}
