package com.mol.purchase.service.token;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.mol.cache.CacheHandle;
import com.mol.purchase.config.Constant;
import com.taobao.api.ApiException;
import lombok.Synchronized;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mol.purchase.config.URLConstant.URL_GET_TOKKEN;

@Service
@Transactional
@Log
public class TokenService {

    @Autowired
    private CacheHandle cacheHandle;

    private final String appTokenKey = "purchase-app-token";

    @Synchronized
    public String getToken(){
        String token = cacheHandle.getStr(appTokenKey);
        if(token == null){
            log.info("访问钉钉服务器获取token");
            try {
                DefaultDingTalkClient client = new DefaultDingTalkClient(URL_GET_TOKKEN);
                OapiGettokenRequest request = new OapiGettokenRequest();
                request.setAppkey(Constant.APP_KEY);
                request.setAppsecret(Constant.APP_SECRET);
                request.setHttpMethod("GET");
                OapiGettokenResponse response = client.execute(request);
                token = response.getAccessToken();
                log.info("钉钉E应用token:"+token);
                cacheHandle.saveStr(appTokenKey,60*50*2,token);
                return token;
            } catch (ApiException e) {
                log.warning("getAccessToken failed");
                e.printStackTrace();
                throw new RuntimeException("服务器通讯异常，请稍后再试");
            }
        }
        return token;
    }
}
