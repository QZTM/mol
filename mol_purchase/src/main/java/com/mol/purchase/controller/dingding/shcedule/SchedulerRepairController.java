package com.mol.purchase.controller.dingding.shcedule;

import com.alipay.api.domain.Sale;
import com.github.pagehelper.PageInfo;
import com.mol.oos.OOSConfig;
import com.mol.purchase.config.OrderStatus;
import com.mol.purchase.entity.FyQuote;
import com.mol.purchase.entity.QuotePayresult;
import com.mol.purchase.entity.SupplierSalesman;
import com.mol.purchase.entity.activiti.ActHiActinst;
import com.mol.purchase.entity.activiti.ActHiComment;
import com.mol.purchase.entity.activiti.ActHiProcinst;
import com.mol.purchase.entity.dingding.login.AppAuthOrg;
import com.mol.purchase.entity.dingding.login.AppUser;
import com.mol.purchase.entity.dingding.solr.fyPurchase;
import com.mol.purchase.service.dingding.schedule.SchedulerRepairService;
import entity.ServiceResult;
import com.mol.purchase.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:SchedulerRepairController
 * Package:com.purchase.controller.dingding.shcedule
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
    public ServiceResult getList(String orgId, String userId,int pageNum,int pageSize){
        List<fyPurchase> purList=schedulerRepairService.getList(orgId,userId,pageNum,pageSize);
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
        if (pur.getStatus().equals(OrderStatus.PASS+"") ){
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
    public ServiceResult getApproveList(String purId){
//        AppAuthOrg org=schedulerRepairService.getOrg(orgId);
//        String approve = org.getPurchaseApproveList();
//
//        if (approve == null){
//            return ServiceResult.failure("查询审核人异常！");
//        }
//        String[] split = approve.split(",");
//        List<String> list= new ArrayList<>();
//        for (String s : split) {
//            list.add(s);
//        }
//
//        List<AppUser> userList=schedulerRepairService.getAppUserByIdList(list);
//        return ServiceResult.success(userList);
        if (purId==null){
            return ServiceResult.failureMsg("订单id传递失败！");
        }
        List<ActHiActinst> list=schedulerRepairService.getActHiActinstByPurId(purId);
        List<AppUser> userList=schedulerRepairService.getAppUserByList(list);
        return ServiceResult.success(userList);
    }

    /**
     * 联系供应商
     * @param purId
     * @param supplierId
     * @return
     */
    @GetMapping("/getSaleMan")
    public ServiceResult getSaleman(String purId,String supplierId){
        if (purId ==null || supplierId==null){
            return ServiceResult.failure("查询失败！");
        }
        String id =schedulerRepairService.getSalemanId(purId,supplierId);
        SupplierSalesman man=schedulerRepairService.getSaleManById(id);
        return ServiceResult.success(man);
    }

    /**
     * 供应商支付专家推荐费用情况
     * @param supplierId
     * @param purId
     * @return
     */
    @GetMapping("/getPayresult")
    public ServiceResult getPayR(String supplierId,String purId){
        QuotePayresult qp=schedulerRepairService.getPayExpertResult(supplierId,purId);
        if (qp ==null){
            return ServiceResult.failureMsg("查询失败！");
        }
        return ServiceResult.success(qp);
    }

    /**
     * 保存 合同图片
     * @param file 图片
     * @param purId 订单Id
     * @param orgId 公司id
     * @return
     */
    @PostMapping("/upLoadContractPictures")
    public ServiceResult upLoadContractPictures(@RequestParam("file") MultipartFile file, String purId,String orgId){

        if (file==null){
            return ServiceResult.failureMsg("文件接收失败");
        }
        schedulerRepairService.upload(file,orgId,purId);
        return ServiceResult.successMsg("上传成功");
    }

    /**
     * 下载合同
     * @param bucket 桶名称
     * @param key   文件名称
     * @return
     */
    @GetMapping("/downLoadContractPictures")
    public ServiceResult downLoadOOSConTractPictures(String bucket,String key){

        schedulerRepairService.downFromOOSImg(bucket,key);
        return null;
    }
}
