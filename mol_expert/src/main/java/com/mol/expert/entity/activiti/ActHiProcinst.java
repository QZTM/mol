package com.mol.expert.entity.activiti;

import lombok.Data;

import java.util.Date;

/**
 * ClassName:ActHiProcinst
 * Package:com.purchase.entity.activiti
 * Description
 *（历史流程实例信息）核心表
 * @date:2019/9/26 11:57
 * @author:yangjiangyan
 */
@Data
public class ActHiProcinst {
    private String id;
    private String procInstId;
    private String businessKey;
    private String procDefId;
    private Date startTime;
    private Date endTime;
    private Integer duration;
    private String startUserId;
    private String startActId;
    private String endActId;
    private String superProcessInstanceId;
    private String deleteReason;
    private String tenantId;
    private String name;
}
