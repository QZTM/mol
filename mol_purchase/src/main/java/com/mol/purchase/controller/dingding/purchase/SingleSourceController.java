package com.mol.purchase.controller.dingding.purchase;

import com.mol.config.Constant;
import com.mol.notification.SendNotification;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import com.mol.purchase.service.dingding.purchase.EnquiryPurchaseService;
import com.mol.purchase.service.dingding.purchase.SingleSourceService;
import com.mol.purchase.entity.dingding.purchase.strategPurchaseEntity.PageArray;
import com.mol.purchase.entity.dingding.purchase.strategPurchaseEntity.subObj;
import com.mol.purchase.service.token.TokenService;
import entity.ServiceResult;
import com.mol.purchase.entity.Supplier;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 单一来源
 */
@RestController
@CrossOrigin
@RequestMapping("/single")
public class SingleSourceController {
    private Logger logger = LoggerFactory.getLogger(StrategyPurController.class);

    @Autowired
    private SingleSourceService singleSourceService;

    @Autowired
    private EnquiryPurchaseService shoppingService;


    @Autowired
    private SendNotification sendNotification;

    @Autowired
    private TokenService tokenService;
    /**
     * 保存单一采购的采购物品
     * @param obj
     * @param request
     * @return
     */
    @RequestMapping(value = "/start",method = RequestMethod.POST)
    public ServiceResult<String> start(@RequestBody subObj obj, HttpServletRequest request) throws IOException, DocumentException, IllegalAccessException {
        System.out.println("pagecontent"+ obj);
        PageArray pageArray = obj.getPageArray();
//        DDUser ddUser = JWTUtil.getUserByRequest(request);
//        String userid = ddUser.getUserid();
        String staid=obj.getStaffId();
        String orgId=obj.getOrgId();
        StraregyObj stobj = singleSourceService.save(pageArray,staid,orgId);
        //所属行业供应商
        List<Supplier> list=singleSourceService.findSupplierByPur(stobj);
        //查询供应商下的人员的ddid
        List<String> manList= shoppingService.findSaleList(list);
        //发送通知消息
        for (String s : manList) {
            sendNotification.sendOaFromThird(s, Constant.AGENTID_THIRDPLAT,tokenService.getMicroToken());
        }

        return ServiceResult.success("成功");
    }

    /**
     * 获得智能推荐的供应商和名称
     * @param obj
     * @return
     */
    @RequestMapping(value = "/getSupplier",method = RequestMethod.POST)
    public ServiceResult<Supplier> getNameAndPhone(@RequestBody subObj obj ){
        if (obj==null){
            return null;
        }

        return singleSourceService.getSupplier(obj.getPageArray().getPurchaseArray());
    }


}
