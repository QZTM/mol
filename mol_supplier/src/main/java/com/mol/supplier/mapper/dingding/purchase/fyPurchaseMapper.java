package com.mol.supplier.mapper.dingding.purchase;

import com.mol.supplier.entity.dingding.login.AppUser;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import com.mol.supplier.entity.dingding.solr.fyPurchase;
import com.mol.supplier.entity.thirdPlatform.BdMarbasclass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * fypurchase表
 */
@Mapper
public interface fyPurchaseMapper {

    void insertStrategyPur(StraregyObj obj);

    List<fyPurchase> findListByStatus(String status_f, String status_s, String status_t);

    int findCountByStatus(String status_f, String status_s, String status_t);

    String  getQuoteCountsByPurchaseId(String id);

    void addQuoteCountsByPurchaseId(String id, String counts);

    List<fyPurchase> findListByIdAndGoodsType(String buyId, String goodsType);

    int findCountByIdAndStatus(@Param("buyId") String buyId, @Param("status") String status, @Param("list") List<PurchaseDetail> list);

    List<fyPurchase> findListByIdAndStatus(String buyId, String status);

    List<fyPurchase> selectOrderByDeadLine(String nowDateTime);

    void updateStatusById(String id, String status);

    List<fyPurchase> findListByPurchaseDetailList(List<PurchaseDetail> purDetailList);

    List<fyPurchase> findList(@Param("buyId") String buyId, @Param("status") String status, @Param("list") List<PurchaseDetail> list);

    List<fyPurchase> findListByIdlistAndStatus(@Param("status_f") String status, @Param("status_s") String status_s, @Param("status_t") String status_t, @Param("status_fo") String status_fo, @Param("list") List<String> quoteIdList);

    List<fyPurchase> findListByOrgIdAndStatus(String orgId, String status, String secondStatus);

    fyPurchase findOneById(String id);

    void updateAppUserListById(String id, String arr);

    String findAppUserListById(String id);

    void updateExplainById(String purId, String explain);

    void updateStatusAndApproverProposalById(String purId, String passOrNot, String approverProposal);

    List<fyPurchase> findListByOrgIdAndStaffId(String orgId, String userId);

    List<fyPurchase> findPurchaseByIdList(List<String> arr);

    List<fyPurchase> findListByBuyChannIdAndMarbascAndStatus(String buyId, String goodsType, String status);

    Integer findCountByBuyChannIdAndMarbascAndStatus(String buyId, String status, String goodsType);
}
