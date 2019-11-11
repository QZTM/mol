package com.mol.supplier.service.dingding.purchase;

import com.alibaba.fastjson.JSON;
import com.mol.supplier.config.BuyChannelResource;
import com.mol.supplier.config.OrderStatus;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseArray;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import com.mol.supplier.mapper.EnquiryOraclMapper;
import com.mol.supplier.mapper.dingding.purchase.EnquiryPurMapper;
import com.mol.supplier.mapper.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.supplier.mapper.dingding.purchase.fyPurchaseMapper;
import com.mol.supplier.mapper.third.FyPurchaseEsMapper;
import com.mol.supplier.util.Cread_PDF;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;
import util.TimeUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

import static com.mol.supplier.config.Constant.orederStartNum;

@Service
public class EnquiryPurchaseService {

    private Logger logger = LoggerFactory.getLogger(StrategyPurchaseService.class);

//    @Resource
//    private StrategyPurMapper strategyPurMapper;

    @Resource
    private fyPurchaseMapper purchaseMapper;

    @Resource
    private fyPurchaseDetailMapper purchaseDetailMapper;

    @Resource
    private EnquiryOraclMapper oraclMapper;

    @Resource
    private EnquiryPurMapper mysqlMapper;

    @Resource
    private FyPurchaseEsMapper fyPurchaseEsMapper;

    @Autowired
    private IdWorker idWorker;


    public ServiceResult<String> save(PageArray pageArray, String staId, String orgId)  {
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

        ServiceResult result = null;
        //存入purchase表
        StraregyObj stObj = new StraregyObj();
        stObj.setId(idWorker.nextId() + "");
        stObj.setBuyChannelId(BuyChannelResource.ENQUIRY);
        stObj.setGoodsType(purchaseList.get(0).getTypeName());
        stObj.setGoodsBrand(purchaseList.get(0).getBrandName());
        stObj.setGoodsName(purchaseList.get(0).getItemName());
        stObj.setGoodsSpecs(purchaseList.get(0).getNorms());
        stObj.setGoodsBranch(purchaseList.get(0).getUnit());
        stObj.setGoodsDetail(toJson(pageArray));//----------------------->
        stObj.setCreateTime(TimeUtil.getNowDateTime());
        stObj.setStatus(OrderStatus.waitingQuote+"");
        stObj.setTitle(purchaseList.get(0).getTypeName() + "询价采购");
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
        stObj.setQuoteCounts(0+"");

        //生成pdf
        String []page_text={"申请理由："+applyCause,"报价商家："+quoteSellerNum,"零配件供应商数："+supplierSellerNum,"备注："+remarks};

        stObj.setReqfileurl(new Cread_PDF().Cread_PDF_function("询价采购清单",purchaseList,page_text));
        //strategyPurMapper.insertStrategyPur(stObj);
        //将数据插入fy_purchase表

        purchaseMapper.insertStrategyPur(stObj);
        //list存入purchasedetail表中
        if (purchaseList!=null&&purchaseList.size()>0){
            for (PurchaseArray pur : purchaseList) {
                PurchaseDetail pd=new PurchaseDetail();
                pd.setId(new IdWorker(1, 1).nextId() + "");
                pd.setFyPurchaseId(stObj.getId());
                pd.setPkMaterial(pur.getMaterialId());
                pd.setGoodsQuantity(pur.getCount());
                purchaseDetailMapper.insert(pd);

                //**

                System.out.println("--------------"+page_text);


            }
        }
        //从es表查询是否有这个id对应的表
        StraregyObj esObj=fyPurchaseEsMapper.selectOrderById(stObj.getId());
        if (esObj==null){
            //es表中无
            int i=fyPurchaseEsMapper.insert(stObj);
        }else {
            //修改
            fyPurchaseEsMapper.updata(stObj);
        }
        result = new ServiceResult(true, "200", "物品添加成功");
        return result;
    }

    /**
     * 将物品信息存为json
     */
    private String toJson(Object obj) {
        String json = JSON.toJSONString(obj);
        return json;
    }

    /**
     * 查询商家个数
     *
     * @param purchaseArray
     * @return
     */
    public ServiceResult<Integer> getSupplierNum(List<PurchaseArray> purchaseArray) {
        HashMap<String,String> map=new HashMap<String,String>();
        int key =1;
        if (purchaseArray.size() > 0 && purchaseArray != null) {
            for (int i = 0; i < purchaseArray.size(); i++) {
                String itemName = purchaseArray.get(i).getItemName();
                //先用物品名称在mysql查询物品的id,查不到则去oracl查询
                String mysqlId = mysqlMapper.getMysqlId(itemName);
                if (mysqlId!=null){
                    map.put(key+"",mysqlId);
                    key++;
                }else {
                    //去oracl数据库查询
                    String oraclId = oraclMapper.getOraclId(itemName);
                    map.put(key+"",oraclId);
                    key++;
                }
            }
        } else {
            return null;
        }

        //通过查询出来的id，查询商品的供应商数量
        int num=mysqlMapper.getSupplierSellerNum(map);
        return ServiceResult.success(num);
    }
    /**
     * 产生订单号
     */
    private String makeOrderNum(){
        String orderNum="";
        orderNum+="xj";
        String[] splits = TimeUtil.getNowOnlyDate().split("-");
        String a="";
        for (int i=0;i<splits.length;i++){
            if (i==splits.length-1){
                orderNum+=splits[i]+"_";
            }else {
                orderNum+=splits[i];
            }
        }
        orederStartNum++;
        orderNum+=orederStartNum;
        return orderNum;
    }

}