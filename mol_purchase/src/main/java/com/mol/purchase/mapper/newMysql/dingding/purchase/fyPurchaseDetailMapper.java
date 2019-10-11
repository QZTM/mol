package com.mol.purchase.mapper.newMysql.dingding.purchase;

import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.BdMarbasclass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface fyPurchaseDetailMapper {


    void insert(PurchaseDetail pd);

    List<PurchaseDetail> getFyPurchaseDetailListByMarbasClassList(List<BdMarbasclass> marList);

    void updataQuoteIdByPurIdAndPkMatId(String purId, String materId, String quoteId);

    List<PurchaseDetail> findPurchaseDetailList(String id);

    PurchaseDetail findPurchaseDetailByPurIdAndQuoteId(String id, String quoteId);
}
