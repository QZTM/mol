package com.mol.ddmanage.Service.DepartmentManagement;

import com.mol.ddmanage.Ben.DepartmentManagement.jurisdictionManagementben;
import com.mol.ddmanage.mapper.DepartmentMangement.jurisdictionManagementListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class jurisdictionManagementListService
{
    @Resource
    jurisdictionManagementListMapper jurisdictionManagement;
    public ArrayList<jurisdictionManagementben> GetPositionListLogic()
    {
        ArrayList<jurisdictionManagementben> jurisdictionManagementbens = jurisdictionManagement.GetPositionList();
        for (int n=0;n<jurisdictionManagementbens.size();n++)
        {
            jurisdictionManagementbens.get(n).setNumber(String.valueOf(n));
            if (jurisdictionManagementbens.get(n).getStatus()!=null && jurisdictionManagementbens.get(n).getStatus().equals("1"))
            {
                jurisdictionManagementbens.get(n).setStatus("正常");
            }
            else
            {
                jurisdictionManagementbens.get(n).setStatus("停用");
            }
        }
        return jurisdictionManagementbens;
    }

    public Map DeleteJurisdictionLogic(String jurisdictionId)
    {
        Map map=new HashMap();
        try
        {
            jurisdictionManagement.DeleteJurisdiction(jurisdictionId);//删除角色
            jurisdictionManagement.Deleteuser_position(jurisdictionId);//删除角色对应的人员权限
            map.put("status",true);
            return map;
        }
        catch (Exception e)
        {
            map.put("status",false);
            return  map;
        }

    }
}
