package com.mol.ddmanage.Service.DepartmentManagement;

import com.mol.ddmanage.Util.DataUtil;
import com.mol.ddmanage.mapper.DepartmentMangement.UpdateUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class UpdateUserService//更新数据库里的user表
{
    @Resource
    UpdateUserMapper updateUserMapper;
   public boolean UpdateUserLogic(ArrayList<Map> UserIdName)
   {
       try
       {
           ArrayList<Map> AllUsers=updateUserMapper.GetTableAllUser();//获取数据表中所有的用户
           for (int n=0;n<UserIdName.size();n++)
           {
               if (AllUsers.size()!=0)
               {
                   for(int n_1=0;n_1<AllUsers.size();n_1++)
                   {
                       if (UserIdName.get(n).get("userid").equals(AllUsers.get(n_1).get("ddUserId")))//如果数据库里有这个用户就不要再插入
                       {
                           break;
                       }
                       else  if (AllUsers.size()-1==n_1)//最后一次循环依然没有再数据库中找到，需要将此用户插入用户表中
                       {
                           updateUserMapper.AddUser(DataUtil.GetTimestamp()+n,UserIdName.get(n).get("name").toString(),UserIdName.get(n).get("userid").toString(), "");
                       }
                   }
               }
               else
               {
                 updateUserMapper.AddUser(DataUtil.GetTimestamp()+n,UserIdName.get(n).get("name").toString(),UserIdName.get(n).get("userid").toString(),"");
               }

           }
           return true;
       }
       catch (Exception e)
       {
           return false;
       }
   }
}
