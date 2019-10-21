package com.mol.purchase.controller.dingding.purchase;

import com.mol.config.Constant;
import com.mol.notification.SendNotification;
import com.mol.purchase.entity.SupplierSalesman;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import com.mol.purchase.service.dingding.purchase.EnquiryPurchaseService;
import com.mol.purchase.service.dingding.purchase.SingleSourceService;
import com.mol.purchase.entity.dingding.purchase.strategPurchaseEntity.PageArray;
import com.mol.purchase.entity.dingding.purchase.strategPurchaseEntity.subObj;
import com.mol.purchase.service.token.TokenService;
import com.mol.quartz.handler.AddJobHandler;
import com.mol.sms.SendMsmHandler;
import com.mol.sms.XiaoNiuMsm;
import com.mol.sms.XiaoNiuMsmTemplate;
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

    private SendMsmHandler sendMsmHandler = SendMsmHandler.getSendMsmHandler();

    private AddJobHandler addJobHandler = new AddJobHandler().getInstance();

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
        //添加定时任务：
        addJobHandler.addQuoteEndJob(stobj.getId(),stobj.getDeadLine());
        //所属行业供应商
        List<Supplier> list=singleSourceService.findSupplierByPur(stobj);
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
