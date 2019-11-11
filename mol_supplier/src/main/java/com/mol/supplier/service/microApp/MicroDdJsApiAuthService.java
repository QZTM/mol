package com.mol.supplier.service.microApp;

import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.mol.supplier.config.MicroAttr;
import com.mol.supplier.exception.microApp.OApiException;
import com.mol.supplier.util.PageUrlUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.RandomStr;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于生成钉钉jsApi鉴权信息
 */
@Service
@Log
public class MicroDdJsApiAuthService {

    @Autowired
    private MicroJsapiTicketService microJsapiTicketService;


    public Map getAuthMap(HttpServletRequest request){
        /**
         * 随机字符串
         */
        String url = PageUrlUtils.getPageUrl(request);
        return getAuthMap(url);
    }


    public Map getAuthMap(String url){
        /**
         * 随机字符串
         */
        log.info("获取jsapi鉴权数据,,,,,,url:"+url);
        String nonceStr = RandomStr.getRandom(7, RandomStr.TYPE.LETTER);
        long timeStamp = System.currentTimeMillis() / 1000;
        //String accessToken = microTokenService.getToken();;
        String ticket = microJsapiTicketService.getJsApiTicket();;
        String signature = null;

        try {
            signature = sign(ticket, nonceStr, timeStamp, url);
        } catch (OApiException e) {
            e.printStackTrace();
        }
        Map<String, Object> configValue = new HashMap<>();
        configValue.put("jsticket", ticket);
        configValue.put("signature", signature);
        configValue.put("nonceStr", nonceStr);
        configValue.put("timeStamp", timeStamp);
        configValue.put("corpId", MicroAttr.CROPID);
        configValue.put("agentId", MicroAttr.AGENTID);
        return configValue;
    }



    public String sign(String ticket, String nonceStr, long timeStamp, String url) throws OApiException {
        try {
            return DingTalkJsApiSingnature.getJsApiSingnature(url, nonceStr, timeStamp, ticket);
        } catch (Exception ex) {
            throw new OApiException();
        }
    }
}
