package com.mol.supplier.entity.MicroApp;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bd_supplier_auth_info")
@Data
public class SupplierAuthInfo {

    @Id
    private String id;
    //供应商表ID
    private String bdSupplierId;
    //营业执照号码
    private String bussinessLicenceNumber;
    //营业执照图片
    private byte[] bussinessLicenceImg;
    //所在地所在省编码
    private String locationProvinceCode;
    //所在地所在市编码
    private String locationCityCode;
    //所在地所在区县编码
    private String locationAreaCode;
    //注册地
    private String registeredAddress;
    //行业类别第一级类别
    private String industryFirst;
    //行业类别第二级类别
    private String industrySecode;
    //行业类别第三级类别
    private String industryThird;
    //经营范围
    private String bussinessScope;
    //法人身份证正面照片
    private byte[] legalPersonCardFrontImg;
    //法人身份证反面照片
    private byte[] legalPersonCardBackImg;


    //战略供应商协议
    private byte[] strategySupplierProtocol;
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


}
