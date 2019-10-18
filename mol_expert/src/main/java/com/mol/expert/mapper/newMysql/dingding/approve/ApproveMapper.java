package com.mol.expert.mapper.newMysql.dingding.approve;

import com.mol.expert.base.BaseMapper;
import com.mol.expert.entity.dingding.approve.PurchaseApprove;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ApproveMapper extends BaseMapper<PurchaseApprove> {

    int updateStatus(PurchaseApprove pa);

    int savePageContent(Map saveMap);

}
