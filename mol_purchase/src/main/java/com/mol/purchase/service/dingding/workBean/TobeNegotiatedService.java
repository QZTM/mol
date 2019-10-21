package com.mol.purchase.service.dingding.workBean;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mol.purchase.config.OrderStatus;
import com.mol.purchase.entity.ExpertRecommend;
import com.mol.purchase.entity.ExpertUser;
import com.mol.purchase.entity.FyQuote;
import com.mol.purchase.entity.dingding.login.AppUser;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.dingding.purchase.workBench.BigDataStar;
import com.mol.purchase.entity.dingding.purchase.workBench.Ucharts;
import com.mol.purchase.entity.dingding.purchase.workBench.UchartsSeries;
import com.mol.purchase.entity.dingding.purchase.workBench.toBeNegotiated.MaterIdToSupplierId;
import com.mol.purchase.entity.dingding.purchase.workBench.toBeNegotiated.NegotiatIng;
import com.mol.purchase.entity.dingding.purchase.workBench.toBeNegotiated.SupplierIdToExpertId;
import com.mol.purchase.entity.dingding.solr.fyPurchase;
import com.mol.purchase.mapper.newMysql.ExpertRecommendMapper;
import com.mol.purchase.mapper.newMysql.ExpertUserMapper;
import com.mol.purchase.mapper.newMysql.FyQuoteMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.BdSupplierMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.purchase.mapper.newMysql.dingding.user.AppUserMapper;
import com.mol.purchase.mapper.newMysql.dingding.workBench.PoOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.swing.plaf.SeparatorUI;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:TobeNegotiatedService
 * Package:com.purchase.service.dingding.workBean
 * Description
 *      E应用待议价service
 * @date:2019/8/12 13:27
 * @author:yangjiangyan
 */
@Service
public class TobeNegotiatedService {

    @Autowired
    private fyPurchaseMapper fyPurchaseMapper;

    @Autowired
    private FyQuoteMapper fyQuoteMapper;

    @Autowired
    private BdSupplierMapper bdSupplierMapper;

    @Autowired
    private fyPurchaseDetailMapper fyPurchaseDetailMapper;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private PoOrderMapper poOrderMapper;

    @Autowired
    private ExpertRecommendMapper expertRecommendMapper;

    @Autowired
    private ExpertUserMapper expertUserMapper;


    public List<String> getListBelongsSupplier(String supplierId) {
        return fyQuoteMapper.getListBySupplier(supplierId);
    }

    public List<fyPurchase> findListByIdListAndStatus(String status, List<String> quoteIdList) {
        return fyPurchaseMapper.findListByIdlistAndStatus(status,null,null,quoteIdList);
    }

    public List<fyPurchase> findListByOrgId(String orgId, String status,String secondStatus) {
         return fyPurchaseMapper.findListByOrgIdAndStatus(orgId,status,secondStatus);
    }

    public fyPurchase findFypurchaseById(String id) {
        fyPurchase pur = fyPurchaseMapper.findOneById(id);

        //申请人信息
        AppUser appUser = new AppUser();
        appUser.setId(pur.getStaffId());
        appUser= appUserMapper.selectOne(appUser);
        pur.setStaffId(appUser.getUserName());

        return pur;
    }

    public Map<String,List> findQuoteById(String id) {
        Map<String,List> quoteMap=new HashMap<>();
        //根据订单id查询报价公司id
        List<String> supplierIdsList=fyQuoteMapper.findFypurchaseIdListById(id);

        //根据订单id，公司id 查询
        for (String supplierId : supplierIdsList) {
            List<FyQuote> fyQuotesList=fyQuoteMapper.findQuoteBySupplierIdAndPurchaseId(id,supplierId);

            //根据公司id查询公司名称
            //String supplierName = bdSupplierMapper.getSupplierNameById(supplierId);
            quoteMap.put(supplierId,fyQuotesList);
        }
        return quoteMap;
    }

    public void updataAppUserListById(String id, String [] callId) {
        List<AppUser> list= new ArrayList<>();
        for (String dduserId : callId) {
            AppUser appUser= new AppUser();
            appUser.setDdUserId(dduserId);
            appUser= appUserMapper.selectOne(appUser);
            list.add(appUser);
        }
        //fyPurchase pur = fyPurchaseMapper.findOneById(id);
        String arr = JSON.toJSONString(list);
        fyPurchaseMapper.updateAppUserListById(id,arr);
    }

    public List<AppUser> findAppUserListById(String id) {
        String appUserString=fyPurchaseMapper.findAppUserListById(id);
//        List<AppUser>list = (List<AppUser>) JSON.parse(appUserString);
        List<AppUser>list= JSON.parseArray(appUserString,AppUser.class);

        return list;
    }

    /**
     * 查询该公司待审核的订单list，选择userid有权限查看的
     * @param orgId
     * @param status
     * @param userId
     * @return
     */
    public List<fyPurchase> findListIfOk(String orgId, String status,String secondStatus, String userId) {

        List<fyPurchase> overList=new ArrayList<>();

        List<fyPurchase> list = fyPurchaseMapper.findListByOrgIdAndStatus(orgId, status,secondStatus);
        for (fyPurchase fyPurchase : list) {
            String negotiatePerson = fyPurchase.getNegotiatePerson();
            if (negotiatePerson!=null&&negotiatePerson.length()>0){
                List appuserlist=JSON.parseObject(negotiatePerson, List.class);
                for (int i =0 ;i<appuserlist.size();i++){
                    Object o = appuserlist.get(i);
                    String s = JSON.toJSONString(o);
                    AppUser appUser = JSON.parseObject(s, AppUser.class);
                    if (appUser.getId().equals(userId)){
                        System.out.println(fyPurchase);
                        overList.add(fyPurchase);
                    }
                }
            }
        }
        return overList;
    }

    public Ucharts getBigData(String supplierId, String pkMaterialId) {
        List<BigDataStar> bdList=poOrderMapper.getBigDataBySuppliedAndpkMaterialId(supplierId,pkMaterialId);

        Ucharts u=new Ucharts();
        UchartsSeries us=new UchartsSeries();
        String [] caArr=new String [bdList.size()];
        Double [] daArr= new Double[bdList.size()];
        for(int i =0;i<bdList.size();i++){
            //x轴时间
            caArr[i]=bdList.get(i).getDmakedate().split(" ")[0];
            //价格
            daArr[i]=bdList.get(i).getNorigprice();
            //name
        }
        us.setName(bdList.get(0).getName());
        us.setData(daArr);
        List<UchartsSeries> ulist=new ArrayList<>();
        ulist.add(us);

        u.setCategories(caArr);
        u.setSeries(ulist);

        System.out.println(u);
        return u;
    }

    // 保存待议价界面提交的对应数据
    @Transactional
    public void save(NegotiatIng negotiatIng) {
        //订单id
        String purId = negotiatIng.getPurId();
        //负责人说明
        String explain = negotiatIng.getExplain();
        //保存负责人说明
        fyPurchaseMapper.updateExplainById(purId,explain);
        //更改订单状态为等待审核结果
        fyPurchaseMapper.updateStatusById(purId, OrderStatus.EndOfBargaining+"");
        //对应关系
        List<MaterIdToSupplierId> mts = negotiatIng.getMaterIdToSupplierId();

        List<SupplierIdToExpertId> ste = negotiatIng.getSupplierToExpert();

//        for (MaterIdToSupplierId idToSupplierId : mts) {
//            String materId = idToSupplierId.getMaterId();
//            String supplierId = idToSupplierId.getSupplierId();
//            //保存物料对应关系
//            //根据物料，订单，公司查询报价记录id
//            String quoteId=fyQuoteMapper.findIdByPurIdAndPkMatIdAndSupplierId(purId,materId,supplierId);
//            //根据订单id，物料id，保存选中的报价id
//            fyPurchaseDetailMapper.updataQuoteIdByPurIdAndPkMatId(purId,materId,quoteId);
//        }

        //合并
        for(int k=0;k<mts.size();k++){
            for (int v=0;v<ste.size();v++){
                if (mts.get(k).getSupplierId().equals(ste.get(v).getSupplierId())){
                    //专家id 字符串
                    List<ExpertUser> expertList = ste.get(v).getExpertList();
                    String idString="";
                    for (int i=0;i<expertList.size();i++){
                        if (i==expertList.size()-1){
                            idString+=expertList.get(i).getId();
                        }else {
                            idString+=expertList.get(i).getId();
                            idString+=",";
                        }
                    }

                    String materId = mts.get(k).getMaterId();
                    String supplierId = mts.get(k).getSupplierId();
                    //保存物料对应关系
                    //根据物料，订单，公司查询报价记录id
                    String quoteId=fyQuoteMapper.findIdByPurIdAndPkMatIdAndSupplierId(purId,materId,supplierId);
                    //根据订单id，物料id，保存选中的报价id
                    fyPurchaseDetailMapper.updataQuoteIdAndExpertIdByPurIdAndPkMatId(purId,materId,quoteId,idString);
                }
            }
        }
        //保存推荐专家

//            for (SupplierIdToExpertId ste : supplierToExpert) {
//            String supplierId = ste.getSupplierId();
//            List<ExpertUser> expertList = ste.getExpertList();
//            String idString="";
//            for (int i=0;i<expertList.size();i++){
//                if (i==expertList.size()-1){
//                    idString+=expertList.get(i).getId();
//                }else {
//                    idString+=expertList.get(i).getId();
//                    idString+=",";
//                }
//            }
//        }
    }

    //查询订单详情表中具体物料选中的具体公司
    public List<PurchaseDetail> findFyPurchaseDetailById(String id) {
         return fyPurchaseDetailMapper.findPurchaseDetailList(id);
    }

    //保存待审批界面审批人建议以及订单是否通过审批的状态
    public void saveApproverProlosalAndStatus(String purId, String passOrNot, String approverProposal) {
        fyPurchaseMapper.updateStatusAndApproverProposalById(purId,passOrNot,approverProposal);
    }
    //通过业务id集合 查询待审批订单集合
    public List<fyPurchase> findFyPurchaseByIdArr(List<String> arr) {
         return fyPurchaseMapper.findPurchaseByIdList(arr);
    }

    //查询订单中供应商的推荐专家
    public List<ExpertRecommend> findExpertList(String purId, String supplierId) {
        Example o = new Example(ExpertRecommend.class);
        o.and().andEqualTo("purchaseId",purId).andEqualTo("supplierId",supplierId);
        List<ExpertRecommend> expertRecommends = expertRecommendMapper.selectByExample(o);
        return expertRecommends;
    }

    public List<ExpertUser> findExpertUserList(List<ExpertRecommend> erList) {
        List<ExpertUser> list = new ArrayList<>();
        for (ExpertRecommend er : erList) {
//            ExpertUser t = new ExpertUser();
//            t.setId(er.getExpertId());
            ExpertUser t2 = expertUserMapper.findExpertUserById(er.getExpertId());
            t2.setRecommendReason(er.getRecommendReason());

            list.add(t2);
        }
        return list;
    }

    public List<FyQuote> getFyQuoteByPurIdAndSupplierId(String purId, String supplierId) {
        FyQuote t = new FyQuote();
        t.setFyPurchaseId(purId);
        t.setPkSupplierId(supplierId);
        return fyQuoteMapper.select(t);
    }

    public PurchaseDetail getPurchaseDetailByPurIdAndQuoteId(String purId, List<FyQuote > quote) {
        PurchaseDetail pd=null;
        if (quote!=null &&quote.size()>0){
            for (int i=0;i<quote.size();i++){
                PurchaseDetail t = new PurchaseDetail();
                t.setFyPurchaseId(purId);
                t.setQuoteId(quote.get(i).getId());
                PurchaseDetail purchaseDetail = fyPurchaseDetailMapper.selectOne(t);
                if (purchaseDetail!=null){
                    return purchaseDetail;
                }else {
                    if (i==quote.size()-1){
                        return pd;
                    }
                }
            }
        }else {
            return pd;
        }
        return pd;
    }
}
