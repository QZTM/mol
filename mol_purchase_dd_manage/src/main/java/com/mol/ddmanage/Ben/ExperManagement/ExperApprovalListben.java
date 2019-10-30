package com.mol.ddmanage.Ben.ExperManagement;

public class ExperApprovalListben//专家审核展示列表实体类
{
    private String number;//序号
    private String id;//专家id
    private String name;// 姓名
    private String birthday;//出生日期
    private String id_number;//身份证号码
    private String industry;//所属行业
    private String work_life;//工作年限

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getWork_life() {
        return work_life;
    }

    public void setWork_life(String work_life) {
        this.work_life = work_life;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
