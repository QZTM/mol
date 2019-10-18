package com.mol.expert.service.dingding.callBack;

import com.alibaba.fastjson.JSONObject;
import com.mol.expert.config.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处理回调事件service，通过判断审批的processcode来调用不同的service来处理
 */
@Service
public class CallbackService {

    private static final Logger bizLogger = LoggerFactory.getLogger(CallbackService.class);
    @Autowired
    private Callback_UnderlineApproveService callback_underlineApproveService;

    /**
     * 审批实例状态更改（发起审批和审批结束时触发）
     * @param obj
     * @param plainText
     * @param processInstanceId
     */
    public void handlerBPMS_INSTANCE_CHANGE(JSONObject obj,String plainText,String processInstanceId){
        bizLogger.info("收到审批实例状态更新: " + plainText);
        //指定处理的审批模板：
        if(obj.containsKey("processCode") && obj.getString("processCode").equals(Constant.UNDERLINEPUR_PROCESS_CODE)){
            callback_underlineApproveService.handlerBPMS_INSTANCE_CHANGE(obj,plainText,processInstanceId);
        }
    }


    /**
     * 审批任务状态更改
     */
    public void handlerBPMS_TASK_CHANGE(JSONObject obj,String plainText,String processInstanceId){
        bizLogger.info("收到审批任务进度更新: " + plainText);
        if(obj.containsKey("processCode") && obj.getString("processCode").equals(Constant.UNDERLINEPUR_PROCESS_CODE)){
            callback_underlineApproveService.handlerBPMS_TASK_CHANGE(obj,plainText,processInstanceId);
        }
    }
}
