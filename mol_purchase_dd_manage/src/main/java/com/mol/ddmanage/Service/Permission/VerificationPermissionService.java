package com.mol.ddmanage.Service.Permission;

import com.mol.ddmanage.mapper.Permission.VerificationPermissionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class VerificationPermissionService//验证登录人权限
{
    @Resource
    VerificationPermissionMapper verificationPermissionMapper;
    public boolean VerificationPermissionLogic(String userid ,String PageName)
    {
      ArrayList<Map> verifications= verificationPermissionMapper.VerificationPermission(userid);//获取这个用户自身绑定角色的集合
      for (int n=0;n<verifications.size();n++)
      {
          if (verifications.get(n).get(PageName).toString().equals("1"))//集合里有任一一个身份可以访问这个页面就通过
          {
              return true;
          }
      }
      return false;//改用户绑定的所有角色都没有权限访问给定的页面
    }
}
