package com.mol.ddmanage.mapper.DepartmentMangement;

import java.util.ArrayList;
import java.util.Map;

public interface AddStaffToPositionPageMapper
{
    void DeleteStaffToPosition(String jurisdictionId);//删除人员职位
    void AddStaffToPosition(String id,String ddUserId,String jurisdictionId);//新增人员职位

    ArrayList<Map> GetPositionPeolpeLogic(String jurisdictionId);//获取权限已有的人员以及id
}
