package com.mol.fadada.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 法大大注册记录实体类
 */
@Data
@Table(name = "fadada_regist_record")
public class RegistRecord implements Serializable {

    @Id
    private String id;
    /**
     * 注册类型，1为个人，2为公司
     */
    private String accountType;
    /**
     * 接入平台客户唯一识别号
     */
    private String openId;
    /**
     * 法大大id
     */
    private String customerId;
    /**
     * 注册时间
     */
    private String registTime;


}
