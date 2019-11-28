package com.mol.supplier.service.third;

import com.github.pagehelper.PageHelper;
import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseArray;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.supplier.entity.dingding.solr.fyPurchase;
import com.mol.supplier.entity.thirdPlatform.FyQuote;
import com.mol.supplier.mapper.dingding.purchase.BdSupplierMapper;
import com.mol.supplier.mapper.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.supplier.mapper.dingding.purchase.fyPurchaseMapper;
import com.mol.supplier.mapper.third.FyQuoteMapper;
import com.mol.supplier.mapper.third.TpMapper;
import com.mol.supplier.util.StatusScheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ScheService
 * Package:com.mol.supplier.service.third
 * Description
 *
 * @date:2019/9/9 13:15
 * @author:yangjiangyan
 */
@Service
public class ScheService {

    @Autowired
    private fyPurchaseMapper fyPurchaseMapper;

    @Autowired
    private fyPurchaseDetailMapper fyPurchaseDetailMapper;

    @Autowired
    private TpMapper tpMapper;

    @Autowired
    private BdSupplierMapper bdSupplierMapper;

    @Autowired
    private FyQuoteMapper fyQuoteMapper;

    public List<fyPurchase> findListByStatus(String status_f,String status_s,String status_t, int pageNum, int pageSize) {
        if (pageNum <= 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 6;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<fyPurchase> fyPurchaseList = fyPurchaseMapper.findListByStatus(status_f,status_s,status_t);
        List<fyPurchase> fList = new ArrayList<>();
        for (fyPurchase fyPurchase : fyPurchaseList) {
            fList.add(StatusScheUtils.getStatusIntegerToString(fyPurchase));
        }
        return fList;
    }
    public int findCountByStatus(String status_f,String status_s,String status_t) {
        return fyPurchaseMapper.findCountByStatus( status_f, status_s, status_t);
    }

    public List<String> findPurIdListBySupplierId(String supplierId) {
        return fyQuoteMapper.findFypurchaseIdListBySupplierId(supplierId);
    }

    public List<fyPurchase> findListByIdList(String status_f,String status_s,String status_t,String status_fo,List<String> purIdList) {
        if (purIdList==null || purIdList.size()==0 ){
            return new ArrayList<>();
        }
        List<fyPurchase> fyPurchaseList = fyPurchaseMapper.findListByIdlistAndStatus(status_f,status_s,status_t, status_fo,purIdList);
        List<fyPurchase> fList = new ArrayList<>();
        for (fyPurchase fyPurchase : fyPurchaseList) {
            fList.add(StatusScheUtils.getStatusIntegerToString(fyPurchase));
        }
        return fList;
    }

    public List<FyQuote> findQuoteById(String id,String supplierId) {
        return fyQuoteMapper.findQuoteBySupplierIdAndPurchaseId(id, supplierId);
    }
    public fyPurchase selectOneById(String id) {
        fyPurchase fyPurchase = tpMapper.selectOneById(id);
        fyPurchase= StatusScheUtils.getStatusIntegerToString(fyPurchase);
        String supplierNameById = bdSupplierMapper.getSupplierNameById(fyPurchase.getPkSupplier());
        fyPurchase.setPkSupplier(supplierNameById);
        return fyPurchase;
    }

    public List<FyQuote> findQuoteBySupplierIdAndPurId(String supplierId, String id) {
        return fyQuoteMapper.findQuoteBySupplierIdAndPurchaseId(id,supplierId);
    }

    public PurchaseDetail findPurchDetailByPurIdAndQuoteId(String id, String quoteId) {
        return fyPurchaseDetailMapper.findPurchaseDetailByPurIdAndQuoteId(id,quoteId);
    }

    public List<PurchaseDetail> findPurchDetailByPurId(String id) {
        return fyPurchaseDetailMapper.findPurchaseDetailList(id);
    }

    public List<FyQuote> findPassQuoteByPurId(String id) {
        return  fyQuoteMapper.findPassQuoteByPurId(id);
    }

    public Supplier findSupplierById(String pkSupplierId) {
        Supplier t=new Supplier();
        t.setPkSupplier(pkSupplierId);
        return bdSupplierMapper.selectOne(t);
    }

    public int getCountWinningKidSupplier(String id) {
        return fyQuoteMapper.findCountWinningKidSupplierByPurId(id);
    }


//    public Map<String,List<PurchaseArray>> getList(int count,List<FyQuote> passQuoteList, List<PurchaseArray> purchaseArrayList) {
//        Map<String,List<PurchaseArray>>  map=new HashMap<String,List<PurchaseArray>>();
//        List<PurchaseArray> list=new ArrayList<>();
//        if (count==1){
//            //只有一家
//            Supplier t =new Supplier();
//            t.setPkSupplier(passQuoteList.get(0).getPkSupplierId());
//            Supplier supplier = bdSupplierMapper.selectOne(t);
//            for (int i=0;i<purchaseArrayList.size();i++){
//                for (int j=0;j<passQuoteList.size();j++){
//                    if (purchaseArrayList.get(i).getMaterialId()==passQuoteList.get(j).getPkMaterialId()){
//                        PurchaseArray pa =new PurchaseArray();
//                        pa.setMaterialId(purchaseArrayList.get(i).getMaterialId());
//                        pa.setTypeName(purchaseArrayList.get(i).getTypeName());
//                        pa.setItemName(purchaseArrayList.get(i).getItemName());
//                        pa.setNorms(purchaseArrayList.get(i).getNorms());
//                        pa.setCount(purchaseArrayList.get(i).getCount());
//                        pa.setUnit(purchaseArrayList.get(i).getUnit());
//                        pa.setQuote(passQuoteList.get(j).getQuote());
//                        list.add(pa);
//                    }
//                }
//            }
//            map.put(supplier.getName(),list);
//            return map;
//        }else {
//            //多商家采购
//            for (int i=0;i<passQuoteList.size();i++) {//先遍历报价
//                Supplier t =new Supplier();
//                t.setPkSupplier(passQuoteList.get(0).getPkSupplierId());
//                Supplier supplier = bdSupplierMapper.selectOne(t);
//                map.put(supplier.getName(),list);
//                for (int j = 0; j < purchaseArrayList.size(); j++) {
//
//                }
//            }
//        }
//    }
}
