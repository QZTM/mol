package com.mol.supplier.service.third.callbackEventServices;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiServiceActivateSuiteRequest;
import com.dingtalk.api.request.OapiServiceGetPermanentCodeRequest;
import com.dingtalk.api.request.OapiServiceGetSuiteTokenRequest;
import com.dingtalk.api.response.OapiServiceActivateSuiteResponse;
import com.dingtalk.api.response.OapiServiceGetPermanentCodeResponse;
import com.dingtalk.api.response.OapiServiceGetSuiteTokenResponse;
import com.mol.supplier.config.Constant;
import com.taobao.api.ApiException;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 授权开通
 */
@Service
public class TmpAuthCodeService implements BaseEventService{

    private Logger logger = LoggerFactory.getLogger(TmpAuthCodeService.class);

    @Setter
    private String eventType;
    /**
     * 临时授权码
     */
    @Setter
    private String authCode ;
    /**
     * 应用的SuiteKey
     */
    @Setter
    private String suiteKey ;
    /**
     * 时间戳
     */
    @Setter
    private String timeStamp ;
    /**
     * 授权开通应用企业的corpId
     */
    @Setter
    private String authCorpId ;

    @Setter
    private String suiteAccessToken;

    /**
     * 企业永久授权码
     */
    @Setter
    private String permanentCode;

    @Setter
    private String corpId;

    @Setter
    private String corpName;

    @Override
    public void action(JSONObject obj) {
        //把得到的值赋值给属性
        this.setAttr(obj);

        //获取suite_access_token
        getSuiteAccessToken();

        //获取企业永久授权码
        getForeverAuthCode();

        //激活应用
        activateApp();
    }

    //把得到的值赋值给属性
    public void setAttr(JSONObject obj){
        setEventType((String)obj.getString("EventType"));
        setAuthCorpId((String)obj.getString("AuthCorpId"));
        setAuthCode((String)obj.getString("AuthCode"));
        setSuiteKey((String)obj.getString("SuiteKey"));
        setTimeStamp((String)obj.getString("TimeStamp"));
    }

    //获取企业永久授权码
    public void getForeverAuthCode() {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/service/get_permanent_code?suite_access_token=" + suiteAccessToken);
        OapiServiceGetPermanentCodeRequest req = new OapiServiceGetPermanentCodeRequest();
        req.setTmpAuthCode(authCode);
        OapiServiceGetPermanentCodeResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            throw new RuntimeException("获取企业永久授权码出错，"+e.getMessage());
        }
        Map maps = (Map)JSON.parse(rsp.getBody());
        setPermanentCode((String)maps.get("permanent_code"));
        JSONObject obj = (JSONObject)maps.get("auth_corp_info");
        setCorpId(obj.getString("corpid"));
        setCorpName(obj.getString("corp_name"));
    }

    //获取第三方应用凭证suite_access_token
    public void getSuiteAccessToken(){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/service/get_suite_token");
        OapiServiceGetSuiteTokenRequest request = new OapiServiceGetSuiteTokenRequest();
        request.setSuiteKey(Constant.THIRD_SUITEKEY);
        request.setSuiteSecret(Constant.THIRD_SUITE_SECRET);
        request.setSuiteTicket("xxx");//**************************正式应用需要填写正确

        OapiServiceGetSuiteTokenResponse response = null;
        try {
            response = client.execute(request);
        } catch (ApiException e) {
            throw new RuntimeException("获取第三方应用凭证出错，"+e.getMessage());
        }
        Map maps = (Map)JSON.parse(response.getBody());
        if((int)maps.get("errcode")!=0){
            throw new RuntimeException("获取第三方应用凭证出错，errcode:"+maps.get("errcode"));
        }
        setSuiteAccessToken((String)maps.get("suite_access_token"));
    }

    //激活应用
    public void activateApp(){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/service/activate_suite?suite_access_token=" + suiteAccessToken);
        OapiServiceActivateSuiteRequest req = new OapiServiceActivateSuiteRequest();
        req.setSuiteKey(suiteKey);
        req.setAuthCorpid(authCorpId);
        req.setPermanentCode(permanentCode);
        OapiServiceActivateSuiteResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            throw new RuntimeException("激活应用是发生异常，"+e.getMessage());
        }
        Map resultMap = (Map)JSON.parse(rsp.getBody());
        if((Integer)resultMap.get("errcode") == 0){
            logger.info("激活应用成功！");
        }
    }
}
