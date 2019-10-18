package com.mol.quartz.job;

import com.mol.quartz.entity.Purchase;
import com.mol.quartz.mapper.PurchaseMapper;
import com.mol.quartz.service.PurchaseService;
import lombok.extern.java.Log;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 专家评审结束任务实际逻辑
 */
@Log
public class ExpertReviewEndJob implements Job {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data=context.getTrigger().getJobDataMap();
        Object orderIdObj = data.get("orderId");
        String orderId = "";
        if(orderIdObj != null){
            orderId = (String) orderIdObj;
        }else{
            log.warning("报价结束定时任务------找不到orderId");
            throw new RuntimeException("需要处理的报价结束任务参数异常------找不到orderId");
        }
        Purchase updatePurchase = new Purchase();
        updatePurchase.setId(orderId);
        updatePurchase.setStatus("4");
        purchaseMapper.updateByPrimaryKey(updatePurchase);
    }
}
