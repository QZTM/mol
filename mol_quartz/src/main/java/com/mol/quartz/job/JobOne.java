package com.mol.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;


@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class JobOne implements Job{

	 @Override
	    public void execute(JobExecutionContext context) throws JobExecutionException{
	        JobDataMap data=context.getTrigger().getJobDataMap();
	        String invokeParam =(String) data.get("invokeParam");
	        System.out.println("JobOne执行");
	        //在这里实现业务逻辑
	        }
	
}
