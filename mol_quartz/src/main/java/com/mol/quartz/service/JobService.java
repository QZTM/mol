package com.mol.quartz.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mol.quartz.entity.Quartz;
import com.mol.quartz.job.QuoteEndJob;
import lombok.extern.java.Log;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mol.quartz.config.MolJob;
import com.mol.quartz.job.JobTwo;
import util.TimeUtil;

@Service
@Log
public class JobService {

	
	@Autowired
    private Scheduler scheduler;




	
	 /**
     * 新建一个任务
     * 
     */     
    public String addJob(Quartz quartz) throws Exception  {
        
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=df.parse(quartz.getStartTime());
        
            if (!CronExpression.isValidExpression(quartz.getCronExpression())) {
               return "Illegal cron expression";   //表达式格式不正确
           }                            
           JobDetail jobDetail=null;
           //构建job信息
           if(MolJob.QUOTEENDJOB == quartz.getJobGroup()) {
                jobDetail = JobBuilder.newJob(QuoteEndJob.class).withIdentity(quartz.getJobName(), MolJob.QUOTEENDJOB.getDesc()).build();
           }
           if(MolJob.EXPERTREVIEWJOB == quartz.getJobGroup()) {
                jobDetail = JobBuilder.newJob(JobTwo.class).withIdentity(quartz.getJobName(), MolJob.EXPERTREVIEWJOB.getDesc()).build();
           }
                   
           //表达式调度构建器(即任务执行的时间,不立即执行)
           CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression()).withMisfireHandlingInstructionFireAndProceed();

           //按新的cronExpression表达式构建一个新的trigger
           CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartz.getJobName(), quartz.getJobGroup().name()).startAt(date)
                   .withSchedule(scheduleBuilder).build();

            System.out.println(quartz.getInvokeParam());
           //传递参数
           if(quartz.getInvokeParam()!=null && !"".equals(quartz.getInvokeParam())) {
               trigger.getJobDataMap().putAll(quartz.getInvokeParam());
           }                                
           scheduler.scheduleJob(jobDetail, trigger);
          // pauseJob(quartz.getJobName(),quartz.getJobGroup());
           return "success";
       } 
        /**
         * 获取Job状态
         * @param jobName
         * @param jobGroup
         * @return
         * @throws SchedulerException
         */
        public String getJobState(String jobName, String jobGroup) throws SchedulerException {
            TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);    
            return scheduler.getTriggerState(triggerKey).name();
          }
        
        //暂停所有任务
        public void pauseAllJob() throws SchedulerException {            
            scheduler.pauseAll();
         }
       
       //暂停任务
       public String pauseJob(String jobName, String jobGroup) throws SchedulerException {
           JobKey jobKey = new JobKey(jobName, jobGroup);
           JobDetail jobDetail = scheduler.getJobDetail(jobKey);
           if (jobDetail == null) {
                return "fail";
           }else {
                scheduler.pauseJob(jobKey);
                return "success";
           }
                                        
       }
       
       //恢复所有任务
       public void resumeAllJob() throws SchedulerException {
           scheduler.resumeAll();
       }
       
       // 恢复某个任务
       public String resumeJob(String jobName, String jobGroup) throws SchedulerException {
           
           JobKey jobKey = new JobKey(jobName, jobGroup);
           JobDetail jobDetail = scheduler.getJobDetail(jobKey);
           if (jobDetail == null) {
               return "fail";
           }else {               
               scheduler.resumeJob(jobKey);
               return "success";
           }
       }
       
       //删除某个任务
       public String  deleteJob(Quartz quartz) throws SchedulerException {
           JobKey jobKey = new JobKey(quartz.getJobName(), quartz.getJobGroup().name());
           JobDetail jobDetail = scheduler.getJobDetail(jobKey);
           if (jobDetail == null ) {
                return "jobDetail is null";
           }else if(!scheduler.checkExists(jobKey)) {
               return "jobKey is not exists";
           }else {
                scheduler.deleteJob(jobKey);
                return "success";
           }  
          
       }
       
       //修改任务
       public String  modifyJob(Quartz quartz) throws SchedulerException {
           if (!CronExpression.isValidExpression(quartz.getCronExpression())) {
               return "Illegal cron expression";
           }
           TriggerKey triggerKey = TriggerKey.triggerKey(quartz.getJobName(), quartz.getJobGroup().name());
           JobKey jobKey = new JobKey(quartz.getJobName(), quartz.getJobGroup().name());
           if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
               CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);            
               //表达式调度构建器,不立即执行
               CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();
               //按新的cronExpression表达式重新构建trigger
               trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                   .withSchedule(scheduleBuilder).build();
               //修改参数
               if(!trigger.getJobDataMap().get("invokeParam").equals(quartz.getInvokeParam())) {
                   trigger.getJobDataMap().put("invokeParam", quartz.getInvokeParam());
               }                
               //按新的trigger重新设置job执行
               scheduler.rescheduleJob(triggerKey, trigger);                                                
               return "success";                    
           }else {
               return "job or trigger not exists";
           }    
           
       }











	
	
}
