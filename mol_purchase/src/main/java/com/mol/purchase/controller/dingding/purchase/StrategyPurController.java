package com.mol.purchase.controller.dingding.purchase;


import com.mol.config.Constant;
import com.mol.notification.SendNotification;
import com.mol.purchase.entity.Supplier;
import com.mol.purchase.entity.SupplierSalesman;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.SubObj;
import com.mol.purchase.service.dingding.purchase.EnquiryPurchaseService;
import com.mol.purchase.service.dingding.purchase.StrategyPurchaseService;
import com.mol.purchase.service.token.TokenService;
import com.mol.purchase.util.JWTUtil;
import com.mol.quartz.handler.AddJobHandler;
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
 * 是战略
 */
@RestController
@CrossOrigin
@RequestMapping("/strategyPur")
public class StrategyPurController {

    private Logger logger = LoggerFactory.getLogger(EnquiryPurchaseController.class);

    @Autowired
    private StrategyPurchaseService strategyPurchaseService;

    @Autowired
    private EnquiryPurchaseService shoppingService;

    @Autowired
    private SendNotification sendNotification;

    @Autowired
    private TokenService tokenService;

    private AddJobHandler addJobHandler = new AddJobHandler().getInstance();

    private SendMsmHandler sendMsmHandler = SendMsmHandler.getSendMsmHandler();

    @RequestMapping(value = "/start",method = RequestMethod.POST)
    public ServiceResult<String> start(@RequestBody SubObj obj, HttpServletRequest request) throws DocumentException , IllegalAccessException, IOException {

        System.out.println("pagecontent"+ obj);
        PageArray pageArray = obj.getPageArray();
//        DDUser ddUser = JWTUtil.getUserByRequest(request);
//        String userid = ddUser.getUserid();
        String orgId = obj.getOrgId();
        String staffId = obj.getStaffId();
        StraregyObj stobj =strategyPurchaseService.save(pageArray,orgId,staffId);

        //添加定时任务：
        addJobHandler.addQuoteEndJob(stobj.getId(),stobj.getDeadLine());
        //所属行业供应商
        List<Supplier> list=strategyPurchaseService.findSupplierByPur(stobj);
        if (list.size()>0 && list!=null){
            //查询供应商下的人员
            List<SupplierSalesman> saleManList = shoppingService.findSaleManList(list);
            //查询人员的ddId
            List<String> manIdList=shoppingService.findSaleIdList(saleManList);
            //发送通知消息
            for (String s : manIdList) {
                sendNotification.sendOaFromThird(s, Constant.AGENTID_THIRDPLAT,tokenService.getMicroToken());
            }
            //查询人员的电话
            List<String> manPhoneList= shoppingService.findSalePhoneList(saleManList);
            for (String phone : manPhoneList) {
                sendMsmHandler.sendMsm(XiaoNiuMsm.SIGNNAME_MEYG, XiaoNiuMsmTemplate.提醒供应商报价模板(),phone);
            }
        }
        return ServiceResult.success("成功");
    }

    @RequestMapping(value = "/getSupplierNum")
    public ServiceResult<Integer> getSupplierNumByItemName(@RequestBody SubObj obj){
        return strategyPurchaseService.getSupplierNum(obj.getPageArray().getPurchaseArray());
    }

}