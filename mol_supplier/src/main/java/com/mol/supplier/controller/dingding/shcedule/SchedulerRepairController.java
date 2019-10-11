package com.mol.supplier.controller.dingding.shcedule;

import com.mol.supplier.config.OrderStatus;
import com.mol.supplier.entity.activiti.ActHiComment;
import com.mol.supplier.entity.activiti.ActHiProcinst;
import com.mol.supplier.entity.dingding.login.AppAuthOrg;
import com.mol.supplier.entity.dingding.login.AppUser;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.supplier.entity.dingding.purchaseOrder.Supplier;
import com.mol.supplier.entity.dingding.solr.fyPurchase;
import com.mol.supplier.entity.thirdPlatform.FyQuote;
import com.mol.supplier.service.dingding.schedule.SchedulerRepairService;
import entity.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:SchedulerRepairController
 * Package:com.mol.supplier.controller.dingding.shcedule
 * Description
 *  E应用进度（第二次更改）
 * @date:2019/9/11 14:42
 * @author:yangjiangyan
 */
@RestController
@CrossOrigin
@RequestMapping("/scheRe")
public class SchedulerRepairController {

    @Autowired
    private SchedulerRepairService schedulerRepairService;


    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    public ServiceResult getList(String orgId, String userId){
        List<fyPurchase> purList=schedulerRepairService.getList(orgId,userId);
        return ServiceResult.success(purList);
    }

    @RequestMapping(value = "/getPur",method = RequestMethod.GET)
    public ServiceResult getPur(String id ){
        Map<String,Object> map=new HashMap<>();
        //查询订单
        fyPurchase pur=schedulerRepairService.getPurById(id);
        map.put("pur",pur);
        //获取最终选中的报价
        List<FyQuote> detailList=null;

        //公司的list
        List<Supplier> supplierList=new ArrayList<>();
        if (pur.getStatus().equals(OrderStatus.pass+"") ){
            detailList=schedulerRepairService.checkQuoteList(id);

            for (int i =0;i<detailList.size();i++){
                if (supplierList.size()>0){
                    for (int j=0;j<supplierList.size();j++){
                        if (supplierList.get(j).getPkSupplier().equals(detailList.get(i).getPkSupplierId())){
                            break;
                        }
                        if (j==supplierList.size()-1){
                            Supplier supplier=schedulerRepairService.getSupplierById(detailList.get(i).getPkSupplierId());
                            supplierList.add(supplier);
                        }
                    }
                }else {
                    Supplier supplier=schedulerRepairService.getSupplierById(detailList.get(i).getPkSupplierId());
                    supplierList.add(supplier);
                }


            }


            //报价公司
            //1if (detailList!=null && detailList.size()>0){
                //List<Supplier> supplierList
            //}
        }
        map.put("quote",detailList);
        map.put("supplier",supplierList);

        return ServiceResult.success(map);
    }

    @GetMapping("/getComment")
    public ServiceResult getComment(@RequestParam String purId){
        ActHiProcinst pro =schedulerRepairService.getComment(purId);
        if (pro!=null){
            List<ActHiComment> commentList=schedulerRepairService.getuserIdAndCommentByprocInstId(pro.getProcInstId());
            return ServiceResult.success(commentList);
        }else {
            return ServiceResult.failure("还没有进行审批！");
        }
    }

    @GetMapping("/getApproveList")
    public ServiceResult getApproveList(String orgId){
        AppAuthOrg org=schedulerRepairService.getOrg(orgId);
        String approve = org.getPurchaseApproveList();

        if (approve == null){
            return ServiceResult.failure("查询审核人异常！");
        }
        String[] split = approve.split(",");
        List<String> list= new ArrayList<>();
        for (String s : split) {
            list.add(s);
        }

        List<AppUser> userList=schedulerRepairService.getAppUserByIdList(list);
        return ServiceResult.success(userList);
    }
}
