package com.mol.ddmanage.Service;

import com.alibaba.fastjson.JSONObject;
import com.mol.ddmanage.Util.Dingding_Tools;
import com.mol.ddmanage.Util.Token_hand;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
@Service
public class LoginService
{
    public Map LoginService_dingding(Map map, HttpSession session)
    {

        Map map1=new HashMap();
        try
        {

            String token= Dingding_Tools.getAccessToken();//获取扫码应用的登录token
            String persistentCode=Dingding_Tools.get_persistent_code(token,map.get("code").toString());//获取长期授权码
            JSONObject object = JSONObject.parseObject(persistentCode);//解析json
            String sns_token=Dingding_Tools.get_sns_token(object.getString("openid"),object.getString("persistent_code"),token);//获取sns_token
            String people= Dingding_Tools.get_sns_userinfo_unionid(sns_token);//获取登录人员基本新
            JSONObject user_info=JSONObject.parseObject(people);
            String unionid=JSONObject.parseObject(user_info.getString("user_info")).getString("unionid") ;
            String userid=JSONObject.parseObject(Dingding_Tools.getUseridByUnionid(Dingding_Tools.GetAPPdingding_token(),unionid)).getString("userid") ;//获取userid

            if (userid!=null && userid!="")
            {

                session.setAttribute("token", Token_hand.CreateMyToken(userid));//这里存储的是本地服务器的token
                session.setAttribute("userid",userid);
                String user_infor=Dingding_Tools.Get_User_infor(userid);//获取登录人员详细信息

                map1.put("name",JSONObject.parseObject(user_infor).get("name").toString());
                map1.put("rest",true);

            }
            else
            {
                map1.put("rest",false);
            }
            return map1;
        }
        catch (Exception e)
        {
            map1.put("rest",false);
            return map1;
        }
    }
}
