package com.mol.supplier.mapper.dingding.purchase;

import com.mol.supplier.entity.dingding.solr.fyPurchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface EnquiryPurMapper {
    String getMysqlId(String itemName);

    int getSupplierSellerNum(@Param("map") HashMap<String, String> map);

    //查询存入solr的值
    List<fyPurchase> getFyPurchase();
}
