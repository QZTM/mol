package com.mol.ddmanage.Ben.SupplierManagement;

public class SupplierToExamineListben
{
    private String number;//序号
    private String name;//供应商名称

    private String supstate_normal;//基础供应商状态0=潜在，1=核准，2=正在审批，4=审批被拒绝
    private String supstate_strategy;//战略采购供应商状态0=潜在，1=核准，2=正在审批，4=审批被拒绝
    private String supstate_single;//单一来源供应商状态0=潜在，1=核准，2=正在审批，4=审批被拒绝
    private String supplier_type;//申请的供应商类型

    private String industry_first;//行业类别第一级类别 (行业类别）
    private String industry;//行业列表明细

    private String legalbody_name;//法人
    private String telephone;//手机号
    private String regist_time;//注册时间
    private String Review_statu;//审核状态

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

    public String getSupstate_normal() {
        return supstate_normal;
    }

    public void setSupstate_normal(String supstate_normal) {
        this.supstate_normal = supstate_normal;
    }

    public String getSupstate_strategy() {
        return supstate_strategy;
    }

    public void setSupstate_strategy(String supstate_strategy) {
        this.supstate_strategy = supstate_strategy;
    }

    public String getSupstate_single() {
        return supstate_single;
    }

    public void setSupstate_single(String supstate_single) {
        this.supstate_single = supstate_single;
    }

    public String getSupplier_type() {
        return supplier_type;
    }

    public void setSupplier_type(String supplier_type) {
        this.supplier_type = supplier_type;
    }

    public String getIndustry_first() {
        return industry_first;
    }

    public void setIndustry_first(String industry_first) {
        this.industry_first = industry_first;
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

    public String getReview_statu() {
        return Review_statu;
    }

    public void setReview_statu(String review_statu) {
        Review_statu = review_statu;
    }
}
