package com.mol.ddmanage.Ben.DepartmentManagement;

public class jurisdictionManagementben//职位列表实体类
{
    private String number;//序号
    private String jurisdictionId;//岗位id
    private String jurisdictionName;//职位名称
    private String jurisdictionSize;//权限范围
    private String Staffs;//员工名单
    private String status;//职位状态

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getJurisdictionName() {
        return jurisdictionName;
    }

    public void setJurisdictionName(String jurisdictionName) {
        this.jurisdictionName = jurisdictionName;
    }

    public String getJurisdictionSize() {
        return jurisdictionSize;
    }

    public void setJurisdictionSize(String jurisdictionSize) {
        this.jurisdictionSize = jurisdictionSize;
    }

    public String getStaffs() {
        return Staffs;
    }

    public void setStaffs(String staffs) {
        Staffs = staffs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJurisdictionId() {
        return jurisdictionId;
    }

    public void setJurisdictionId(String jurisdictionId) {
        this.jurisdictionId = jurisdictionId;
    }
}
