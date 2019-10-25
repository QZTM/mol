package com.mol.expert.entity.expert;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ClassName:ExpertRecommend
 * Package:com.purchase.entity.expert
 * Description
 * 专家推荐表
 * @date:2019/10/6 16:49
 * @author:yangjiangyan
 */
@Data
@Table(name = "expert_recommend")
public class ExpertRecommend {
    @Id
    private String id;
    private String expertId;
    private String purchaseId;
    private String supplierId;
    private String recommendReason;
    private String createTime;
    //是否采纳
    private String adopt;
    //佣金是否分配
    private String commission;
    //推荐采纳时间
    private String recommendTime;
    //佣金
    private String commissionMoney;
}
