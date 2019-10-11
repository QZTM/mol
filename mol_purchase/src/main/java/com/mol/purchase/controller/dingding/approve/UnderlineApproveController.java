package com.mol.purchase.controller.dingding.approve;
import com.mol.purchase.entity.dingding.purchase.underlinePurchasePageEntity.PageContent;
import com.mol.purchase.service.dingding.approve.UnderlineApproveService;
import com.mol.purchase.util.JWTUtil;
import entity.ServiceResult;
import entity.dd.DDUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 线下审批控制器
 */
@RestController
@RequestMapping("/underApprove")
@CrossOrigin
public class UnderlineApproveController {

    private static final Logger bizLogger = LoggerFactory.getLogger(UnderlineApproveController.class);
    @Autowired
    private UnderlineApproveService underlineApproveService;


    /**
     * 发起审批
     */
    @ResponseBody
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ServiceResult<String> startApprove(@RequestBody PageContent pageObjs, HttpServletRequest request) {
        DDUser ddUser = JWTUtil.getUserByRequest(request);
        if(ddUser == null){
            //重新获取用户逻辑待完善！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        }
        String userId = ddUser.getUserid();
        bizLogger.info("startApprove()..userId:"+userId);
        return underlineApproveService.startApprove(pageObjs,userId);
    }
}
