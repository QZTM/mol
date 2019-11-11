package com.mol.supplier.config;

import com.mol.supplier.mapper.dingding.purchase.fyPurchaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Reference;

/**
 * ClassName:ValidOrder
 * Package:com.purchase.config
 * Description
 * 判断订单在截止日期过后是否作废
 * @date:2019/8/6 11:10
 * @author:yangjiangyan
 */
@Configuration
public class ValidOrder {

    private static final Logger logger=LoggerFactory.getLogger(ValidOrder.class);

    @Reference
    private fyPurchaseMapper  fyPurchaseMapper;
//
//    @Scheduled(cron = "0 */1 * * * *")
//    public void DetectOrderStatus(){
//        logger.info("=========定时开始=========");
//        String nowDateTime = TimeUtil.getNowDateTime();
//        logger.info("现在的时间："+nowDateTime);
//        List<fyPurchase> purList =fyPurchaseMapper.selectOrderByDeadLine(nowDateTime);
//
//
//        if (purList!=null&&purList.size()>0){
//            for (fyPurchase pur : purList) {
//                if (Integer.parseInt(pur.getQuoteCounts())<3){
//                    String status = OrderStatus.Purchaseabolish + "";
//                    String id =pur.getId();
//                    fyPurchaseMapper.updateStatusById(id,status);
//                }
//            }
//        }
//        logger.info("=========定时结束=========");
//
//    }

}
