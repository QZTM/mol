package com.mol.purchase.service.dingding.schedule;

import com.github.pagehelper.PageHelper;
import com.mol.purchase.entity.ExpertRecommend;
import com.mol.purchase.entity.FyQuote;
import com.mol.purchase.entity.SupplierSalesman;
import com.mol.purchase.entity.activiti.ActHiComment;
import com.mol.purchase.entity.activiti.ActHiProcinst;
import com.mol.purchase.entity.dingding.login.AppAuthOrg;
import com.mol.purchase.entity.dingding.login.AppUser;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.dingding.solr.fyPurchase;
import com.mol.purchase.mapper.newMysql.BdSupplierSalesmanMapper;
import com.mol.purchase.mapper.newMysql.ExpertRecommendMapper;
import com.mol.purchase.mapper.newMysql.FyQuoteMapper;
import com.mol.purchase.mapper.newMysql.dingding.activiti.ActHiCommentMapper;
import com.mol.purchase.mapper.newMysql.dingding.activiti.ActHiProcinstMapper;
import com.mol.purchase.mapper.newMysql.dingding.org.AppOrgMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.BdSupplierMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.purchase.mapper.newMysql.dingding.user.AppUserMapper;
import com.mol.purchase.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:SchedulerRepairService
 * Package:com.purchase.service.dingding.schedule
 * Description
 *
 * @date:2019/9/11 14:48
 * @author:yangjiangyan
 */
@Service
public class SchedulerRepairService {
    @Autowired
    private fyPurchaseMapper fyPurchaseMapper;

    @Autowired
    private fyPurchaseDetailMapper fyPurchaseDetailMapper;

    @Autowired
    private FyQuoteMapper fyQuoteMapper;

    @Autowired
    private BdSupplierMapper bdSupplierMapper;

    @Autowired
    private ActHiProcinstMapper actHiProcinstMapper;

    @Autowired
    private ActHiCommentMapper actHiCommentMapper;

    @Autowired
    private AppOrgMapper appOrgMapper;

    @Autowired
    private AppUserMapper appUserMapper;


    @Autowired
    private BdSupplierSalesmanMapper supplierSalesmanMapper;

    @Autowired
    private ExpertRecommendMapper expertRecommendMapper;


    public List<fyPurchase> getList(String orgId, String userId,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<fyPurchase> list=fyPurchaseMapper.findListByOrgIdAndStaffId(orgId,userId);
//        List<fyPurchase> ll=new ArrayList<>();
//        for (fyPurchase fyPurchase : list) {
//            if (fyPurchase.getBuyChannelId()==3 || fyPurchase.getBuyChannelId()==4 || fyPurchase.getBuyChannelId()==5){
//                ll.add(fyPurchase);
//            }
//        }
        return list;
    }

    public fyPurchase getPurById(String id) {
        return fyPurchaseMapper.findOneById(id);
    }

    public List<FyQuote> checkQuoteList(String id) {
        List<PurchaseDetail> purchaseDetailList = fyPurchaseDetailMapper.findPurchaseDetailList(id);
        List<FyQuote> quoteList=new ArrayList<>();
        if (purchaseDetailList!=null && purchaseDetailList.size()>0){
            for (PurchaseDetail purchaseDetail : purchaseDetailList) {
                FyQuote quote=fyQuoteMapper.findQuoteByid(purchaseDetail.getQuoteId());
                if (quote!=null){
                    quoteList.add(quote);
                }
            }
        }
        return quoteList;
    }

    public Supplier getSupplierById(String pkSupplierId) {
        if (pkSupplierId!=null){
            return   bdSupplierMapper.getSupplierById(pkSupplierId);
        }else {
            return null;
        }
    }
    //历史流程实例中查询订单对应的定义pro对象
    public ActHiProcinst getComment(String purId) {
        return actHiProcinstMapper.getProByPurId(purId);
    }
    //历史审批意见表中查询批注
    public List<ActHiComment> getuserIdAndCommentByprocInstId(String procInstId) {
        return actHiCommentMapper.getComByProDfId(procInstId);
    }

    public AppAuthOrg getOrg(String orgId) {
        AppAuthOrg org= new AppAuthOrg();
        org.setId(orgId);

        AppAuthOrg appAuthOrg = appOrgMapper.selectOne(org);
        return appAuthOrg;
    }

    public List<AppUser> getAppUserByIdList(List<String> list) {
        List<AppUser> appUserList = new ArrayList<>();
        if (list != null && list.size()>0){
            for (String id : list) {
                AppUser appUser = new AppUser();
                appUser.setId(id);
                AppUser appUser1 = appUserMapper.selectOne(appUser);
                appUserList.add(appUser1);
            }
        }
        return appUserList;
    }

    public String getSalemanId(String purId, String supplierId) {
        FyQuote t=new FyQuote();
        t.setFyPurchaseId(purId);
        t.setPkSupplierId(supplierId);
        return fyQuoteMapper.select(t).get(0).getSupplierSalesmanId();
    }

    public SupplierSalesman getSaleManById(String id) {
        SupplierSalesman t=new SupplierSalesman();
        t.setId(id);
        return supplierSalesmanMapper.selectByPrimaryKey(t);
    }

    public List<Supplier> getPayExpertResult(List<Supplier> supplierList, String purId) {
        //purid supplierId    quoteId
        //purId quoteId  expertId
        //for expertId
        for (Supplier supplier : supplierList) {
            supplier.setPayAllExpert(true);
            FyQuote t= new FyQuote();
            t.setPkSupplierId(supplier.getPkSupplier());
            t.setFyPurchaseId(purId);
            List<FyQuote> quoteList = fyQuoteMapper.select(t);
            for (FyQuote fyQuote : quoteList) {
                PurchaseDetail fd=new PurchaseDetail();
                fd.setFyPurchaseId(purId);
                fd.setQuoteId(fyQuote.getId());
                PurchaseDetail purchaseDetail = fyPurchaseDetailMapper.selectOne(fd);
                if (purchaseDetail!=null){
                    for (String expertId : purchaseDetail.getExpertId().split(",")) {
                        ExpertRecommend er=new ExpertRecommend();
                        er.setExpertId(expertId);
                        er.setPurchaseId(purId);
                        ExpertRecommend expertRecommend = expertRecommendMapper.selectOne(er);
                        if ( expertRecommend.getCommission()==0+""){
                            supplier.setPayAllExpert(false);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return supplierList;
    }
}
