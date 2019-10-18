package com.mol.quartz.service;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mol.quartz.bean.AppQuartz;
import com.mol.quartz.config.MolJob;
import com.mol.quartz.job.JobOne;
import com.mol.quartz.job.JobTwo;

@Service
public class JobService {

	
	@Autowired
    private Scheduler scheduler;
	
	
	 /**
     * 新建一个任务
     * 
     */     
    public String addJob(AppQuartz appQuartz) throws Exception  {
        
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=df.parse(appQuartz.getStartTime());
        
            if (!CronExpression.isValidExpression(appQuartz.getCronExpression())) {           
               return "Illegal cron expression";   //表达式格式不正确
           }                            
           JobDetail jobDetail=null;
           //构建job信息
           if(MolJob.QUOTEENDJOB == appQuartz.getJobGroup()) {
                jobDetail = JobBuilder.newJob(JobOne.class).withIdentity(appQuartz.getJobName(), MolJob.QUOTEENDJOB.getDesc()).build();
           }
           if(MolJob.EXPERTREVIEWJOB == appQuartz.getJobGroup()) {
                jobDetail = JobBuilder.newJob(JobTwo.class).withIdentity(appQuartz.getJobName(), MolJob.EXPERTREVIEWJOB.getDesc()).build();
           }
                   
           //表达式调度构建器(即任务执行的时间,不立即执行)
           CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(appQuartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();

           //按新的cronExpression表达式构建一个新的trigger
           CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(appQuartz.getJobName(), appQuartz.getJobGroup().name()).startAt(date)
                   .withSchedule(scheduleBuilder).build();
                                
           //传递参数
           if(appQuartz.getInvokeParam()!=null && !"".equals(appQuartz.getInvokeParam())) {
               trigger.getJobDataMap().putAll(appQuartz.getInvokeParam());    
           }                                
           scheduler.scheduleJob(jobDetail, trigger);
          // pauseJob(appQuartz.getJobName(),appQuartz.getJobGroup());
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
       public String  deleteJob(AppQuartz appQuartz) throws SchedulerException {            
           JobKey jobKey = new JobKey(appQuartz.getJobName(), appQuartz.getJobGroup().name());
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
       public String  modifyJob(AppQuartz appQuartz) throws SchedulerException {            
           if (!CronExpression.isValidExpression(appQuartz.getCronExpression())) {
               return "Illegal cron expression";
           }
           TriggerKey triggerKey = TriggerKey.triggerKey(appQuartz.getJobName(),appQuartz.getJobGroup().name());
           JobKey jobKey = new JobKey(appQuartz.getJobName(),appQuartz.getJobGroup().name());
           if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
               CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);            
               //表达式调度构建器,不立即执行
               CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(appQuartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();
               //按新的cronExpression表达式重新构建trigger
               trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                   .withSchedule(scheduleBuilder).build();
               //修改参数
               if(!trigger.getJobDataMap().get("invokeParam").equals(appQuartz.getInvokeParam())) {
                   trigger.getJobDataMap().put("invokeParam",appQuartz.getInvokeParam());
               }                
               //按新的trigger重新设置job执行
               scheduler.rescheduleJob(triggerKey, trigger);                                                
               return "success";                    
           }else {
               return "job or trigger not exists";
           }    
           
       }
	
	
}
