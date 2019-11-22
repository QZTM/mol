package com.mol.fadada.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 法大大签署记录实体类
 */
@Table(name = "fadada_sign_result_recode")
@Data
public class SignResultRecord implements Serializable {

    @Id
    private String id;
    /**
     * 交易号
     */
    private String transactionId;
    /**
     * 合同编号
     */
    private String contractId;
    /**
     * 签署结果代码
     */
    private String resultCode;
    /**
     * 签署结果描述
     */
    private String resultDesc;
    /**
     * 下载地址
     */
    private String downloadUrl;
    /**
     * 查看地址
     */
    private String viewpdfUrl;
    /**
     * 请求时间
     */
    private String reqTime;
}
