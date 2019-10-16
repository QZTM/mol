package com.mol.expert.entity.dingding.approve;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 采购审批实例对象
 */
@Entity
@Data
@Table(name = "fy_purchase")
public class PurchaseApprove implements Serializable {


    @Id
    private String id;
    /**
     * 审批实例ID
     */
    private String processInstanceId;

    /**
     * 职员ID（发起审批人ID）
     */
    private String staffId;

    /**
     * 渠道ID
     */
    private Integer buyChannelId;

    /**
     * 产品类型
     */
    private String goodsType;

    /**
     * 产品名称
     */
    private String goodsName;

    /**
     * 产品规格
     */
    private String goodsSpecs;

    /**
     * 产品单位
     */
    private String goodsBranch;

    /**
     * 产品数量
     */
    private String goodsQuantity;

    /**
     * 产品详情
     */
    private String goodsDetail;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 部门ID
     */
    private String orgId;

    /**
     * 状态
     * 0代表新建，1代表正常进行，2代表完成，3代表中断
     */
    private Integer status;



    /**
     *实体信息url
     */
    private String url;

    /**
     * 审批标题
     */
    private String title;

    /**
     * 审批模板唯一ID
     */
    private String processCode;

    /**
     * cropID
     */
    private String cropId;


    /**
     * 审核人
     */
    private String auditor;

    /**
     * 申请事由
     */
    private String applyCause;

}
