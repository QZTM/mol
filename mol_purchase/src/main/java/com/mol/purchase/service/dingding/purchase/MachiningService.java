package com.mol.purchase.service.dingding.purchase;

import com.alibaba.fastjson.JSON;
import com.mol.purchase.entity.BdMaterialRepair;
import com.mol.purchase.mapper.newMysql.dingding.purchase.BdMaterialRepairMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.purchase.config.BuyChannelResource;
import com.mol.purchase.config.OrderStatus;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseArray;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper;
import entity.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.TimeUtil;

import java.util.List;

import static com.mol.purchase.config.Constant.orederStartNum;

/**
 * ClassName:MachiningService
 * Package:com.purchase.service.dingding.purchase
 * Description
 *  加工
 * @date:2019/9/11 9:14
 * @author:yangjiangyan
 */
@Service
public class MachiningService {

    @Autowired
    private fyPurchaseMapper purchaseMapper;

    @Autowired
    private BdMaterialRepairMapper bdMaterialRepairMapper;

    @Autowired
    private fyPurchaseDetailMapper fyPurchaseDetailMapper;

    @Autowired
    private IdWorker idWorker;


    @Transactional
    public ServiceResult<String> save(PageArray pageArray, String staId, String orgId, String type) {
        //申请事由
        String applyCause = pageArray.getApplyCause();
        //采购详情
        List<PurchaseArray> purchaseList = pageArray.getPurchaseArray();
        //报价商家
        int quoteSellerNum = pageArray.getQuoteSellerNum();
        //零配件供应商数
        int supplierSellerNum = pageArray.getSupplierSellerNum();
        //备注
        String remarks = pageArray.getRemarks();
        //截止时间
        String deadLine = pageArray.getDeadLine();
        //供货周期
        String supplyCycle = pageArray.getSupplyCycle();
        //支付方式
        String payMent = pageArray.getPayMent();
        //技术支持电话
        String technicalSupportTelephone = pageArray.getTechnicalSupportTelephone();
        //专家评审
        String expertReview = pageArray.getExpertReview();
        //评审奖励
        String expertReward = pageArray.getExpertReward();
        ServiceResult result = null;

        //保存到表中
        for (int i=0;i<purchaseList.size();i++){
            BdMaterialRepair mr= new BdMaterialRepair();
            mr.setPkMaterial(new IdWorker(1, 1).nextId() + "");
            mr.setName(applyCause);
            mr.setCode(null);
            mr.setMaterialspec(purchaseList.get(i).getNorms());
            mr.setMaterialtype(null);
            mr.setMeasdoc(purchaseList.get(i).getUnit());
            mr.setBrand(null);
            //将加工的具体物料名存入 物料类型 字段
            mr.setMarbasclass(purchaseList.get(i).getItemName());
            mr.setCreationtime(TimeUtil.getNowDateTime());
            mr.setCreator(staId);
            mr.setPk_org(null);
            mr.setPk_group(orgId);
            mr.setEnablestate(2);
            mr.setType(Integer.parseInt(type));
            purchaseList.get(i).setMaterialId(mr.getPkMaterial());
            bdMaterialRepairMapper.insert(mr);
        }
        //存入purchase表
        StraregyObj stObj = new StraregyObj();
        stObj.setId(idWorker.nextId() + "");
        stObj.setBuyChannelId(BuyChannelResource.MACHINING);
        stObj.setGoodsType("加工");
        stObj.setGoodsBrand(purchaseList.get(0).getBrandName());
        stObj.setGoodsName(purchaseList.get(0).getItemName());
        stObj.setGoodsSpecs(purchaseList.get(0).getNorms());
        stObj.setGoodsBranch(purchaseList.get(0).getUnit());
        stObj.setGoodsDetail(toJson(pageArray));//----------------------->
        stObj.setCreateTime(TimeUtil.getNowDateTime());
        stObj.setStatus(OrderStatus.waitingQuote+"");
        stObj.setTitle("");
        stObj.setStaffId(staId);
        stObj.setOrgId(orgId);
        stObj.setOrderNumber(makeOrderNum());
        stObj.setQuoteSellerNum(quoteSellerNum+"");
        stObj.setSupplierSellerNum(supplierSellerNum+"");
        stObj.setApplyCause(applyCause);
        stObj.setRemarks(remarks);
        stObj.setDeadLine(deadLine);
        stObj.setSupplyCycle(supplyCycle);
        stObj.setPayMent(payMent);
        stObj.setTechnicalSupportTelephone(technicalSupportTelephone);
        stObj.setExpertReview(expertReview);
        stObj.setExpertReward(expertReward);
        stObj.setQuoteCounts(0+"");




//        //生成pdf
//        String []page_text={"申请理由："+applyCause,"报价商家："+quoteSellerNum,"零配件供应商数："+supplierSellerNum,"备注："+remarks};
//
//        stObj.setReqfileurl(new Cread_PDF().Cread_PDF_function("询价采购清单",purchaseList,page_text));
        //strategyPurMapper.insertStrategyPur(stObj);
        //将数据插入fy_purchase表

        //
        purchaseMapper.insertStrategyPur(stObj);

        if (purchaseList!=null&&purchaseList.size()>0){
            for (PurchaseArray pur : purchaseList) {
                PurchaseDetail pd =new PurchaseDetail();
                pd.setId(new IdWorker(1, 1).nextId() + "");
                pd.setFyPurchaseId(stObj.getId());
                pd.setPkMaterial(pur.getMaterialId());
                pd.setGoodsQuantity(pur.getCount());
                fyPurchaseDetailMapper.insert(pd);

            }
        }
        return ServiceResult.success("保存成功！");
    }

    /**
     * 将物品信息存为json
     */
    private String toJson(Object obj) {
        String json = JSON.toJSONString(obj);
        return json;
    }
    /**
     * 产生订单号
     */
    private String makeOrderNum(){
        String orderNum="";
        orderNum+="jg";
        String[] splits = TimeUtil.getNowOnlyDate().split("-");
        String a="";
        for (int i=0;i<splits.length;i++){
            if (i==splits.length-1){
                orderNum+=splits[i]+"-";
            }else {
                orderNum+=splits[i];
            }
        }
        orederStartNum++;
        orderNum+=orederStartNum;
        return orderNum;
    }

}
