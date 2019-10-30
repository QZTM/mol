package com.mol.ddmanage.Ben.SupplierManagement;

public class SupplierListben
{
    private String number;//序号
    private String pk_supplier;//供应商id
    private String name;//供应商名称
    private String supprop;//供应商类型 供应商类型0=外部单位，1=内部单位，

    private String industry_first;//行业类别第一级类别 (行业类别）
    private String industry_second;//行业类别第二级类别
    private String industry_third;//行业类别第三级类别
    private String industry;//行业列表明细

    private String legalbody_name;//法人
    private String telephone;//手机号
    private String regist_time;//注册时间

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupprop() {
        return supprop;
    }

    public void setSupprop(String supprop) {
        this.supprop = supprop;
    }

    public String getIndustry_first() {
        return industry_first;
    }

    public void setIndustry_first(String industry_first) {
        this.industry_first = industry_first;
    }

    public String getIndustry_second() {
        return industry_second;
    }

    public void setIndustry_second(String industry_second) {
        this.industry_second = industry_second;
    }

    public String getIndustry_third() {
        return industry_third;
    }

    public void setIndustry_third(String industry_third) {
        this.industry_third = industry_third;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLegalbody_name() {
        return legalbody_name;
    }

    public void setLegalbody_name(String legalbody_name) {
        this.legalbody_name = legalbody_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(String regist_time) {
        this.regist_time = regist_time;
    }

    public String getPk_supplier() {
        return pk_supplier;
    }

    public void setPk_supplier(String pk_supplier) {
        this.pk_supplier = pk_supplier;
    }
}
