package com.mol.purchase.service.dingding.purchase;
import com.mol.config.Constant;
import com.mol.notification.SendNotification;
import com.mol.purchase.entity.Supplier;
import com.mol.purchase.entity.SupplierSalesman;
import com.mol.purchase.entity.dingding.solr.fyPurchase;
import com.mol.purchase.mapper.newMysql.BdSupplierSalesmanMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.BdMarbasclassMapper;
import com.alibaba.fastjson.JSON;
import com.mol.purchase.mapper.newMysql.FyPurchaseEsMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.*;
import com.mol.purchase.config.BuyChannelResource;
import com.mol.purchase.config.OrderStatus;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseArray;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import com.mol.purchase.mapper.fyOracle.dingding.purchase.EnquiryOraclMapper;
import com.mol.purchase.service.token.TokenService;
import com.mol.purchase.util.FindFirstMarbasclassByMaterialUtils;
import com.mol.purchase.util.robot.Cread_PDF;
import com.mol.sms.SendMsmHandler;
import com.mol.sms.XiaoNiuMsm;
import com.mol.sms.XiaoNiuMsmTemplate;
import entity.BdMarbasclass;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;
import util.TimeUtil;

import javax.annotation.Resource;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

import static com.mol.purchase.config.Constant.orederStartNum;

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

    @Autowired
    private TokenService tokenService;

    @Resource
    private BdSupplierMapper supplierMapper;

    @Autowired
    private BdMarbasclassMapper bdMarbasclassMapper;

    @Autowired
    FindFirstMarbasclassByMaterialUtils find;

    @Autowired
    private BdSupplierSalesmanMapper bdSupplierSalesmanMapper;

    @Transient
    public synchronized StraregyObj save(PageArray pageArray, String staId, String orgId)  {
        //申请事由
        String applyCause = pageArray.getApplyCause();
        //采购详情
        List<PurchaseArray> purchaseList = pageArray.getPurchaseArray();
        BdMarbasclass firstMarbasclass = find.getFirstMarbasclass(purchaseList.get(0).getMaterialId());

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
        //电子合同
        String electronicContract=pageArray.getElectronicContract();
        //专家评审
        String expertReview = pageArray.getExpertReview();
        if (expertReview==null ||expertReview==""){
            expertReview="false";
        }
        //评审奖励
        String expertReward=pageArray.getExpertReward();
        ServiceResult result = null;
        //存入purchase表
        StraregyObj stObj = new StraregyObj();
        stObj.setId(new IdWorker(1, 1).nextId() + "");
        stObj.setBuyChannelId(BuyChannelResource.ENQUIRY);
        stObj.setPkMarbasclass(firstMarbasclass.getPkMarbasclass());
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
        stObj.setOrderNumber(makeOrderNum(BuyChannelResource.ENQUIRY));
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
        stObj.setElectronicContract(electronicContract);
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
        return stObj;
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
    private String makeOrderNum(String buyChannalId){
        String orderNum="";
        orderNum+="XJ";
        String time=TimeUtil.getNowOnlyDateNoline();
        orderNum+=time;


        List<fyPurchase> list=purchaseMapper.findPurListByLikeCreateTime(TimeUtil.getNowOnlyDate(),buyChannalId);
        if ( list.size()==0){
            orderNum+="001";
        }else{
            String substring = list.get(0).getOrderNumber().substring(10);
            int i = Integer.parseInt(substring);
            i++;
            String i2="0000"+i;
            String substring1 = i2.substring(i2.length() - 3);
            orderNum+=substring1;
        }
        //orederStartNum++;
        //orderNum+=orederStartNum;
        return orderNum;
    }

    public List<Supplier> findSupplierByPur(StraregyObj stobj) {

        Example e=new Example(Supplier.class);
        Example.Criteria and = e.and();
        and.andEqualTo("industryFirst",stobj.getPkMarbasclass()).andEqualTo("ifAttrNormal",1);
        return  supplierMapper.selectByExample(e);

    }

    /**
     * 获取公司的员工
     * @param list
     * @return
     */
    public List<SupplierSalesman> findSaleManList(List<Supplier> list) {
        List<SupplierSalesman > ssL=new ArrayList<>();
        if (list!=null && list.size()>0){
//            SupplierSalesman e =new SupplierSalesman();
            Example e=new Example(SupplierSalesman.class);
            for (Supplier supplier : list) {
                e.and().andEqualTo("pkSupplier",supplier.getPkSupplier());
                List<SupplierSalesman> supplierSalesmenlist = bdSupplierSalesmanMapper.selectByExample(e);
                if (supplierSalesmenlist!=null && supplierSalesmenlist.size()>0){
                    ssL.addAll(supplierSalesmenlist);
                }
            }
            return ssL;
        }else {
            return ssL;
        }

    }


    public List<String> findSaleIdList(List<SupplierSalesman> saleManList) {
        List<String> ssI=new ArrayList<>();
        if (saleManList!=null && saleManList.size()>0){
            for (SupplierSalesman supplierSalesman : saleManList) {
                ssI.add(supplierSalesman.getDdUserId());
            }
            return ssI;
        }else {
            return ssI;
        }
    }

    public List<String> findSalePhoneList(List<SupplierSalesman> saleManList) {
        List<String> ssP=new ArrayList<>();
        if (saleManList!=null && saleManList.size()>0){
            for (SupplierSalesman supplierSalesman : saleManList) {
                ssP.add(supplierSalesman.getPhone());
            }
            return ssP;
        }else {
            return ssP;
        }
    }

    @Async
    public void sendMessage(StraregyObj stobj , SendMsmHandler sendMsmHandler, SendNotification sendNotification, XiaoNiuMsmTemplate templateName) {
        //所属行业供应商
        List<Supplier> list=findSupplierByPur(stobj);
        if (list.size()>0 && list!=null){
            //查询供应商下的人员
            List<SupplierSalesman> saleManList = findSaleManList(list);
            //查询人员的ddId
            List<String> manIdList=findSaleIdList(saleManList);
            //发送通知消息
            for (String s : manIdList) {
                sendNotification.sendOaFromThird(s, Constant.AGENTID_THIRDPLAT,tokenService.getMicroToken());
            }
            //查询人员的电话
            List<String> manPhoneList= findSalePhoneList(saleManList);
            for (String phone : manPhoneList) {
                sendMsmHandler.sendMsm(XiaoNiuMsm.SIGNNAME_MEYG, XiaoNiuMsmTemplate.提醒供应商报价模板(),phone);
            }
            logger.info("询价采购---短信，通知发送成功："+list);
        }
    }
}