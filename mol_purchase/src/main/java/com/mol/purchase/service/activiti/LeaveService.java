package com.mol.purchase.service.activiti;

import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:LeaveService
 * Package:com.purchase.service.activiti
 * Description
 *
 * @date:2019/9/20 14:27
 * @author:yangjiangyan
 */
@Service
public class LeaveService {

    private static final Logger logger=LoggerFactory.getLogger(LeaveService.class);
    @Autowired
    private fyPurchaseMapper purchaseMapper;


    public void changeStatus(DelegateExecution execution,String status){

        logger.info("流程结束，设置订单状态");
        System.out.println("status:"+status);
        logger.info("状态：",status);
        String businessKey = execution.getProcessInstanceBusinessKey();
        logger.info("订单id："+businessKey);
        purchaseMapper.updateStatusById(businessKey,status);
    }

}
