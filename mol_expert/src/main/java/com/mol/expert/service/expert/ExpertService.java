package com.mol.expert.service.expert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mol.expert.config.ExpertStatus;
import com.mol.expert.entity.dingding.login.AppAuthOrg;
import com.mol.expert.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseArray;
import com.mol.expert.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.expert.entity.dingding.solr.fyPurchase;
import com.mol.expert.entity.pageBean.PageBean;
import com.mol.expert.entity.thirdPlatform.FyQuote;
import com.mol.expert.entity.expert.ExpertDetail;
import com.mol.expert.entity.expert.ExpertEnter;
import com.mol.expert.entity.expert.ExpertRecommend;
import com.mol.expert.entity.expert.ExpertUser;
import com.mol.expert.mapper.newMysql.dingding.org.AppOrgMapper;
import com.mol.expert.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.expert.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.expert.mapper.newMysql.expert.ExpertEnterMapper;
import com.mol.expert.mapper.newMysql.expert.ExpertRecomendMapper;
import com.mol.expert.mapper.newMysql.third.BdMarbasclassMapper;
import com.mol.expert.mapper.newMysql.third.FyQuoteMapper;
import com.mol.expert.util.IdWorker;
import com.mol.expert.util.TimeUtil;
import entity.BdMarbasclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ExpertService
 * Package:com.purchase.service.expert
 * Description
 *
 * @date:2019/9/29 14:52
 * @author:yangjiangyan
 */
@Service
public class ExpertService {

    @Autowired
    private ExpertEnterMapper eem;

    @Autowired
    private BdMarbasclassMapper marbasclassMapper;

    @Autowired
    private AppOrgMapper appOrgMapper;

    @Autowired
    private fyPurchaseMapper purchaseMapper;
    
    @Autowired
    private fyPurchaseDetailMapper purchaseDetailMapper;

    @Autowired
    private FyQuoteMapper fyQuoteMapper;

    @Autowired
    private ExpertRecomendMapper expertRecomendMapper;
    /**
     * 查询入口
     * @return
     */
    public List<ExpertEnter> findEnter() {
        List<ExpertEnter> expertEnters = eem.selectAll();
        return expertEnters;
    }

    /**
     * 查询最高级分类，获取id
     * @return
     */
    public List<BdMarbasclass> findMarbasclassTopLevel() {
        List<BdMarbasclass> marbasFirstList = marbasclassMapper.findMarbasFirstList();
        return marbasFirstList;
    }

    /**
     * 查询下属所有物料
     * @param code
     * @return
     */
    public List<BdMarbasclass> findMarbasclassByCodeLike(String code) {
        return  marbasclassMapper.findListByCodeLike(code);
    }

    /**
     * 查询需要专家评审的订单
     * @param status
     * @param exper
     * @return
     */
    public List<fyPurchase> findPurList(String pkMarbasclasss,String status, String exper) {
        Example o = new Example(fyPurchase.class);
        o.and().andEqualTo("pkMarbasclass",pkMarbasclasss).andEqualTo("status",status).andEqualTo("expertReview",exper);
        return purchaseMapper.selectByExample(o);
    }


    /**
     * 预算
     * @param byCodeLike
     * @param purList
     * @return
     */
    public List<fyPurchase> findSelectionList( List<fyPurchase> purList) {
        for (fyPurchase pur : purList) {

            //采购预算
            //数量，物料id
            //先查在加
            String budget=purchaseMapper.findBudgetByPurId(pur.getId());
            if (budget == null){
                List<PurchaseDetail> purDeList = purchaseDetailMapper.findPurchaseDetailList(pur.getId());
                List<FyQuote> quoteList = fyQuoteMapper.findQuoteByPurchaseId(pur.getId());
                int l=0;
                for (int i=0 ;i<purDeList.size();i++){
                    for(int j=0 ;j<quoteList.size();j++){
                        if (purDeList.get(i).getPkMaterial().equals(quoteList.get(j).getPkMaterialId())){
                            //l+=purDeList.get(i).getGoodsQuantity()*Integer.parseInt(quoteList.get(j).getQuote());
                            int goodsQuantity = purDeList.get(i).getGoodsQuantity();
                            String quote = quoteList.get(j).getQuote();
                            l+=goodsQuantity*Double.parseDouble(quote);
                        }
                    }
                }
                pur.setBudget(l/Integer.parseInt(pur.getQuoteCounts())+"");
                purchaseMapper.updateBudgetById(l/Integer.parseInt(pur.getQuoteCounts())+"",pur.getId());
            }else {
                pur.setBudget(budget);
            }




        }
        return purList;
    }

    /**
     * 查询公司
     * @param id
     * @return
     */
    public AppAuthOrg getOrg(String id){
        AppAuthOrg t =new AppAuthOrg();
        t.setId(id);
        return appOrgMapper.selectOne(t);
    }

    /**
     * 查询订单
     * @param purId
     * @return
     */
    public fyPurchase findPurById(String purId) {
       return purchaseMapper.findOneById(purId);
    }

    public Map<String, List<ExpertDetail>> getMap(List<PurchaseArray> purList, Map<String, List> quoteMap) {
        Map<String, List<ExpertDetail>> newMap= new HashMap<>();
        List<ExpertDetail> arrayList= null;
        int ind=65;
        for (String key : quoteMap.keySet()) {

            arrayList=new ArrayList<>();
            List<FyQuote> valueList = quoteMap.get(key);
            //查供应商id
            String pkSupplierId = fyQuoteMapper.findQuoteByid(valueList.get(0).getId()).getPkSupplierId();
            List<PurchaseArray> plist = JSONObject.parseArray(JSON.toJSON(purList).toString(), PurchaseArray.class);

            for (int i=0;i<plist.size();i++){
                for (int j=0;j<valueList.size();j++){
                    if (plist.get(i).getMaterialId().equals(valueList.get(j).getPkMaterialId())){
                        int k=0;
                        //plist.get(i).setQuote(valueList.get(j).getQuote());

                        k+=plist.get(i).getCount()*Double.parseDouble(valueList.get(j).getQuote());
                        ExpertDetail ed=new ExpertDetail();
                        ed.setBrandName(plist.get(i).getBrandName());
                        ed.setCount(plist.get(i).getCount());
                        ed.setFyPurchaseId(valueList.get(i).getFyPurchaseId());
                        ed.setItemName(plist.get(i).getItemName());
                        ed.setNorms(plist.get(i).getNorms());
                        ed.setMaterialId(plist.get(i).getMaterialId());
                        ed.setQuote(valueList.get(j).getQuote());
                        ed.setSum(k+"");
                        ed.setUnit(plist.get(i).getUnit());
                        ed.setSupplierId(pkSupplierId);



                        //plist.get(i).setSum(k+"");
                        arrayList.add(ed);
                    }
                }
            }

            newMap.put((char)ind+"",arrayList);
            ind++;
        }
        return newMap;
    }

    /**
     * 保存推荐信息
     * @param er 推荐的相关信息
     * @param user 专家
     * @return
     */
    @Transactional
    public ExpertRecommend saveExRecommend(ExpertRecommend er, ExpertUser user) {
        er.setId(new IdWorker(1,1).nextId()+"");
        er.setExpertId(user.getId());
        er.setCreateTime(TimeUtil.getNowDateTime());
        er.setAdopt(ExpertStatus.EXPERT_ORDER_WAITADOPTED+"");
        er.setCommission(ExpertStatus.EXPERT_MONEY_NOTDISTRIBUTION+"");
        //佣金不知道是订单的还是赚取的，没写
        expertRecomendMapper.insert(er);
        return er;
    }

    /**
     * 推荐的相关信息
     * @param er
     */
    public void changeExpertRecommend(ExpertRecommend er) {
        fyQuoteMapper.updateExpertRecommendByPurIdAndSupplierId(er.getPurchaseId(),er.getSupplierId());
    }

    public ExpertRecommend findRecommendByPurIdAndUserId(String purId, String id) {

        ExpertRecommend t=new ExpertRecommend();
        t.setPurchaseId(purId);
        t.setExpertId(id);
        ExpertRecommend expertRecommend = expertRecomendMapper.selectOne(t);
        return expertRecommend;
    }
    public List<fyPurchase> findPurListByPageBean(String status, String exper) {
        PageBean pb = new PageBean();
        pb.setPageSize(1);
        pb.setCurrentPage(5);
        return purchaseMapper.findListByStatusAndExpertReview("",status,exper);
    }

    public void findExpertAuthentication(ExpertUser user) {
    }

    public void addCiShu(String purchaseId,String supplierId) {

        FyQuote quote = new FyQuote();
        quote.setFyPurchaseId(purchaseId);
        quote.setPkSupplierId(supplierId);

        List<FyQuote> list = fyQuoteMapper.select(quote);
        if (list!=null && list.size()>0){
            FyQuote quo=list.get(0);
            String expertAgreeCounts = quo.getExpertAgreeCounts();

            if (expertAgreeCounts == null || expertAgreeCounts =="0"){
                expertAgreeCounts=1+"";
                quo.setExpertAgreeCounts(expertAgreeCounts);
            }else {
                expertAgreeCounts=Integer.valueOf(expertAgreeCounts)+1+"";
                quo.setExpertAgreeCounts(expertAgreeCounts);
            }
            fyQuoteMapper.updateExpertAgreeCountsByPurIdAndSupplierId(expertAgreeCounts,quo.getFyPurchaseId(),quo.getPkSupplierId());

        }
    }
}
