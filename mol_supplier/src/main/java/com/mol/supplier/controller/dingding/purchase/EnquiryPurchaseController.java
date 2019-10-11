package com.mol.supplier.controller.dingding.purchase;

import com.itextpdf.text.DocumentException;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.SubObj;
import com.mol.supplier.service.dingding.purchase.EnquiryPurchaseService;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    @RequestMapping(value = "/start",method = RequestMethod.POST)
    public ServiceResult<String> start(@RequestBody SubObj obj, HttpServletRequest request) throws DocumentException, IllegalAccessException, IOException {

        System.out.println("pagecontent"+ obj);
        PageArray pageArray = obj.getPageArray();
//        DDUser ddUser = JWTUtil.getUserByRequest(request);
//        String userid = ddUser.getUserid();
        String staid=obj.getStaffId();
        String orgId=obj.getOrgId();
        return shoppingService.save(pageArray,staid,orgId);
    }

    @RequestMapping(value = "/getSupplierNum")
    public ServiceResult<Integer> getSupplierNumByItemName(@RequestBody SubObj obj){
        return null;
        //return shoppingService.getSupplierNum(obj.getPageArray().getPurchaseArray());
    }

}
