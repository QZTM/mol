package com.mol.purchase.service.validOrder;

import com.mol.purchase.config.OrderStatus;
import com.mol.purchase.entity.ExpertUser;
import com.mol.purchase.entity.QuotePayresult;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.dingding.solr.fyPurchase;
import com.mol.purchase.mapper.newMysql.ExpertRecommendMapper;
import com.mol.purchase.mapper.newMysql.ExpertUserMapper;
import com.mol.purchase.mapper.newMysql.QuotePayresultMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;
import util.BigDecimalUtils;
import util.TimeUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class ValidOrderService {

    @Autowired
    QuotePayresultMapper quotePayresultMapper;
    @Autowired
    fyPurchaseDetailMapper purchaseDetailMapper;
    @Autowired
    ExpertRecommendMapper expertRecommendMapper;
    @Autowired
    ExpertUserMapper expertUserMapper;
    @Autowired
    fyPurchaseMapper  purchaseMapper;

    private static final Logger logger= LoggerFactory.getLogger(ValidOrderService.class);


    public List<QuotePayresult> findPayResultByStatus(String status){
//        Example e =new Example(QuotePayresult.class);
//        e.and().andEqualTo("status",status);
//        e.setDistinct(true);
//        return quotePayresultMapper.selectByExample(e);
        return quotePayresultMapper.findPayResultListByStatus(status);
    }

    public void updataStatus(List<QuotePayresult> payResultByStatusList) {
        //HashSet<String> checkPurIdSet=new HashSet<>();
        for (QuotePayresult quotePayresult : payResultByStatusList) {
            logger.info("订单id："+quotePayresult.getPurchaseId());
            pay(quotePayresult.getPurchaseId());
        }
    }
    //修改订单全部支付，支付专家费用
    public void pay(String purchaseId) {
            Integer result = updataStatus(purchaseId);
            logger.info("订单支付结果，修改为已全部支付成功！");
            payExpertCost(purchaseId);
    }
    //修改订单全部支付，
    private Integer updataStatus(String purchaseId){
        String stats=OrderStatus.ALL_SUPPLIER_PAY_YES+"";
        String time=TimeUtil.getNowDateTime();
        return quotePayresultMapper.updataStatusByPurId(stats,time,purchaseId);
    }
    //支付专家费用
     public Integer payExpertCost(String purId){
         logger.info("支付专家费用逻辑执行*****");
         //查询订单
         fyPurchase pur = purchaseMapper.findOneById(purId);
         logger.info("支付专家推荐的订单："+pur);
         //奖励金额
         String expertReward = pur.getExpertReward();
         logger.info("订单需要支付的总推荐金额："+expertReward);

         //根据订单id 查询选中的专家id
         List<PurchaseDetail> purDetailList=findPurDetailByPurId(purId);
         //专家id不为空
         String expertIdString = purDetailList.get(0).getExpertId();
         logger.info("专家id的字符串："+expertIdString);

         if (expertIdString!=null){
             logger.info("需要支付专家推荐，并且有选中的推荐专家");
             //专家idArray
             String[] split = expertIdString.split(",");
             logger.info("专家id的Arr："+split.toString());

             //专家数量
             int expertCount=split.length;
             logger.info("获得奖励的专家的数量："+expertCount);
             //计算每个专家获得的奖励金额

             double moneyOne = Math.floor(BigDecimalUtils.divide(expertReward, expertCount + ""));
             logger.info("每个专家获得的奖励金额："+moneyOne);

             //expert_recommend插入数据
             String nowTime= TimeUtil.getNowDateTime();
             for (String expertId : split) {
                 //更新订单的获得金额
                 int updataERResult=expertRecommendMapper.updataComissionMoneyByPurIdAndExpertId(moneyOne+"",nowTime,pur.getId(),expertId);
                 logger.info("更新订单结果："+updataERResult);
                 //更新个人总金额
                 ExpertUser t=new ExpertUser();
                 t.setId(expertId);
                 ExpertUser expertUser = expertUserMapper.selectOne(t);
                 String newAward = BigDecimalUtils.add(expertUser.getAward(),moneyOne+"")+ "";
                 logger.info("更新之后个人的总金额："+newAward);

                 Integer updataEUResult = expertUserMapper.updataAwardByExpertId(newAward, expertId);
                 logger.info("更新个人金额结果："+updataEUResult);

             }
             //expert_user更新数据
         }else {
             logger.info("需要支付专家推荐，但是没有选中的推荐专家");
         }

        return 0;
    }

    public List<PurchaseDetail> findPurDetailByPurId(String purId) {
        PurchaseDetail t = new PurchaseDetail();
        t.setFyPurchaseId(purId);
        return purchaseDetailMapper.select(t);
    }

    public List<QuotePayresult> findPayResultByPurId(String purId){
        if (purId==null){
            return new ArrayList<>();
        }
        QuotePayresult t = new QuotePayresult();
        t.setPurchaseId(purId);
        return quotePayresultMapper.select(t);
    }
}
