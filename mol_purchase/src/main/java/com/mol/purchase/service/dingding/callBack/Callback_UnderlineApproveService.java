package com.mol.purchase.service.dingding.callBack;

import com.alibaba.fastjson.JSONObject;
import com.mol.purchase.config.ApproveStatus;
import com.mol.purchase.entity.dingding.approve.PurchaseApprove;
import com.mol.purchase.service.DDBaseService;
import com.mol.purchase.service.dingding.approve.ApproveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class Callback_UnderlineApproveService {


    private static final Logger bizLogger = LoggerFactory.getLogger(Callback_UnderlineApproveService.class);

    @Autowired
    private ApproveService approveService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private DDBaseService ddBaseService;

    public void handlerBPMS_INSTANCE_CHANGE(JSONObject obj, String plainText, String processInstanceId) {
        bizLogger.info(obj.toJSONString());
        /**
         * 审批最终通过
         */
        if (obj.containsKey("result") && obj.getString("result").equals("agree")) {
            ddBaseService.sendMessageToOriginator(processInstanceId);

            //更改数据库状态为完成  2：
            PurchaseApprove pa = new PurchaseApprove();
            pa.setProcessInstanceId(obj.getString("processInstanceId"));
            pa.setStatus(ApproveStatus.finalSuccess);

            approveService.updateApprove(pa);


            /**
             * 审批最终被拒绝
             */
        }else if(obj.containsKey("result") && obj.getString("result").equals("refuse")){
            //最终被拒绝了：修改数据库审批实例状态为  4
            PurchaseApprove pa = new PurchaseApprove();
            pa.setProcessInstanceId(obj.getString("processInstanceId"));
            pa.setStatus(ApproveStatus.finalFail);
            approveService.updateApprove(pa);

            /**
             * 审批开始
             */
        }else if(obj.containsKey("type") && obj.getString("type").equals("start")){

        }
    }



    /**
     * 审批任务状态更改处理
     */
    public void handlerBPMS_TASK_CHANGE(JSONObject obj,String plainText,String processInstanceId){

    }



}
