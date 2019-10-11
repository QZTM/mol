package com.mol.supplier.mapper.newMysql.dingding.schedule;

import com.mol.base.BaseMapper;
import com.mol.supplier.entity.dingding.approve.PurchaseApprove;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleMapper extends BaseMapper<PurchaseApprove> {

    List<PurchaseApprove> selectByUserIdAndState(Map paraMap);
    PurchaseApprove selectDetailByIds(Map paraMap);
}
