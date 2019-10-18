package com.mol.quartz.bean;

import lombok.Data;
import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="fy_purchase")
public class Purchase implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
    private Integer buyChannelId;
    private String goodsType;
    private String goodsBrand;
    private String goodsName;
    private String goodsSpecs;
    private String goodsBranch;
    private int goodsQuantity;
    private String goodsDetail;
    private String createTime;
    private String orgId;
    private String status;
    private String processInstanceId;
    private String staffId;
    private String url;
    private String title;
    private String processCode;
    private String cropId;
    private String auditor;
    private String applyCause;
    private String remarks;
    //零配件供应商数
    private String supplierSellerNum;
    //供应商id
    private String pkSupplier;
    //订单号
    private String orderNumber;
    //报价商家数
    private String quoteSellerNum;
    //pdf_url
    private String reqfileurl;
    //订单报价的公司数量
    private String quoteCounts;
    //截止时间
    private String deadLine;
    //供货周期
    private String supplyCycle;
    //支付方式
    private String payMent;
    //技术支持电话
    private String technicalSupportTelephone;
    //专家评审
    private String expertReview;
    //参与议价人员列表
    private String negotiatePerson;
    //待议价结束后的订单说明
    private String negotiatedExplain;
    //待审核结束后的审核人的建议
    private String approverProposal;
}
