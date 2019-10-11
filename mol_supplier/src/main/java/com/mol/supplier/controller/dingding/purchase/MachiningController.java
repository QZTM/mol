package com.mol.supplier.controller.dingding.purchase;

import com.itextpdf.text.DocumentException;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.SubObj;
import com.mol.supplier.service.dingding.purchase.MachiningService;
import entity.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ClassName:MachiningController
 * Package:com.mol.supplier.controller.dingding.purchase
 * Description
 *   加工
 * @date:2019/9/11 9:12
 * @author:yangjiangyan
 */
@RestController
@CrossOrigin
@RequestMapping("/machining")
public class MachiningController {

    @Autowired
    private MachiningService machiningService;

    @RequestMapping(value = "/start",method = RequestMethod.POST)
    public ServiceResult<String> start(@RequestBody SubObj obj, HttpServletRequest request) throws DocumentException, IllegalAccessException, IOException {


        PageArray pageArray = obj.getPageArray();
        String staid=obj.getStaffId();
        String orgId=obj.getOrgId();
        String type=obj.getType();

        return machiningService.save(pageArray,staid,orgId,type);
    }
}
