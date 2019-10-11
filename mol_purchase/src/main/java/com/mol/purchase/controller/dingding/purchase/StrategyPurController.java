package com.mol.purchase.controller.dingding.purchase;


import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.SubObj;
import com.mol.purchase.service.dingding.purchase.StrategyPurchaseService;
import com.mol.purchase.util.JWTUtil;
import entity.ServiceResult;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    @RequestMapping(value = "/start",method = RequestMethod.POST)
    public ServiceResult<String> start(@RequestBody SubObj obj, HttpServletRequest request) throws DocumentException , IllegalAccessException, IOException {

        System.out.println("pagecontent"+ obj);
        PageArray pageArray = obj.getPageArray();
//        DDUser ddUser = JWTUtil.getUserByRequest(request);
//        String userid = ddUser.getUserid();
        String orgId = obj.getOrgId();
        String staffId = obj.getStaffId();
        return strategyPurchaseService.save(pageArray,orgId,staffId);
    }

    @RequestMapping(value = "/getSupplierNum")
    public ServiceResult<Integer> getSupplierNumByItemName(@RequestBody SubObj obj){
        return strategyPurchaseService.getSupplierNum(obj.getPageArray().getPurchaseArray());
    }

}