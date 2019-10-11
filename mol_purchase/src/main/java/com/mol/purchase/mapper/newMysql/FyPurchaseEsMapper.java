package com.mol.purchase.mapper.newMysql;

import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.StraregyObj;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FyPurchaseEsMapper {


    StraregyObj selectOrderById(String id);

    int insert(StraregyObj stObj);

    void updata(StraregyObj stObj);
}
