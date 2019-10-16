package com.mol.expert.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.mol.expert.config.Constant;
import com.mol.expert.config.URLConstant;
import com.mol.expert.service.dingding.login.TokenService;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DDBaseService {

    private static final Logger bizLogger = LoggerFactory.getLogger(DDBaseService.class);

    @Autowired
    private TokenService tokenService;

    public void sendMessageToOriginator(String processInstanceId) throws RuntimeException {
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_PROCESSINSTANCE_GET);
            OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
            request.setProcessInstanceId(processInstanceId);
            OapiProcessinstanceGetResponse response = client.execute(request, tokenService.getToken("token"));
            String recieverUserId = response.getProcessInstance().getOriginatorUserid();

            sendMessageToSomeBody(recieverUserId,"您的审批已通过");
        } catch (ApiException e) {
            bizLogger.error("send message failed", e);
            throw new RuntimeException();
        }
    }





    /**
     * 给某人发送某信息
     * @param userId        某人ID
     * @param message       某信息ID
     */
    public void sendMessageToSomeBody(String userId,String message){
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.MESSAGE_ASYNCSEND);
            OapiMessageCorpconversationAsyncsendV2Request messageRequest = new OapiMessageCorpconversationAsyncsendV2Request();
            messageRequest.setUseridList(userId);
            messageRequest.setAgentId(Constant.AGENTID);
            messageRequest.setToAllUser(false);
            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msg.setMsgtype("text");
            msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
            msg.getText().setContent(message);
            messageRequest.setMsg(msg);
            OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(messageRequest,tokenService.getToken("token"));
        } catch (ApiException e) {
            bizLogger.error("send message failed", e);
            throw new RuntimeException();
        }
    }
}
