package com.mol.supplier.service.dingding.login;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.mol.supplier.config.Constant;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mol.supplier.config.URLConstant.URL_GET_TOKKEN;

@Service
@Transactional
public class TokenService {

    private static final Logger bizLogger = LoggerFactory.getLogger(TokenService.class);

    @Cacheable(value = "token",key="#key")
    public String getToken(String key){
        bizLogger.info("访问钉钉服务器获取小程序token");
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(URL_GET_TOKKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(Constant.APP_KEY);
            request.setAppsecret(Constant.APP_SECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            String accessToken = response.getAccessToken();
            bizLogger.info(accessToken);
            return accessToken;
        } catch (ApiException e) {
            bizLogger.error("getAccessToken failed", e);
            throw new RuntimeException("服务器通讯异常，请稍后再试");
        }
    }
}
