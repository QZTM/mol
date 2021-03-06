package com.mol.supplier.mapper.dingding.approve;

import com.mol.base.BaseMapper;
import com.mol.supplier.entity.dingding.approve.PurchaseApprove;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ApproveMapper extends BaseMapper<PurchaseApprove> {

    int updateStatus(PurchaseApprove pa);

    int savePageContent(Map saveMap);

}
