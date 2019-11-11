package com.mol.ddmanage.Service.DepartmentManagement;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mol.ddmanage.Ben.DepartmentManagement.DeparmentManagementben;
import com.mol.ddmanage.Util.Dingding_Tools;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DepartmentManagementListService
{
    public ArrayList GetOrganizationService(String DeparmentId)
    {
        ArrayList<DeparmentManagementben> deparmentManagementbens=new ArrayList<>();//填装所有此层级下的部门和人员信息

        String dingding_DepartmentInfor = Dingding_Tools.GetDepartmentInfor(DeparmentId);//获取部门基本信息
        JSONArray Departments= JSONArray.parseArray(JSONObject.parseObject(dingding_DepartmentInfor).getString("department"));//获取所有部门数组

        for (int n=0;n< Departments.size();n++)//获取此层级下的部门
        {
            DeparmentManagementben deparmentManagementben=new DeparmentManagementben();
            JSONObject item=JSONObject.parseObject(Departments.get(n).toString());
            if (item.getString("parentid").equals(DeparmentId))//为此部门下
            {
                deparmentManagementben.setName(item.getString("name"));
                deparmentManagementben.setId(item.getString("id"));
                deparmentManagementben.setType("1");//1为部门2为人员
                deparmentManagementbens.add(deparmentManagementben);
            }
        }
        String Userinfor=JSONObject.parseObject(Dingding_Tools.GetDepartmentUser(Long.parseLong(DeparmentId))).getString("userlist")  ;//获取本门所有用户
        JSONArray Userids=JSONArray.parseArray(Userinfor);

        for (int n=0;n<Userids.size();n++)//获取此层级下所有人员
        {
            DeparmentManagementben deparmentManagementben=new DeparmentManagementben();
            JSONObject item=JSONObject.parseObject(Userids.get(n).toString());
            deparmentManagementben.setName(item.getString("name"));
            deparmentManagementben.setId(item.getString("userid"));
            deparmentManagementben.setType("2");
            deparmentManagementbens.add(deparmentManagementben);
        }
        return deparmentManagementbens;
    }
}
