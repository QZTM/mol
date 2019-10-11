package com.mol.purchase.controller.dingding.approve;

import com.mol.purchase.entity.dingding.purchase.onlinePurchaseEntity.PageContentOnline;
import com.mol.purchase.service.dingding.approve.OnlineApproveService;
import com.mol.purchase.util.JWTUtil;
import entity.ServiceResult;
import entity.dd.DDUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 线上审批控制层
 */
@Controller
@CrossOrigin
@RequestMapping("/onlineApprove")
public class OnlineApproveController {

    private static final Logger loggerOnline= LoggerFactory.getLogger(OnlineApproveController.class);

    @Autowired
    private OnlineApproveService onlineApproveService;

    @ResponseBody
    @RequestMapping(value = "/start" ,method = RequestMethod.POST)
    public ServiceResult<String> startSubmit(@RequestBody PageContentOnline pageObjOnline, HttpServletRequest request){
        //获取用户
        System.out.println("page:"+pageObjOnline);
        DDUser ddUser = JWTUtil.getUserByRequest(request);
        if (ddUser==null){
            return ServiceResult.failure("通讯异常，请稍后再试！");
        }

        String userId=ddUser.getUserid();
        return onlineApproveService.starApprove(pageObjOnline,userId);
    }

}
