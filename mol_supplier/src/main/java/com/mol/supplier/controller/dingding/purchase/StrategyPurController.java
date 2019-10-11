package com.mol.supplier.controller.dingding.purchase;


import com.itextpdf.text.DocumentException;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.SubObj;
import com.mol.supplier.service.dingding.purchase.StrategyPurchaseService;
import entity.ServiceResult;
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
    public ServiceResult<String> start(@RequestBody SubObj obj, HttpServletRequest request) throws DocumentException, IllegalAccessException, IOException {

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