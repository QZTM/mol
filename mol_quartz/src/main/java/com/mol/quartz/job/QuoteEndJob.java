package com.mol.quartz.job;

import com.mol.quartz.entity.Purchase;
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
import tk.mybatis.mapper.entity.Example;

import java.util.Iterator;
import java.util.Map;
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


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDataMap data=context.getTrigger().getJobDataMap();
		Map invokeParam = (Map)data.get("invokeParam");
		System.out.print("JobTwo执行,参数：");
		Set set = invokeParam.keySet();
		Iterator iterator = set.iterator();
		if(iterator.hasNext()){
			String mapKey = iterator.next().toString();
			System.out.println("key:"+mapKey+",,,"+invokeParam.get(mapKey));
		}

		Object orderIdObj = invokeParam.get("orderId");
		if(orderIdObj == null){
			log.warning("报价结束定时任务------找不到orderId");
			throw new RuntimeException("需要处理的报价结束任务参数异常------找不到orderId");
		}
		String orderId = (String)orderIdObj;

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
			purchaseMapper.updateByPrimaryKey(updatePurchase);
			return ;
		}else{
			//判断是否需要专家评审
			String expertReview = purchase.getExpertReview();
			if(StringUtils.isEmpty(expertReview)){
				log.warning("订单"+orderId+"是否需要专家评审为空");
				throw new RuntimeException("订单"+orderId+"是否需要专家评审为空");
			}
			log.info("订单"+orderId+",expertReview:"+expertReview);
			if("true".equals(expertReview)){
				//调用另一个定时任务
			}else{
				Purchase updatePurchase = new Purchase();
				updatePurchase.setId(orderId);
				updatePurchase.setStatus("4");
				purchaseMapper.updateByPrimaryKey(updatePurchase);
				return ;
			}
		}






	}
}
