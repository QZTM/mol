package com.mol.ddmanage.Ben;

public class App_user_table
{
   private String id;
    private  String user_name;//用户名
    private  String dd_user_id;//用户钉钉id
    private  String orgwx_user_id;//用户企业微信对应人员id
    private  String wx_user_id;//用户微信id
    private  String app_auth_org_id;//用户对应的企业id
    private  String mobile;//手机号码
    private  String email;//电子邮箱
    private  String if_buyer;//是否是采购员（0不是，1是，默认为1）
    private  String if_admin;//是否是管理员（0不是，1是，默认为0）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDd_user_id() {
        return dd_user_id;
    }

    public void setDd_user_id(String dd_user_id) {
        this.dd_user_id = dd_user_id;
    }

    public String getOrgwx_user_id() {
        return orgwx_user_id;
    }

    public void setOrgwx_user_id(String orgwx_user_id) {
        this.orgwx_user_id = orgwx_user_id;
    }

    public String getWx_user_id() {
        return wx_user_id;
    }

    public void setWx_user_id(String wx_user_id) {
        this.wx_user_id = wx_user_id;
    }

    public String getApp_auth_org_id() {
        return app_auth_org_id;
    }

    public void setApp_auth_org_id(String app_auth_org_id) {
        this.app_auth_org_id = app_auth_org_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIf_buyer() {
        return if_buyer;
    }

    public void setIf_buyer(String if_buyer) {
        this.if_buyer = if_buyer;
    }

    public String getIf_admin() {
        return if_admin;
    }

    public void setIf_admin(String if_admin) {
        this.if_admin = if_admin;
    }
}
