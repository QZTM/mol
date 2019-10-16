package com.mol.expert.entity.dingding.ncBase;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * nc65用户
 */
@Data
@Table(name = "sm_user")
public class NCUser {

    @Id
    @Column(name = "cuserid ")
    private String id;

    //身份类型，0=员工，4=外部系统，5=开发者，1=客户，2=供应商，3=审计，
    private Integer baseDocType;

    private String email;

    //身份
    private String pkBaseDoc;

    //所属组织
    private String pkOrg;

    //所属集团
    private String pkGroup;

    //身份——人员信息
    private String pkPsndoc;

    //用户编码
    private String userCode;
}
