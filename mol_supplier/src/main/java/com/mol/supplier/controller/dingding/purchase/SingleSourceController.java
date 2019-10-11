package com.mol.supplier.controller.dingding.purchase;

import com.itextpdf.text.DocumentException;
import com.mol.supplier.entity.dingding.purchase.strategPurchaseEntity.PageArray;
import com.mol.supplier.entity.dingding.purchase.strategPurchaseEntity.subObj;
import com.mol.supplier.entity.dingding.purchaseOrder.Supplier;
import com.mol.supplier.service.dingding.purchase.SingleSourceService;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
        return singleSourceService.save(pageArray,staid,orgId);
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
