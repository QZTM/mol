package com.mol.quartz.handler;

import com.mol.quartz.config.MolJob;
import com.mol.quartz.entity.Quartz;
import com.mol.quartz.service.JobService;
import lombok.extern.java.Log;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.TimeUtil;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Log
public class AddJobHandler {



    //public static final Long EXPERTREVIEWDELAY = 2*24*60*60L;

    public static final Long EXPERTREVIEWDELAY = 90L;


    @Autowired
    private Scheduler scheduler;


    @Autowired
    private JobService jobService;



    public AddJobHandler getInstance(){
        return this;
    }


    /**
     * 添加供应商报价截止事件
     * @param orderId               采购订单ID
     * @param quoteEndDate          供应商报价截止时间，格式为   XXXX-XX-XX XX:XX   或者  XXXX-XX-XX XX:XX:XX两种格式
     * @return                      添加成功会返回success
     * @throws Exception
     */
    public String addQuoteEndJob(String orderId,String quoteEndDate)  {
        log.info("添加供应商报价截止事件：orderId:"+orderId+",quoteEndDate:"+quoteEndDate);
        if(StringUtils.isEmpty(orderId)){
            log.warning("添加供应商报价截止事件出错，orderId为空");
            throw new RuntimeException("添加供应商报价截止事件出错，orderId不该为空");
        }
        if(quoteEndDate.length()<17){
            quoteEndDate = quoteEndDate+":00";
        }
        System.out.println("quoteEndDate:"+quoteEndDate);
        Map paraMap = new HashMap();
        paraMap.put("orderId",orderId);
        try {
            jobService.addJob(new Quartz()
                    .setQuartzId(RandomStringUtils.random(10)+"----"+orderId)
                    .setJobName("订单报价截止事件--"+orderId)
                    .setStartTime(TimeUtil.getNow())
                    .setCronExpression(TimeUtil.getCron(quoteEndDate))
                    .setJobGroup(MolJob.QUOTEENDJOB)
                    .setInvokeParam(paraMap));
        } catch (Exception e) {
            log.warning("添加定时任务出错... orderId:"+orderId+",quoteEndDate:"+quoteEndDate);
            e.printStackTrace();
        }
        log.info("添加供应商报价截止事件：orderId:"+orderId+"成功！");
        return "success";
    }


    /**
     * 添加专家推荐截止事件
     * @param orderId               采购订单ID
     * @param delaySeconds          延时时间，即从当前时间延时多长时间执行，单位为秒
     * @return                      添加成功返回"success"
     * @throws Exception
     */
    public String addExpertReviewEndJob(String orderId,Long delaySeconds) {
        log.info("添加专家推荐截止事件：orderId:"+orderId+",延时:"+delaySeconds+"秒执行");
        if(StringUtils.isEmpty(orderId)){
            log.warning("添加专家推荐截止事件出错，orderId为空");
            throw new RuntimeException("添加专家推荐截止事件出错，orderId不该为空");
        }
        if(delaySeconds == null || delaySeconds == 0L){
            log.warning("添加专家推荐截止事件出错，延时时间（delaySeconds）为空");
            throw new RuntimeException("添加专家推荐截止事件出错，延时时间（delaySeconds）不该为空");
        }
        Map paraMap = new HashMap();
        paraMap.put("orderId",orderId);
        try {
            jobService.addJob(new Quartz()
                    .setQuartzId(RandomStringUtils.random(10)+"----"+orderId)
                    .setJobName("专家推荐截止事件--"+orderId)
                    .setStartTime(TimeUtil.getNowDateTime())
                    .setCronExpression(TimeUtil.getCron(TimeUtil.LocalDateTimeToDate(LocalDateTime.now().plusSeconds(delaySeconds))))
                    .setJobGroup(MolJob.EXPERTREVIEWJOB)
                    .setInvokeParam(paraMap));
        } catch (Exception e) {
            log.warning("添加专家推荐截止事件出错....orderId:"+orderId);
            e.printStackTrace();
        }
        log.info("添加专家推荐截止事件：orderId:"+orderId+"成功！");
        return "success";
    }

    /**
     * 添加专家推荐截止事件
     * @param orderId           采购订单ID
     * @param endTime           专家推荐截止时间，格式为   XXXX-XX-XX XX:XX   或者  XXXX-XX-XX XX:XX:XX两种格式
     * @return                  添加成功返回"success"
     */
    public String addExpertReviewEndJob(String orderId,String endTime) throws Exception {
        log.info("添加专家推荐截止事件：orderId:"+orderId+",执行时间:"+endTime);
        if(StringUtils.isEmpty(orderId)){
            log.warning("添加专家推荐截止事件出错，orderId为空");
            throw new RuntimeException("添加专家推荐截止事件出错，orderId不该为空");
        }
        if(StringUtils.isEmpty(endTime)){
            log.warning("添加专家推荐截止事件出错，结束时间（endTime）为空");
            throw new RuntimeException("添加专家推荐截止事件出错，结束时间（endTime）不该为空");
        }
        if(endTime.length()<17){
            endTime = endTime+":00";
        }
        Map paraMap = new HashMap();
        paraMap.put("orderId",orderId);
        jobService.addJob(new Quartz()
                .setQuartzId(RandomStringUtils.random(10)+"----"+orderId)
                .setJobName("专家推荐截止事件--"+orderId)
                .setStartTime(TimeUtil.getNowDateTime())
                .setCronExpression(TimeUtil.getCron(endTime))
                .setJobGroup(MolJob.EXPERTREVIEWJOB)
                .setInvokeParam(paraMap));
        log.info("添加专家推荐截止事件：orderId:"+orderId+"成功！");
        return "success";
    }

}
