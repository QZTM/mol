package com.mol.purchase.service.dingding.schedule;

import com.github.pagehelper.PageHelper;
import com.mol.oos.OOSConfig;
import com.mol.oos.TYOOSUtil;
import com.mol.purchase.entity.*;
import com.mol.purchase.entity.activiti.ActHiActinst;
import com.mol.purchase.entity.activiti.ActHiComment;
import com.mol.purchase.entity.activiti.ActHiProcinst;
import com.mol.purchase.entity.dingding.login.AppAuthOrg;
import com.mol.purchase.entity.dingding.login.AppUser;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.dingding.solr.fyPurchase;
import com.mol.purchase.mapper.newMysql.BdSupplierSalesmanMapper;
import com.mol.purchase.mapper.newMysql.ExpertRecommendMapper;
import com.mol.purchase.mapper.newMysql.FyQuoteMapper;
import com.mol.purchase.mapper.newMysql.QuotePayresultMapper;
import com.mol.purchase.mapper.newMysql.dingding.activiti.ActHiActinstMapper;
import com.mol.purchase.mapper.newMysql.dingding.activiti.ActHiCommentMapper;
import com.mol.purchase.mapper.newMysql.dingding.activiti.ActHiProcinstMapper;
import com.mol.purchase.mapper.newMysql.dingding.org.AppOrgMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.BdSupplierMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.purchase.mapper.newMysql.dingding.user.AppUserMapper;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import util.TimeUtil;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private ActHiActinstMapper actHiActinstMapper;


    @Autowired
    private BdSupplierSalesmanMapper supplierSalesmanMapper;

    @Autowired
    private ExpertRecommendMapper expertRecommendMapper;

    @Autowired
    private QuotePayresultMapper quotePayresultMapper;

    @Autowired
    private TYOOSUtil tyoosUtil;

    private static final Logger logger= LoggerFactory.getLogger(SchedulerRepairService.class);


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
        List<FyQuote> select = fyQuoteMapper.select(t);
        if (select!=null){
            return select.get(0).getSupplierSalesmanId();
        }
        return null;
    }

    public SupplierSalesman getSaleManById(String id) {
        SupplierSalesman t=new SupplierSalesman();
        t.setId(id);
        return supplierSalesmanMapper.selectByPrimaryKey(t);
    }


    public QuotePayresult getPayExpertResult(String supplierId, String purId) {
        QuotePayresult t = new QuotePayresult();
        t.setSupplierId(supplierId);
        t.setPurchaseId(purId);
        return quotePayresultMapper.selectOne(t);
    }

    public List<ActHiActinst> getActHiActinstByPurId(String purId) {
        return actHiActinstMapper.getListByPurId(purId);
    }

    public List<AppUser> getAppUserByList(List<ActHiActinst> list) {
        List<AppUser> userList=new ArrayList<>();
        if (list!=null){
            for (int i=0;i<list.size();i++){
                AppUser t=new AppUser();
                t.setId(list.get(i).getAssignee());
                userList.add(appUserMapper.selectOne(t));
            }
        }
        return userList;
    }


    public void upload(MultipartFile multipartFile, String orgId,String purId) {
        //获取名字
        String fileName = multipartFile.getOriginalFilename();
        //获取后缀
        String  suffName= fileName.substring(fileName.lastIndexOf("."));
        //产生一个新名字
        String name = UUID.randomUUID()+suffName;
        //获取当前class的路径
        URL resource = SchedulerRepairService.class.getResource("");
        File file=new File(resource.getPath()+File.separator+name);
        try {
            //m 转 f
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),file);
            File newFile=new File(resource.getPath()+File.separator+name);
            //文件夹
            String url=orgId+"/"+purId+"/";
            tyoosUtil.uploadObjToBucket(OOSConfig.采购文件夹,url+name,newFile);

        } catch (IOException e) {
            logger.info("合同照片上传异常");
            e.printStackTrace();
        }finally {
            file.delete();
        }

    }

    public void downFromOOSImg(String bucket,String key){
//        try {
//            tyoosUtil.download(bucket,key);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        tyoosUtil.listObj(bucket,key);
    }

}
