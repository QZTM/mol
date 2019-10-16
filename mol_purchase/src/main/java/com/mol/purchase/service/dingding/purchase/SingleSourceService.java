package com.mol.purchase.service.dingding.purchase;

import com.alibaba.fastjson.JSON;
import com.mol.purchase.config.BuyChannelResource;
import com.mol.purchase.config.OrderStatus;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseArray;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import com.mol.purchase.entity.dingding.purchase.strategPurchaseEntity.PageArray;
import com.mol.purchase.mapper.fyOracle.dingding.purchase.StrategyOraclMapper;
import com.mol.purchase.mapper.newMysql.FyPurchaseEsMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.BdSupplierMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.purchase.util.FindFirstMarbasclassByMaterialUtils;
import com.mol.purchase.util.robot.Cread_PDF;
import entity.BdMarbasclass;
import entity.ServiceResult;
import com.mol.purchase.entity.Supplier;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;
import util.TimeUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.mol.purchase.config.Constant.orederStartNum;

@Service
public class SingleSourceService {

    private Logger logger = LoggerFactory.getLogger(StrategyPurchaseService.class);

    @Resource
    private fyPurchaseMapper purchaseMapper;

    @Resource
    private BdSupplierMapper bdSupplierMapper;

    @Resource
    private com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper fyPurchaseDetailMapper;

    @Autowired
    private IdWorker idWorker;
    //oracl的mapper
    @Resource
    private StrategyOraclMapper strategyOraclMapper;

    @Resource
    private FyPurchaseEsMapper fyPurchaseEsMapper;

    @Resource
    private BdSupplierMapper supplierMapper;

    @Autowired
    FindFirstMarbasclassByMaterialUtils find;


    public StraregyObj save(PageArray pageArray, String staId, String orgId) throws IOException, DocumentException, IllegalAccessException {
        //申请事由
        String applyCause = pageArray.getApplyCause();
        //采购详情
        List<PurchaseArray> purchaseList = pageArray.getPurchaseArray();
        BdMarbasclass firstMarbasclass = find.getFirstMarbasclass(purchaseList.get(0).getMaterialId());
        //单一供应商
        String singleSource = pageArray.getSingleSource();
        //电话
        String telePhone = pageArray.getTelePhone();
        //商家id
        String pkSupplier = pageArray.getPkSupplier();
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


        StraregyObj stObj = new StraregyObj();
        stObj.setId(idWorker.nextId()+"");
        stObj.setBuyChannelId(BuyChannelResource.SINGLESOURCE);//单一来源
        stObj.setPkMarbasclass(firstMarbasclass.getPkMarbasclass());
        stObj.setGoodsType(purchaseList.get(0).getTypeName());
        stObj.setGoodsBrand(purchaseList.get(0).getBrandName());
        stObj.setGoodsName(purchaseList.get(0).getItemName());
        stObj.setGoodsSpecs(purchaseList.get(0).getNorms());
        stObj.setGoodsBranch(purchaseList.get(0).getUnit());
        //stObj.setGoodsQuantity(purchaseList.get(0).getCount()+"");
        stObj.setGoodsDetail(toJson(pageArray));//----------------------->
        stObj.setCreateTime(TimeUtil.getNowDateTime());
        stObj.setOrderNumber(makeOrderNum());
        stObj.setStatus(OrderStatus.waitingQuote+"");
        stObj.setTitle(purchaseList.get(0).getTypeName()+"单一采购");
        stObj.setStaffId(staId);
        stObj.setOrgId(orgId);
        stObj.setRemarks(remarks);
        stObj.setApplyCause(applyCause);
        stObj.setPkSupplier(pkSupplier);
        stObj.setDeadLine(deadLine);
        stObj.setSupplyCycle(supplyCycle);
        stObj.setPayMent(payMent);
        stObj.setTechnicalSupportTelephone(technicalSupportTelephone);
        stObj.setExpertReview(expertReview);
        stObj.setExpertReward(expertReward);
        stObj.setQuoteCounts(0+"");
        purchaseMapper.insertStrategyPur(stObj);
        String []page_text={"申请理由："+applyCause,"单一供应商："+singleSource,"电话："+telePhone,"备注："+remarks};
        new Cread_PDF().Cread_PDF_function("战略采购清单",purchaseList,page_text);//生成pdf
        //strategyPurMapper.insertStrategyPur(stObj);

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
        //从es表查询是否有这个id对应的表
        StraregyObj esObj=fyPurchaseEsMapper.selectOrderById(stObj.getId());
        if (esObj==null){
            //es表中无
            int i=fyPurchaseEsMapper.insert(stObj);
        }else {
            //修改
            fyPurchaseEsMapper.updata(stObj);
        }

        //strategyPurMapper.insertStrategyPur(stObj);
        //result= new ServiceResult(true,"添加成功","物品添加成功");


        return stObj;
    }
    /**
     * 将物品信息存为json
     */
    private String toJson(Object obj){
        String json = JSON.toJSONString(obj);
        return json;
    }

    public ServiceResult<Supplier> getSupplier(List<PurchaseArray> purList) {
        //判断有没有id？？
        //先拿到ID
        //拿到查询到的id，到mysql中查询对应的公司name ,telephone
        HashMap<String, String> map = new HashMap<String, String>();
        int key=1;
        Supplier supplier=null;
        for (PurchaseArray pur : purList) {
            map.put(key+"",pur.getMaterialId());
        }
        System.out.println(supplier);

        if (map.size()>0&&map!=null){
            String id= bdSupplierMapper.getSupplierId(map);
            System.out.println(id);
            if (id!=null){
                supplier=bdSupplierMapper.getSupplierById(id);
            }
        }
        return ServiceResult.success(supplier);
    }
    /**
     * 产生订单号
     */
    private String makeOrderNum(){
        String orderNum="";
        orderNum+="dy";
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

    public List<Supplier> findSupplierByPur(StraregyObj stobj) {
        Example e=new Example(Supplier.class);
        Example.Criteria and = e.and();
        and.andEqualTo("industryFirst",stobj.getPkMarbasclass()).andEqualTo("ifAttrSingle",1);
        return  supplierMapper.selectByExample(e);

    }
}
