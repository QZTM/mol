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
     * 法大大id
     */
    private String customerId;
    /**
     * 法大大客户类型，1为个人，2为单位
     */
    private String customerType;
    /**
     * 交易号
     */
    private String transactionNo;
    /**
     * 认证地址
     */
    private String url;


}
