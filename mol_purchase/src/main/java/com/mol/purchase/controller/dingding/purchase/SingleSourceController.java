package com.mol.purchase.controller.dingding.purchase;

import com.mol.config.Constant;
import com.mol.notification.SendNotification;
import com.mol.purchase.client.QuartzClient;
import com.mol.purchase.entity.SupplierSalesman;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import com.mol.purchase.service.dingding.purchase.EnquiryPurchaseService;
import com.mol.purchase.service.dingding.purchase.SingleSourceService;
import com.mol.purchase.entity.dingding.purchase.strategPurchaseEntity.PageArray;
import com.mol.purchase.entity.dingding.purchase.strategPurchaseEntity.subObj;
import com.mol.purchase.service.token.TokenService;
//import com.mol.quartz.handler.AddJobHandler;
import com.mol.purchase.util.FindFirstMarbasclassByMaterialUtils;
import com.mol.sms.SendMsmHandler;
import com.mol.sms.XiaoNiuMsm;
import com.mol.sms.XiaoNiuMsmTemplate;
import entity.BdMarbasclass;
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

    @Autowired
    private QuartzClient quartzClient;

    private SendMsmHandler sendMsmHandler = SendMsmHandler.getSendMsmHandler();

    //private AddJobHandler addJobHandler = new AddJobHandler().getInstance();

    /**
     * 保存单一采购的采购物品
     * @param obj
     * @param request
     * @return
     */
    @RequestMapping(value = "/start",method = RequestMethod.POST)
    public ServiceResult start(@RequestBody subObj obj, HttpServletRequest request) throws IOException, DocumentException, IllegalAccessException {
        System.out.println("pagecontent"+ obj);
        PageArray pageArray = obj.getPageArray();
        String staid=obj.getStaffId();
        String orgId=obj.getOrgId();
        StraregyObj stobj=null;
        try{
            if(pageArray.getPkSupplier()==null){
                return ServiceResult.failureMsg("没有单一来源供应商");
            }
            stobj = singleSourceService.save(pageArray,staid,orgId);
        }catch (Exception e){
            e.printStackTrace();
            return ServiceResult.failureMsg("提交失败");
        }
        //添加定时任务：
        quartzClient.addquoteendjobwithendtime(stobj.getId(),stobj.getDeadLine());

        String pkSupplier = stobj.getPkSupplier();
        List<SupplierSalesman> saleManList=singleSourceService.findSaleManListBySupplierId(pkSupplier);
        //所属行业供应商
        //List<Supplier> list=singleSourceService.findSupplierByPur(stobj);
        if (saleManList.size()>0 && saleManList!=null){
            //查询供应商下的人员
            //List<SupplierSalesman> saleManList = shoppingService.findSaleManList(list);
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
        return ServiceResult.successMsg("提交成功");
    }

    /**
     * 获得智能推荐的供应商和名称
     * @param
     * @return
     */
    @RequestMapping(value = "/getSupplier",method = RequestMethod.GET)
    public ServiceResult getNameAndPhone(String pkmaClass){
        if (pkmaClass==null){
            return null;
        }

        BdMarbasclass bm = singleSourceService.getBdMarBasclssByPkMaclass(pkmaClass);
        bm = singleSourceService.getBm(bm);
        List<Supplier> supplier = singleSourceService.getSupplier(bm.getPkMarbasclass());
        return ServiceResult.success("查询成功",supplier);
    }


}
