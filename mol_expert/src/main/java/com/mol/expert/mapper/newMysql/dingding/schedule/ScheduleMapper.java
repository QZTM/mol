package com.mol.expert.mapper.newMysql.dingding.schedule;

import com.mol.expert.base.BaseMapper;
import com.mol.expert.entity.dingding.approve.PurchaseApprove;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleMapper extends BaseMapper<PurchaseApprove> {

    List<PurchaseApprove> selectByUserIdAndState(Map paraMap);
    PurchaseApprove selectDetailByIds(Map paraMap);
}
