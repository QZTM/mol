package com.mol.purchase.config;

import com.mol.purchase.entity.QuotePayresult;
import com.mol.purchase.mapper.newMysql.QuotePayresultMapper;
import com.mol.purchase.service.validOrder.ValidOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Transient;
import org.springframework.scheduling.annotation.Scheduled;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * ClassName:ValidOrder
 * Package:com.purchase.config
 * Description
 * 判断所有供应商支付完成订单的专家费用
 * @date:2019/8/6 11:10
 * @author:yangjiangyan
 */
@Configuration
public class ValidOrder {

    private static final Logger logger=LoggerFactory.getLogger(ValidOrder.class);

        @Autowired
    ValidOrderService validOrderService;

    @Scheduled(cron = "0 0/3 * * * ? ")//0 0/1 * * * ?
    public void allSupplierPayExpertCost(){
        logger.info("allSupplierPayExpertCost 定时任务开启");
        List<QuotePayresult> payResultByStatusList = validOrderService.findPayResultByStatus(OrderStatus.ALL_SUPPLIER_PAY_NOT + "");
        if (payResultByStatusList!=null){
            logger.info("需要处理的内容："+payResultByStatusList);
            validOrderService.updataStatus(payResultByStatusList);
        }else {
            logger.info("没有需要处理的数据...");
        }
        logger.info("allSupplierPayExpertCost 定时任务结束");

    }


}
