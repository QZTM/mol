package com.mol.ddmanage.Dingding;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mol.ddmanage.Service.DepartmentManagement.UpdateUserService;
import com.mol.ddmanage.Util.Dingding_Tools;
import com.mol.ddmanage.config.Dingding_config;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class LogServer {
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=1000*3000)
    private void configureTasks() {
        Dingding_config.DingdingAPP_Token= Dingding_Tools.GetAPPdingding_token();
        if ( Dingding_config.DingdingAPP_Token!=null)
        {
            System.out.println("服务注册成功");
            UpdateUser();
        }
        else
        {
            System.out.println("服务注册失败");
        }
}

    @Resource
    UpdateUserService updateUserService;
    private void UpdateUser()
   {
    ArrayList<String> departmentid=new ArrayList<>();//存储所有部门id
    ArrayList<Map> AllUserNameId=new ArrayList<>();//存储公司所有userid map=> name userid
    departmentid.add("1");//
    String allDepartmentinfor=Dingding_Tools.GetDepartmentInfor("1");//获取所有子部门信息
    JSONObject jsonObject=JSONObject.parseObject(allDepartmentinfor);//转成json
    String departmentids=jsonObject.get("department").toString();//只拿取部门信息
    JSONArray jsonArray=JSONObject.parseArray(departmentids);//部门信息转json数组


    for (int n=0;n<jsonArray.size();n++)//遍历所有部门id
    {
        JSONObject item=JSONObject.parseObject(jsonArray.get(n).toString());
        departmentid.add(item.get("id").toString());
    }

    for (int n=0;n<departmentid.size();n++)//遍历所有部门id
    {
        String DepartmentUserInfor=Dingding_Tools.GetDepartmentUser(Long.parseLong(departmentid.get(n)));//部门下的用户信息
        JSONObject DepartmentUserInforJson=JSONObject.parseObject(DepartmentUserInfor);//部门下的用户信息转json
        String DepartmentUsersJson=DepartmentUserInforJson.get("userlist").toString();//获取用户列表json字符串
        JSONArray Users=JSONArray.parseArray(DepartmentUsersJson);//用户列表字符串转json数组
        for (int n_1=0;n_1<Users.size();n_1++)
        {
            JSONObject item=JSONObject.parseObject(Users.get(n_1).toString());//获取单个用户的json串
            Map map=new HashMap();
            map.put("name",item.get("name"));
            map.put("userid",item.get("userid"));
            AllUserNameId.add(map);
        }
    }

    if (updateUserService.UpdateUserLogic(AllUserNameId)!=false)
    {
        System.out.println("人员更新完成");
    }
    else
    {
        System.out.println("人员更新失败");
    }

}
}
