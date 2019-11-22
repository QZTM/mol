package com.mol.fadada.pojo;

import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 法大大认证记录实体类
 */
@Data
@Table(name = "fadada_auth_record")
public class AuthRecord implements Serializable {

    @Id
    private String id;
    /**
     * 客户姓名
     */
    private String personName;
    /**
     * 法大大id
     */
    private String customerId;
    /**
     * 交易号
     */
    private String transactionNo;
    /**
     * 认证类型
     */
    private String authenticationType;

    /**
     * 认证状态
     * 个人：Status: 0：未激活； 1：未认证； 2：审核通过； 3：已提交待审核； 4：审核不通过
     * 企业：status:  0：未认证； 1：管理员资料已提交； 2：企业基本资料(没有申请表)已提交； 3：已提交待审核； 4：审核通过； 5：审核不通过； 6 人工初审通过
     */
    private String status;

    /**
     * 认证地址
     */
    private String url;

    /**
     *
     */
    private String sign;

    /**
     * 客户类型
     */
    private String customerType;

    /**
     * 不通过原因描述
     */
    private String statusDesc;

    /**
     * 0:没有申请证书或者申请证书失败，
     * 1：成功申请证书
     */
    private String certStatus;

}
