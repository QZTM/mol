package com.mol.quartz.job;

import com.mol.quartz.entity.Purchase;
import com.mol.quartz.handler.AddJobHandler;
import com.mol.quartz.mapper.PurchaseMapper;
import com.mol.quartz.service.PurchaseService;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.TimeUtil;

import java.util.Iterator;
import java.util.Set;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
@Log
public class QuoteEndJob implements Job{


	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private PurchaseMapper purchaseMapper;

	@Autowired
	private AddJobHandler addJobHandler;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDataMap data=context.getTrigger().getJobDataMap();

		Set set = data.keySet();
		Iterator iterator = set.iterator();
		if(iterator.hasNext()){
			String mapKey = iterator.next().toString();
			System.out.println("key:"+mapKey+",,,"+data.get(mapKey));
		}

		Object orderIdObj = data.get("orderId");
		String orderId = "";
		if(orderIdObj != null){
			orderId = (String) orderIdObj;
		}else{
			log.warning("报价结束定时任务------找不到orderId");
			throw new RuntimeException("需要处理的报价结束任务参数异常------找不到orderId");
		}
		System.out.print("JobTwo执行,参数：");
		System.out.println("orderId:"+orderId);
		Purchase purchase = purchaseMapper.selectByPrimaryKey(orderId);
		System.out.println("订单"+orderId+"根据主键查询到的purchase:");
		System.out.println(purchase);
		if(purchase == null){
			log.warning("订单"+orderId+"根据主键获取的purchase为空");
			throw new RuntimeException("订单"+orderId+"根据主键获取的purchase为空");
		}
		//根据该订单id查询该订单实际报价商家数是否大于需要的报价商家数
		//Integer compareResult = purchaseService.compareQuoteSellerNumAndSellerCountById(orderId);
		Object quoteSellerNumObj = purchase.getQuoteSellerNum();
		if(quoteSellerNumObj == null){
			log.warning("订单"+orderId+"要求报价的商家数为空！");
			throw new RuntimeException("订单"+orderId+"要求报价的商家数为空");
		}
		Integer quoteSellerNum = Integer.valueOf(quoteSellerNumObj.toString());
		System.out.println("quoteSellerNum:"+quoteSellerNum);

		Object quoteCountsObj = purchase.getQuoteCounts();
		if(quoteCountsObj == null){
			log.warning("订单"+orderId+"实际报价的商家数为空！");
			throw new RuntimeException("订单"+orderId+"实际报价的商家数为空");
		}
		Integer quoteCounts = Integer.valueOf(quoteCountsObj.toString());
		System.out.println("quoteCounts:"+quoteCounts);

		if(quoteCounts - quoteSellerNum < 0){
			Purchase updatePurchase = new Purchase();
			updatePurchase.setId(orderId);
			updatePurchase.setStatus("3");
			purchaseMapper.updateByPrimaryKeySelective(updatePurchase);
			return ;
		}else{
			//判断是否需要专家评审
			String expertReview = purchase.getExpertReview();
			if(StringUtils.isEmpty(expertReview)){
				log.warning("订单"+orderId+"是否需要专家评审为空");
				throw new RuntimeException("订单"+orderId+"是否需要专家评审为空");
			}
			log.info("订单"+orderId+",expertReview:"+expertReview);
			Purchase updatePurchase = new Purchase();
			updatePurchase.setId(orderId);
			if("true".equals(expertReview)){
				//调用另一个定时任务
				updatePurchase.setStatus("5");
				updatePurchase.setExpertTime(TimeUtil.getNowDateTime());
				purchaseMapper.updateByPrimaryKeySelective(updatePurchase);
				addJobHandler.addExpertReviewEndJob(orderId,AddJobHandler.EXPERTREVIEWDELAY);

			}else{

				updatePurchase.setStatus("4");
				updatePurchase.setBargainingTime(TimeUtil.getNowDateTime());
				purchaseMapper.updateByPrimaryKeySelective(updatePurchase);
				return ;
			}
		}






	}
}
