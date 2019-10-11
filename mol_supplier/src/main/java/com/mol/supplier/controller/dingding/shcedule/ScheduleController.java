package com.mol.supplier.controller.dingding.shcedule;

import com.mol.supplier.entity.dingding.login.DDUser;
import com.mol.supplier.service.dingding.schedule.ScheduleService;
import com.mol.supplier.util.JWTUtil;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 进度控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/progress")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    private static final Logger bizLogger = LoggerFactory.getLogger(ScheduleController.class);

    @RequestMapping(value = "/getScheduleList",method = RequestMethod.POST)
    public ServiceResult getProgress(String userId, String state, HttpServletRequest request){
        System.out.println("userId:"+userId+",state:"+state);
        DDUser ddUser = JWTUtil.getUserByRequest(request);
            if(ddUser == null){
                return ServiceResult.failure("通讯异常，请稍后再试！");
            }
            userId = ddUser.getUserid();
        return scheduleService.getProgress(userId,state);
    }

    /**
     * 根据审批ID查询审批进度详情
     * @param progressId
     * @return
     */
    @RequestMapping(value = "/getProgress/{progressId}",method = RequestMethod.GET)
    public ServiceResult<String> getProgressById(@PathVariable String progressId,String userId){
        //调用ApproveService里面的根据审批实例获取审批数据的方法
        bizLogger.info("userId:"+userId);
        String pageContent = scheduleService.getProgressDetail(progressId,userId);
        return ServiceResult.success(pageContent);
    }
}
