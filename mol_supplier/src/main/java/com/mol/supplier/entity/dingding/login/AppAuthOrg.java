package com.mol.supplier.entity.dingding.login;

import lombok.Data;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "app_auth_org")
public class AppAuthOrg {


    private String id;
    /**
     * 企业名称
     */
    private String orgName;

    /**
     * 是否已开通钉钉使用权限（0为未开通，1为已开通，默认0）
     */
    private Integer authDd;

    /**
     * 是否已开通企业微信使用权限（0为未开通，1为已开通，默认0）
     */
    private Integer authWx;

    /**
     * 企业对应的钉钉企业cropid
     */
    private String ddOrgCorpId;

    /**
     * 企业小程序对应的agentid
     */
    private String ddOrgAgentId;

    /**
     * 企业对应的企业微信的企业id
     */
    private String wxOrgId;

    /**
     * 该企业最后的登陆时间
     */
    private String lastLoginTime;

    /**
     * 该企业最后的登录人
     */
    private String lastLoginUserId;

    /**
     * 采购负责人
     */
    private AppUser purchaseMainPerson;

    /**
     * 审批领导
     */
    private List<AppUser> purchaseApproveLeader;

    //采购审批人员id
    private String purchaseApproveList;
}
