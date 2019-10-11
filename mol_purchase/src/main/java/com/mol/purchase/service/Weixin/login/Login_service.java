package com.mol.purchase.service.Weixin.login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Login_service
{
    public String Get_openid(Map map)
    {
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?");
        url.append("appid=");//appid设置
        url.append("wx29f66e2e1ad11fe0");//
        url.append("&secret=");//secret设置
        url.append("5c919548ce1767f370e7692afcc77510");//
        url.append("&js_code=");//code设置
        url.append(map.get("code"));//
        url.append("&grant_type=authorization_code");
        try {
            HttpClient client = HttpClientBuilder.create().build();//构建一个Client
            HttpGet get = new HttpGet(url.toString());    //构建一个GET请求
            HttpResponse response = client.execute(get);//提交GET请求
            HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
            String content = EntityUtils.toString(result);
            System.out.println(content);//打印返回的信息
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "errer";
        }
    }
}
