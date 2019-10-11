package com.mol.purchase.service.activiti;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.mol.purchase.config.Constant;
import com.mol.purchase.service.token.TokenService;
import com.mol.purchase.config.URLConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.TimeUtil;

/**
 * ClassName:SendMessageService
 * Package:com.purchase.util
 * Description
 *  发送审批工作通知
 * @date:2019/9/28 11:22
 * @author:yangjiangyan
 */
@Service
public class SendMessageService {

    @Autowired
    private TokenService tokenService;


    /**
     * 发送工作通知
     * @param userId  钉钉id
     * @return
     */
    public  void sendMessage(String userId){
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.MESSAGE_ASYNCSEND);

        OapiMessageCorpconversationAsyncsendV2Request messageRequest = new OapiMessageCorpconversationAsyncsendV2Request();
        messageRequest.setUseridList(userId);
        messageRequest.setAgentId(Constant.AGENTID);
        messageRequest.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        String nowDateTime = TimeUtil.getNowDateTime();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(nowDateTime+"，有新的订单需要您审批，快去处理呀！");
        messageRequest.setMsg(msg);

        try {
            OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(messageRequest, tokenService.getToken());
            System.out.println(rsp.getErrcode());
            System.out.println(rsp.getTaskId());
            System.out.println(rsp.getErrmsg());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
