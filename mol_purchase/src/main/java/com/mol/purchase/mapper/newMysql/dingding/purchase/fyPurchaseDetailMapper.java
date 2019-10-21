package com.mol.purchase.mapper.newMysql.dingding.purchase;

import com.mol.base.BaseMapper;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.BdMarbasclass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface fyPurchaseDetailMapper extends BaseMapper<PurchaseDetail> {


    int insert(PurchaseDetail pd);

    List<PurchaseDetail> getFyPurchaseDetailListByMarbasClassList(List<BdMarbasclass> marList);

    void updataQuoteIdAndExpertIdByPurIdAndPkMatId(String purId, String materId, String quoteId,String expertId);

    List<PurchaseDetail> findPurchaseDetailList(String id);

    PurchaseDetail findPurchaseDetailByPurIdAndQuoteId(String id, String quoteId);
}
