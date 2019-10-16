package com.mol.expert.service.dingding.schedule;

import com.mol.expert.entity.MicroApp.Salesman;
import com.mol.expert.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.expert.entity.dingding.purchaseOrder.Supplier;
import com.mol.expert.entity.thirdPlatform.FyQuote;
import com.mol.expert.entity.activiti.ActHiComment;
import com.mol.expert.entity.activiti.ActHiProcinst;
import com.mol.expert.entity.dingding.login.AppAuthOrg;
import com.mol.expert.entity.dingding.login.AppUser;
import com.mol.expert.entity.dingding.solr.fyPurchase;
import com.mol.expert.mapper.newMysql.dingding.activiti.ActHiCommentMapper;
import com.mol.expert.mapper.newMysql.dingding.activiti.ActHiProcinstMapper;
import com.mol.expert.mapper.newMysql.dingding.org.AppOrgMapper;
import com.mol.expert.mapper.newMysql.dingding.purchase.BdSupplierMapper;
import com.mol.expert.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.expert.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.expert.mapper.newMysql.dingding.user.AppUserMapper;
import com.mol.expert.mapper.newMysql.microApp.MicroSalesmanMapper;
import com.mol.expert.mapper.newMysql.third.FyQuoteMapper;
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
    private FyQuoteMapper  fyQuoteMapper;

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
    private MicroSalesmanMapper salesmanMapper;


    public List<fyPurchase> getList(String orgId, String userId) {
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

    /**
     * 查询公司业务员list
     * @param supplierId 公司id
     * @return
     */
    public List<Salesman> findListBySupplierId(String supplierId) {
        Salesman sm = new Salesman();
        sm.setPkSupplier(supplierId);
        List<Salesman> select = salesmanMapper.select(sm);
        return select;
    }

    /**
     * 查询订单的报价业务员id
     * @param purId 订单id
     * @return
     */
    public List<FyQuote> findSalemanIdByPurId(String purId) {
        List<FyQuote> quoteBySupplierIdAndPurchaseId = fyQuoteMapper.findQuoteByPurchaseId(purId);
        return quoteBySupplierIdAndPurchaseId;
    }
}
