package com.mol.expert.service.microApp;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.taobao.api.ApiException;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 获取微应用jsapiticket(缓存)
 */
@Service
public class MicroJsapiTicketService {

    private static final Logger bizLogger = LoggerFactory.getLogger(MicroJsapiTicketService.class);

    public static final String MICROAPPJSAPITICKETKEY = "zhuanjiajsApiTicket";

    @Autowired
    private MicroEhcacheCacheService microEhcacheCacheService;


    //@Cacheable(value = "jsapiticket",key="#key")
    @Synchronized
    public String getJsApiTicket(String key) {
        String jsapiTicket = (String) microEhcacheCacheService.getMicroJsapiTicketCacheService().get(key);
        if (jsapiTicket == null) {
            bizLogger.info("访问钉钉服务器获取JSAPITICKET");
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
            OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
            req.setTopHttpMethod("GET");
            try {
                OapiGetJsapiTicketResponse execute = client.execute(req, MicroTokenService.MICROAPPACCESSTOKEN);
                System.out.println(execute.getExpiresIn());
                bizLogger.info("JSAPITICKET:" + execute.getTicket());
                jsapiTicket = execute.getTicket();
            } catch (ApiException e) {
                e.printStackTrace();
                throw new RuntimeException("获取ticket时出错");
            }
        }
        return jsapiTicket;
    }

}
