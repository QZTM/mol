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
public class QuoteEndJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDataMap data=context.getTrigger().getJobDataMap();
		
	}

}
