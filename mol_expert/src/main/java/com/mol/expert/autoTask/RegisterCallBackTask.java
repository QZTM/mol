package com.mol.expert.autoTask;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.mol.expert.config.Constant;
import com.mol.expert.config.URLConstant;
import com.mol.expert.service.dingding.login.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 项目启动后删除原有的回调事件的注册，重新注册回调函数
 * implements ApplicationRunner
 */
@Component
@Order(700)
public class RegisterCallBackTask implements ApplicationRunner {
    @Autowired
    private TokenService tokenService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 先删除企业已有的回调
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.DELETE_CALLBACK);
        OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();request.setHttpMethod("GET");
        String token = tokenService.getToken("token");
        System.out.println("run...token:"+token);
        client.execute(request, tokenService.getToken("token"));

        // 重新为企业注册回调
        client = new DefaultDingTalkClient(URLConstant.REGISTER_CALLBACK);
        OapiCallBackRegisterCallBackRequest registerRequest = new OapiCallBackRegisterCallBackRequest();
        registerRequest.setUrl(Constant.CALLBACK_URL_HOST + "/callback");
        registerRequest.setAesKey(Constant.ENCODING_AES_KEY);
        registerRequest.setToken(Constant.TOKEN);
        registerRequest.setCallBackTag(Arrays.asList("bpms_instance_change", "bpms_task_change"));
        OapiCallBackRegisterCallBackResponse registerResponse = client.execute(registerRequest,tokenService.getToken("token"));
        if (registerResponse.isSuccess()) {
            System.out.println("回调注册成功了！！！");
        }
    }
}
