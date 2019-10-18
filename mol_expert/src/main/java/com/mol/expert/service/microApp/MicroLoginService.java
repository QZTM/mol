package com.mol.expert.service.microApp;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptException;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.mol.expert.exception.microApp.OApiException;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MicroLoginService {

    private Logger logger = LoggerFactory.getLogger(MicroLoginService.class);

    @Autowired
    private MicroTokenService microTokenService;


    public String getDDUserId(String code){
        String userId = "";
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(code);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = null;
        try {
            response = client.execute(request, microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY));
        } catch (ApiException e) {
            throw new RuntimeException("根据免登授权码和access_token获取用户信息时出错！");
        }
        userId = response.getUserid();
        logger.info("获取钉钉用户userId.....userId"+userId);
        if(!StringUtils.isEmpty(userId)){
            return userId;
        }else{
            throw new RuntimeException("获取钉钉用户id时出错");
        }
    }

    /**
     * 根据钉钉用户ID获取用户详情
     * @param userId
     * @return
     */
    public OapiUserGetResponse getUserDetail(String userId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        try {
            OapiUserGetResponse response = client.execute(request, microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY));
            if(response.isSuccess()){
                return response;
            }else{
                throw new RuntimeException("根据用户名获取用户详情时出错");
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new RuntimeException("根据用户名获取用户详情时出错");
        }
    }


    /**
     * 根据ID获取部门信息
     *
     */
    public OapiDepartmentGetResponse getDeptInfo(Long deptId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(deptId+"");
        request.setHttpMethod("GET");
        try {
            OapiDepartmentGetResponse response = client.execute(request, microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY));
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
            throw new RuntimeException("根据部门id获取部门信息时出错,..deptId:"+deptId);
        }
    }


    /**
     * 根据ID获取部门信息
     *
     */
    public OapiDepartmentGetResponse getOrgInfo(){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(1+"");
        request.setHttpMethod("GET");
        try {
            OapiDepartmentGetResponse response = client.execute(request, microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY));
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
            throw new RuntimeException("获取组织信息时出错,..deptId:1");
        }
    }

    /*获取签名*/
    public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws OApiException {
        String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
                + "&url=" + url;
        try {
            return DingTalkJsApiSingnature.getJsApiSingnature(url, nonceStr, timeStamp, ticket);
        } catch (DingTalkEncryptException e) {
            e.printStackTrace();
            throw new RuntimeException("获取sign失败");
        }
    }


    public String  getToken() {
        String token = microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY);
        System.out.println(token);
        return token;
    }

}
