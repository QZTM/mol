package com.mol.ddmanage.Ben.ExperManagement;

public class ExpertInforPageListben//专家实体类
{
    private String number;//序号
    private String id;//专家id
    private String expert_grade;//专家等级
    private String name; //专家名称
    private String review_number;//参与订单
    private String pass_rate;//通过率
    private String industry;//行业
    private String authentication;//状态 1=认证  0=未认证

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpert_grade() {
        return expert_grade;
    }

    public void setExpert_grade(String expert_grade) {
        this.expert_grade = expert_grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview_number() {
        return review_number;
    }

    public void setReview_number(String review_number) {
        this.review_number = review_number;
    }

    public String getPass_rate() {
        return pass_rate;
    }

    public void setPass_rate(String pass_rate) {
        this.pass_rate = pass_rate;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
