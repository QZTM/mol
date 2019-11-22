package com.mol.ddmanage.mapper.DepartmentMangement;

import java.util.ArrayList;
import java.util.Map;

public interface UpdateUserMapper
{
    ArrayList<Map> GetTableAllUser();
    void AddUser( String id,String userName,String ddUserId,String lastTime);
}
