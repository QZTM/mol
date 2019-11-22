package com.mol.purchase.controller.dingding.purchase;

import com.mol.config.Constant;
import com.mol.notification.SendNotification;
import com.mol.purchase.client.QuartzClient;
import com.mol.purchase.entity.Supplier;
import com.mol.purchase.entity.SupplierSalesman;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.SubObj;
import com.mol.purchase.service.dingding.purchase.EnquiryPurchaseService;
import com.mol.purchase.service.token.TokenService;
//import com.mol.quartz.handler.AddJobHandler;
import com.mol.sms.SendMsmHandler;
import com.mol.sms.XiaoNiuMsm;
import com.mol.sms.XiaoNiuMsmTemplate;
import entity.ServiceResult;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 询价采购
 */
@RestController
@CrossOrigin
@RequestMapping("/shopping")
public class EnquiryPurchaseController {

    private Logger logger = LoggerFactory.getLogger(EnquiryPurchaseController.class);

    @Autowired
    private EnquiryPurchaseService shoppingService;


    @Autowired
    private SendNotification sendNotification;

    @Autowired
    private TokenService tokenService;


    @Autowired
    private QuartzClient quartzClient;

    private SendMsmHandler sendMsmHandler = SendMsmHandler.getSendMsmHandler();

    @RequestMapping(value = "/start",method = RequestMethod.POST)
    public ServiceResult start(@RequestBody SubObj obj, HttpServletRequest request) throws DocumentException, IllegalAccessException, IOException {

        PageArray pageArray = obj.getPageArray();
        String staid=obj.getStaffId();
        String orgId=obj.getOrgId();
        StraregyObj stobj=null;
        try{
            stobj = shoppingService.save(pageArray, staid, orgId);
        }catch (Exception e){
           e.printStackTrace();
           return ServiceResult.failureMsg("提交失败");
        }
        //添加定时任务：
        quartzClient.addquoteendjobwithendtime(stobj.getId(),stobj.getDeadLine());
        logger.info("定时任务设置成功");
        shoppingService.sendMessage(stobj,sendMsmHandler,sendNotification,XiaoNiuMsmTemplate.提醒供应商报价模板());
        return ServiceResult.successMsg("提交成功");
    }

    @RequestMapping(value = "/getSupplierNum")
    public ServiceResult<Integer> getSupplierNumByItemName(@RequestBody SubObj obj){
        return null;
        //return shoppingService.getSupplierNum(obj.getPageArray().getPurchaseArray());
    }

}
