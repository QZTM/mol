package com.mol.expert.entity.activiti;

import lombok.Data;

import java.util.Date;

/**
 * ClassName:ActHiComment
 * Package:com.purchase.entity.activiti
 * Description
 *  历史审批意见表
 * @date:2019/9/26 13:02
 * @author:yangjiangyan
 */
@Data
public class ActHiComment {
    private String id;
    private String type;
    private Date  time;
    private String userId;
    private String taskId;
    private String procInstId;
    private String action;
    private String message;
    private String fullMsg;

}
