package com.mol.purchase.entity.activiti;

import javax.xml.crypto.Data;

/**
 * @Classname ActHiActinst
 * @Description TODO
 * @Date 2019/11/8 13:57
 * @Created by Lenovo
 */
@lombok.Data
public class ActHiActinst {
    private String id;
    private String procDefId;
    private String procInstId;
    private String executionId;
    private String actId;
    private String taskId;
    private String callProcInstId;
    private String actName;
    private String actType;
    private String assignee;
    private Data startTime;
    private Data endTime;
    private String duration;
    private String deleteReason;
    private String tenantId;
}
