package com.mol.supplier.service.microApp;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.mol.cache.CacheHandle;
import com.mol.supplier.config.MicroAttr;
import com.taobao.api.ApiException;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.mol.supplier.config.URLConstant.URL_GET_TOKKEN;

/**
 * 获取微应用access_token(缓存)
 */
@Service
@Transactional
public class MicroTokenService {

    private static final Logger bizLogger = LoggerFactory.getLogger(MicroTokenService.class);

    @Autowired
    private CacheHandle cacheHandle;

    public static final String MICROAPPTOKENKEY = "mol-supplier-microAppToken";

    @Synchronized
    public String getToken(){
        String token = cacheHandle.getStr(MICROAPPTOKENKEY);
        if(token == null){
            bizLogger.info("访问钉钉服务器获取微应用token");
            try {
                DefaultDingTalkClient client = new DefaultDingTalkClient(URL_GET_TOKKEN);
                OapiGettokenRequest request = new OapiGettokenRequest();
                request.setAppkey(MicroAttr.APP_KEY);
                request.setAppsecret(MicroAttr.APP_SECRET);
                request.setHttpMethod("GET");
                OapiGettokenResponse response = client.execute(request);
                token = response.getAccessToken();
                bizLogger.info("微应用token:"+token);
                cacheHandle.saveStr(MICROAPPTOKENKEY,2*60*58,token);
                return token;
            } catch (ApiException e) {
                bizLogger.error("getAccessToken failed", e);
                throw new RuntimeException("服务器通讯异常，请稍后再试");
            }
        }
        return token;
    }
}
