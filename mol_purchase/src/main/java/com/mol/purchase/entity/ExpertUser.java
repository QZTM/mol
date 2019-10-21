package com.mol.purchase.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * ClassName:ExpertUser
 * Package:com.purchase.entity.expert
 * Description
 *  专家
 * @date:2019/10/6 16:47
 * @author:yangjiangyan
 */
@Data
public class  ExpertUser {
    @Id
    private String id;
    private String name;
    private String avatar;
    private String orgId;
    private String mobile;


    private String createTime;
    private String lastUpdateTime;
    private String lastSigninTime;
    private String authentication;//认证  1=认证  0=未认证
    private String expertGrade;//专家等级
    private String reviewNumber;//参与评审次数
    private String pass;//通过的次数
    private String passRate;//通过率  通过的次数 / 参与的订单中结束的订单的件数
    private String major;//专业
    private String award;//奖励总和
    private byte[] frontOfId;
    private byte[] reverseOfId;
    private byte[] frontOfBusiness;
    private byte[] reverseOfBusiness;
    private byte[] otherDocumentsOne;
    private byte[] otherDocumentsTwo;
    private String birthday;
    private String age;
    private String workLife;
    private String marId;
    private String idNumber;

    //推荐理由
    @Transient
    private String recommendReason;

    @Transient
    private Boolean checked;

}
