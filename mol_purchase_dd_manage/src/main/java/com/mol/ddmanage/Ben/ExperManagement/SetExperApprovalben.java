package com.mol.ddmanage.Ben.ExperManagement;

public class SetExperApprovalben//专家审核信息实体类
{
    private String id;//专家id
    private String name;//姓名
    private String birthday;//出生年月日
    private String age;//年龄
    private String work_life;//工作年限
    private String id_number;//身份证号码
    private String industry;//所属行业

    private byte []front_of_id;//身份证正面
    private byte []reverse_of_id;//身份证反面
    private byte []front_of_business;//名片正面
    private byte []reverse_of_business;//名片反面
    private byte []other_documents_one;//其他材料1
    private byte []other_documents_two;//其他材料2

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWork_life() {
        return work_life;
    }

    public void setWork_life(String work_life) {
        this.work_life = work_life;
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


    public byte[] getReverse_of_id() {
        return reverse_of_id;
    }

    public void setReverse_of_id(byte[] reverse_of_id) {
        this.reverse_of_id = reverse_of_id;
    }

    public byte[] getFront_of_business() {
        return front_of_business;
    }

    public void setFront_of_business(byte[] front_of_business) {
        this.front_of_business = front_of_business;
    }

    public byte[] getReverse_of_business() {
        return reverse_of_business;
    }

    public void setReverse_of_business(byte[] reverse_of_business) {
        this.reverse_of_business = reverse_of_business;
    }

    public byte[] getOther_documents_one() {
        return other_documents_one;
    }

    public void setOther_documents_one(byte[] other_documents_one) {
        this.other_documents_one = other_documents_one;
    }

    public byte[] getOther_documents_two() {
        return other_documents_two;
    }

    public void setOther_documents_two(byte[] other_documents_two) {
        this.other_documents_two = other_documents_two;
    }




    public byte[] getFront_of_id() {
        return front_of_id;
    }

    public void setFront_of_id(byte[] front_of_id) {
        this.front_of_id = front_of_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
