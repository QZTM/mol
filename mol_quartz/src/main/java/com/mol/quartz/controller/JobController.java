package com.mol.quartz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mol.quartz.bean.AppQuartz;
import com.mol.quartz.bean.ReturnMsg;
import com.mol.quartz.service.AppQuartzService;
import com.mol.quartz.service.JobService;

@RestController
public class JobController {

	@Autowired
    private JobService jobService;    
    @Autowired
    private AppQuartzService appQuartzService;
    
    
    //添加一个job
    @RequestMapping(value="/addJob",method=RequestMethod.POST)
    public ReturnMsg addjob(@RequestBody AppQuartz appQuartz) throws Exception {    
        //appQuartzService.insertAppQuartzSer(appQuartz);        
    	System.out.print(appQuartz);
        String result=jobService.addJob(appQuartz);    
        if("success".equals(result)) {
        	return new ReturnMsg("200","success addjob");    
        }else {
        	return new ReturnMsg("404","fail addjob"); 
        }
    }
    
    //暂停job    
    @RequestMapping(value="/pauseJob",method=RequestMethod.POST)
    public ReturnMsg pausejob(@RequestBody Integer[]quartzIds) throws Exception {    
        AppQuartz appQuartz=null;            
        if(quartzIds.length>0){
            for(Integer quartzId:quartzIds) {
                appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
                jobService.pauseJob(appQuartz.getJobName(), appQuartz.getJobGroup().name());
            }
            return new ReturnMsg("200","success pauseJob");    
        }else {
            return new ReturnMsg("404","fail pauseJob");    
        }                                                                
    }
    
    //恢复job
    @RequestMapping(value="/resumeJob",method=RequestMethod.POST)
    public ReturnMsg resumejob(@RequestBody Integer[]quartzIds) throws Exception {    
        AppQuartz appQuartz=null;
        if(quartzIds.length>0) {
            for(Integer quartzId:quartzIds) {
                appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
                jobService.resumeJob(appQuartz.getJobName(), appQuartz.getJobGroup().name());
            }
            return new ReturnMsg("200","success resumeJob");
        }else {
            return new ReturnMsg("404","fail resumeJob");
        }            
    } 
        
    
    //删除job
    @RequestMapping(value="/deletJob",method=RequestMethod.POST)
    public ReturnMsg deletjob(@RequestBody Integer[]quartzIds) throws Exception {
        AppQuartz appQuartz=null;
        for(Integer quartzId:quartzIds) {
            appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
            String ret=jobService.deleteJob(appQuartz);
            if("success".equals(ret)) {
                appQuartzService.deleteAppQuartzByIdSer(quartzId);
            }
        }
        return new ReturnMsg("200","success deleteJob");    
    }
        
    //修改
    @RequestMapping(value="/updateJob",method=RequestMethod.POST)
    public ReturnMsg  modifyJob(@RequestBody AppQuartz appQuartz) throws Exception {
        String ret= jobService.modifyJob(appQuartz);            
        if("success".equals(ret)) {            
            appQuartzService.updateAppQuartzSer(appQuartz);
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
