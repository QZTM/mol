package com.mol.supplier.service.third;

import com.github.pagehelper.PageHelper;
import com.mol.supplier.entity.MicroApp.Salesman;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.supplier.entity.dingding.solr.fyPurchase;
import com.mol.supplier.entity.thirdPlatform.*;
import com.mol.supplier.mapper.newMysql.dingding.purchase.BdSupplierMapper;
import com.mol.supplier.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.supplier.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.supplier.mapper.newMysql.dingding.workBench.PoOrderMapper;
import com.mol.supplier.mapper.newMysql.microApp.MicroSalesmanMapper;
import com.mol.supplier.mapper.newMysql.third.BdMarbasclassMapper;
import com.mol.supplier.mapper.newMysql.third.FyQuoteMapper;
import com.mol.supplier.mapper.newMysql.third.TpMapper;
import com.mol.supplier.util.StatusUtils;
import entity.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;
import util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ThirdPlatformService {


    @Autowired
    private TpMapper tpMapper;

    @Autowired
    private fyPurchaseMapper fyPurchaseMapper;

    @Autowired
    private fyPurchaseDetailMapper fyPurchaseDetailMapper;

    @Autowired
    private BdSupplierMapper bdSupplierMapper;

    @Autowired
    private FyQuoteMapper fyQuoteMapper;

    @Autowired
    private BdMarbasclassMapper bdMarbasclassMapper;

    @Autowired
    private PoOrderMapper poOrderMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MicroSalesmanMapper salesmanMapper;

    //enter排版
    public List<Enter> findAll() {
        List<Enter> all = tpMapper.findAll();
        return all;
    }

    //轮播
    public List<Lunbo> findLunBo() {
        return tpMapper.findLunBo();
    }

    public List<fyPurchase> findList(String buyId, int pageNumber, int pageSize) {
        if (pageNumber <= 0) {//第几页
            pageNumber = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNumber, pageSize);
        //询价采购的buyid==4
        List<fyPurchase> list = tpMapper.findList(buyId);
        List<fyPurchase> newList = new ArrayList<>();
        for (fyPurchase pu : list) {
            fyPurchase fy = StatusUtils.getStatusIntegerToString(pu);

            //单一来源，通过供应商id查询公司名称
            String supplierName = bdSupplierMapper.getSupplierNameById(fy.getPkSupplier());
            fy.setPkSupplier(supplierName);

            newList.add(fy);
        }

        return list;
    }

    public List<fyPurchase> findList(String buyId, int pageNumber, int pageSize, String goodsType) {
        if (pageNumber <= 0) {//第几页
            pageNumber = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }

        //通过goodType查询该下订单中的goodType集合
        List<BdMarbasclass> marList = null;
        //第四级  如果为空  则表示只有三级，
        marList = bdMarbasclassMapper.findFourMarListByPkmarbasClass(goodsType);
        if (marList == null || marList.size() <= 0) {
            //第三级
            marList = bdMarbasclassMapper.findThreeMarListByPkmarbasClass(goodsType);
        }

        //查询订单id，条件是订单详情表中的pkmaterial在marlist中
        List<PurchaseDetail> purDetailList = fyPurchaseDetailMapper.getFyPurchaseDetailListByMarbasClassList(marList);

        List<fyPurchase> newList = new ArrayList<>();
        if (purDetailList != null && purDetailList.size() > 0) {
            //查询订单list，条件是再purdetailList中的id
            PageHelper.startPage(pageNumber, pageSize);
            List<fyPurchase> list = fyPurchaseMapper.findListByPurchaseDetailList(purDetailList);

            for (fyPurchase pu : list) {
                fyPurchase fy = StatusUtils.getStatusIntegerToString(pu);

                //单一来源，通过供应商id查询公司名称
                String supplierName = bdSupplierMapper.getSupplierNameById(fy.getPkSupplier());
                fy.setPkSupplier(supplierName);

                newList.add(fy);
            }

            return newList;
        } else {
            return newList;
        }

    }

    public List<fyPurchase> findListByStatusAndId(String buyId, int pageNumber, int pageSize, String status) {
        if (pageNumber <= 0) {//第几页
            pageNumber = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNumber, pageSize);
        List<fyPurchase> list = fyPurchaseMapper.findListByIdAndStatus(buyId, status);
        List<fyPurchase> newList = new ArrayList<>();
        for (fyPurchase pu : list) {
            fyPurchase fy = StatusUtils.getStatusIntegerToString(pu);

            //单一来源，通过供应商id查询公司名称
            String supplierName = bdSupplierMapper.getSupplierNameById(fy.getPkSupplier());
            fy.setPkSupplier(supplierName);

            newList.add(fy);
        }

        return list;
    }

//    public int findCount(String id) {
//        return tpMapper.findCount(id);
//    }

    /**
     * 根据id,状态，行业类别  获取订单数量
     * @param buyId
     * @param status
     * @param goodsType
     * @return
     */
    public Integer findCount(String buyId,String status,String goodsType) {
        List<PurchaseDetail> purchaseDetailList = getPurChaseDetail(goodsType);
        return fyPurchaseMapper.findCountByIdAndStatus(buyId, status,purchaseDetailList);
    }


//    public List<String> findTypes() {
//        return tpMapper.findTypeList();
//    }

    public fyPurchase selectOneById(String id) {
        fyPurchase fyPurchase = tpMapper.selectOneById(id);
        fyPurchase = StatusUtils.getStatusIntegerToString(fyPurchase);
        String supplierNameById = bdSupplierMapper.getSupplierNameById(fyPurchase.getPkSupplier());
        fyPurchase.setPkSupplier(supplierNameById);
        return fyPurchase;
    }

    //获取公司上次该物料的报价
    public Double getAvgPrice(String supplierId,String id) {
        Double b =0.00;
        List<Double> bList =poOrderMapper.getNorigpriceBySupplierIdAndMaterialId(supplierId,id);
        if (bList != null && bList.size()>0){
            b=bList.get(0);
        }
        return b;
    }

    public List<fyPurchase> findListByStatus(String status, int pageNum, int pageSize) {
        if (pageNum <= 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 6;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<fyPurchase> fyPurchaseList = fyPurchaseMapper.findListByStatus(status,null,null);
        List<fyPurchase> fList = new ArrayList<>();
        for (fyPurchase fyPurchase : fyPurchaseList) {
            fList.add(StatusUtils.getStatusIntegerToString(fyPurchase));
        }
        return fList;
    }

    public int findCountByStatus(String status) {
        return fyPurchaseMapper.findCountByStatus(status,null,null);
    }

    //设置页码的方法
    public List<Integer> getPageNumList(PageBean pb) {
        List<Integer> pageNumList = new ArrayList<>();
        //如果小于五页，直接最大页数
        if (pb.getTotalPage() <= 5) {
            for (int i = 1; i <= pb.getTotalPage(); i++) {
                pageNumList.add(i);
            }
        } else {
            //如果大于五个
            if (pb.getCurrentPage() <= 3) {
                //当前页码小于等于3 存放1-5
                for (int i = 1; i < 6; i++) {
                    pageNumList.add(i);
                }
            } else if (pb.getCurrentPage() >= pb.getTotalPage() - 2) {
                //当前页码大于等于总页数-2 存放总页码->总页码-4
                for (int i = pb.getTotalPage() - 4; i <= pb.getTotalPage(); i++) {
                    pageNumList.add(i);
                }
            } else {
                //其他则存放当前页码-2 ，当前页码+2
                for (int i = pb.getCurrentPage() - 2; i <= pb.getCurrentPage() + 2; i++) {
                    pageNumList.add(i);
                }
            }
        }
        return pageNumList;
    }

    /**
     * 保存报价信息
     *
     * @param quotes
     */
    public void saveQuote(QuoteModel quotes,String supplierId,Salesman salesman) {

        List<FyQuote> quo = quotes.getQuotes();
        //供货周期
        String supplyCycle = quo.get(0).getSupplyCycle();
        for (FyQuote fyQuote : quo) {
            //------------------------------公司id  和职员id
            fyQuote.setId(idWorker.nextId() + "");
            fyQuote.setPkSupplierId(supplierId);
            fyQuote.setSupplierSalesmanId(salesman.getId());
            fyQuote.setCreationtime(TimeUtil.getNowDateTime());
            //设置供货周期
            fyQuote.setSupplyCycle(supplyCycle);
            //初始化专家推荐  1为推荐，0为不推荐
            fyQuote.setExpertRecommendation("0");
            fyQuoteMapper.insert(fyQuote);
        }

    }

    /**
     * 增加报价记录
     *
     * @param fyPurchaseId
     */
    public void addQuoteCountsByPkMaterialId(String fyPurchaseId) {
        String counts = fyPurchaseMapper.getQuoteCountsByPurchaseId(fyPurchaseId);
        Integer quoteCounts = 1;
        if (Integer.parseInt(counts) == 0) {
            fyPurchaseMapper.addQuoteCountsByPurchaseId(fyPurchaseId, quoteCounts + "");
        } else {
            quoteCounts = Integer.parseInt(counts);
            quoteCounts++;
            fyPurchaseMapper.addQuoteCountsByPurchaseId(fyPurchaseId, quoteCounts + "");
        }
    }

    /**
     * 获取物料类型第一类集合
     *
     * @return
     */
    public List<BdMarbasclass> findMarbasClassFirstList() {
        return bdMarbasclassMapper.findMarbasFirstList();
    }





    public List<fyPurchase> findLIstByStatusAndGoodsTypeAndBuyChannelId(String buyId, String status, String goodsType, Integer pageNumber,Integer pageSize) {

        List<PurchaseDetail> purDetailList = getPurChaseDetail(goodsType);
        List<fyPurchase> ceshi = fyPurchaseMapper.findList(buyId, status, purDetailList);
        System.out.println("测试length："+ceshi.size());
        PageHelper.startPage(pageNumber, pageSize);
        List<fyPurchase> list = fyPurchaseMapper.findList(buyId, status, purDetailList);
        System.out.println("响应length:"+list.size());
        List<fyPurchase> new_List=new ArrayList<>();
        for (fyPurchase pur : list) {
            fyPurchase fp = StatusUtils.getStatusIntegerToString(pur);
            new_List.add(fp);
        }
        return new_List;
    }

    private List<PurchaseDetail> getPurChaseDetail(String goodsType){
        //通过goodType查询该下订单中的goodType集合
        List<BdMarbasclass> marList = null;
        List<PurchaseDetail> purDetailList =null;
        if (goodsType != ""&&goodsType!=null) {
            //第四级  如果为空  则表示只有三级，
            marList = bdMarbasclassMapper.findFourMarListByPkmarbasClass(goodsType);
            if (marList == null || marList.size() <= 0) {
                //第三级
                marList = bdMarbasclassMapper.findThreeMarListByPkmarbasClass(goodsType);
            }
            //查询订单id，条件是订单详情表中的pkmaterial在marlist中
            purDetailList = fyPurchaseDetailMapper.getFyPurchaseDetailListByMarbasClassList(marList);
        }
        return purDetailList;
    }

    public Boolean isQuote(String id, String supplierId) {
        List<FyQuote> list = fyQuoteMapper.findQuoteBySupplierIdAndPurchaseId(id, supplierId);
        if (list.size()>0&&list!=null){
            return true;
        }else {
            return false;
        }
    }

    public Salesman findSalesManId(String ddUserId) {
        Salesman t=new Salesman();
        t.setDdUserId(ddUserId);
        return salesmanMapper.selectOne(t);
    }
}
