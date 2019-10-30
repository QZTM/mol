package com.mol.purchase.controller.dingding.purchase;

import com.mol.purchase.service.dingding.purchase.MachiningService;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.SubObj;
import entity.ServiceResult;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ClassName:MachiningController
 * Package:com.purchase.controller.dingding.purchase
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
    public ServiceResult start(@RequestBody SubObj obj, HttpServletRequest request) throws DocumentException, IllegalAccessException, IOException {


        PageArray pageArray = obj.getPageArray();
        String staid=obj.getStaffId();
        String orgId=obj.getOrgId();
        String type=obj.getType();
        try{
            machiningService.save(pageArray,staid,orgId,type);
        }catch (Exception e){
            e.printStackTrace();
            return ServiceResult.failureMsg("提交失败");
        }
        return ServiceResult.successMsg("提交成功");
    }
}
