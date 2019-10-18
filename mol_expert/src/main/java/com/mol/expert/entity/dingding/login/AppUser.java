package com.mol.expert.entity.dingding.login;

import lombok.Data;

@Data
public class AppUser {

    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户对应的钉钉id
     */
    private String ddUserId;

    /**
     * 用户对应的企业微信id
     */
    private String orgwxUserId;

    /**
     * 用户对应的微信id
     */
    private String wxUserId;

    /**
     * 用户对应的企业id
     */
    private String appAuthOrgId;

    /**
     * 用户手机号码
     */
    private String mobile;

    /**
     * 用户的电子邮箱
     */
    private String email;

    /**
     * 是否是采购员
     */
    private Integer ifBuyer;

    /**
     * 是否是E应用管理员
     */
    private Integer ifAdmin;
}
