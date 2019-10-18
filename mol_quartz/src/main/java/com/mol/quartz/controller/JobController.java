package com.mol.quartz.controller;

import com.mol.quartz.config.MolJob;
import com.mol.quartz.entity.Quartz;
import com.mol.quartz.handler.AddJobHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mol.quartz.entity.ReturnMsg;
import com.mol.quartz.service.QuartzService;
import com.mol.quartz.service.JobService;
import util.TimeUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JobController {

	@Autowired
    private JobService jobService;    
    @Autowired
    private QuartzService appQuartzService;

    @Autowired
    private AddJobHandler addJobHandler;


    @RequestMapping("/testAdd")
    public void test(){
        try{
            addJobHandler.addExpertReviewEndJob("1184292704515919872","2019-10-18 16:12");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    //添加一个job
    @RequestMapping(value="/addJob",method=RequestMethod.POST)
    public ReturnMsg addjob(@RequestBody Quartz quartz) throws Exception {
        //appQuartzService.insertAppQuartzSer(quartz);
    	System.out.print(quartz);
        String result=jobService.addJob(quartz);
        if("success".equals(result)) {
        	return new ReturnMsg("200","success addjob");    
        }else {
        	return new ReturnMsg("404","fail addjob"); 
        }
    }

    //暂停job
    @RequestMapping(value="/pauseJob",method=RequestMethod.POST)
    public ReturnMsg pausejob(@RequestBody Integer[]quartzIds) throws Exception {    
        Quartz quartz =null;
        if(quartzIds.length>0){
            for(Integer quartzId:quartzIds) {
                quartz =appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
                jobService.pauseJob(quartz.getJobName(), quartz.getJobGroup().name());
            }
            return new ReturnMsg("200","success pauseJob");    
        }else {
            return new ReturnMsg("404","fail pauseJob");    
        }                                                                
    }
    
    //恢复job
    @RequestMapping(value="/resumeJob",method=RequestMethod.POST)
    public ReturnMsg resumejob(@RequestBody Integer[]quartzIds) throws Exception {    
        Quartz quartz =null;
        if(quartzIds.length>0) {
            for(Integer quartzId:quartzIds) {
                quartz =appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
                jobService.resumeJob(quartz.getJobName(), quartz.getJobGroup().name());
            }
            return new ReturnMsg("200","success resumeJob");
        }else {
            return new ReturnMsg("404","fail resumeJob");
        }            
    } 
        
    
    //删除job
    @RequestMapping(value="/deletJob",method=RequestMethod.POST)
    public ReturnMsg deletjob(@RequestBody Integer[]quartzIds) throws Exception {
        Quartz quartz =null;
        for(Integer quartzId:quartzIds) {
            quartz =appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
            String ret=jobService.deleteJob(quartz);
            if("success".equals(ret)) {
                appQuartzService.deleteAppQuartzByIdSer(quartzId);
            }
        }
        return new ReturnMsg("200","success deleteJob");    
    }
        
    //修改
    @RequestMapping(value="/updateJob",method=RequestMethod.POST)
    public ReturnMsg  modifyJob(@RequestBody Quartz quartz) throws Exception {
        String ret= jobService.modifyJob(quartz);
        if("success".equals(ret)) {            
            appQuartzService.updateAppQuartzSer(quartz);
            return new ReturnMsg("200","success updateJob",ret);
        }else {
            return new ReturnMsg("404","fail updateJob",ret);
        }                
    }
    
    //暂停所有
    @RequestMapping(value="/pauseAll",method=RequestMethod.GET)
    public ReturnMsg pauseAllJob() throws Exception {
        jobService.pauseAllJob();
        return new ReturnMsg("200","success pauseAll");
    }
    
    //恢复所有
    @RequestMapping(value="/repauseAll",method=RequestMethod.GET)
    public ReturnMsg repauseAllJob() throws Exception {
        jobService.resumeAllJob();
        return new ReturnMsg("200","success repauseAll");
    }    
}
