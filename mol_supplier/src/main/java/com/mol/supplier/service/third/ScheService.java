package com.mol.supplier.service.third;

import com.github.pagehelper.PageHelper;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.supplier.entity.dingding.solr.fyPurchase;
import com.mol.supplier.entity.thirdPlatform.FyQuote;
import com.mol.supplier.mapper.newMysql.dingding.purchase.BdSupplierMapper;
import com.mol.supplier.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.supplier.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.supplier.mapper.newMysql.third.FyQuoteMapper;
import com.mol.supplier.mapper.newMysql.third.TpMapper;
import com.mol.supplier.util.StatusScheUtils;
import com.mol.supplier.util.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        fyPurchase = StatusScheUtils.getStatusIntegerToString(fyPurchase);
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
}
