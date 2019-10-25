package com.mol.notification;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.mol.config.URLConstant;
import com.taobao.api.ApiException;
import entity.ServiceResult;

import util.TimeUtil;


public class SendNotificationImp implements SendNotification{


    private static SendNotificationImp sendNotificationImp;

    private SendNotificationImp(){ }

    public static SendNotificationImp getSendNotification(){
        if (sendNotificationImp==null){
            return new SendNotificationImp();
        }else {
            return sendNotificationImp;
        }
    }
    /**
     * E应用通知
     * @param userIdList 接受通知的id字符串
     * @param userName 当前处理人的名称
     * @param token token
     * @param agentId   应用agentId
     * @return
     */
    @Override
    public ServiceResult sendOaFromE(String userIdList,String userName,String token,long agentId ) {
        //String user="266752374326324047";
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.MESSAGE_ASYNCSEND);

        OapiMessageCorpconversationAsyncsendV2Request messageRequest = new OapiMessageCorpconversationAsyncsendV2Request();
        messageRequest.setUseridList(userIdList);
        messageRequest.setAgentId(agentId);
        messageRequest.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setMessageUrl("eapp://pages/purchase/workbench/tobeapproved/tobeapproved");
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().getHead().setText("云采购");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setContent(TimeUtil.getNowDateTime()+"有新的采购订单需要您审批，点击查看吧！");
        msg.getOa().getBody().setImage("http://140.249.22.202:8082/static/upload/imgs/supplier/ask.png");
        msg.getOa().getBody().setTitle("审批");
        if (userName!=null){
            msg.getOa().getBody().setAuthor(userName);//上一个审批人员
        }
        msg.setMsgtype("oa");
        messageRequest.setMsg(msg);

//        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(messageRequest,microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY));
        OapiMessageCorpconversationAsyncsendV2Response rsp = null;
        try {
            rsp = client.execute(messageRequest,token);
            return ServiceResult.success("发送成功");
        } catch (ApiException e) {
            e.printStackTrace();
            return ServiceResult.failure("发送失败");
        }

    }

    /**
     * 第三方报价平台发布
     * @param userIdList 发送人id的string ，如 111,222
     * @param agentId 应用agentId
     * @param token
     * @return
     */
    @Override
    public ServiceResult sendOaFromThird(String userIdList,Long agentId,String token){
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.MESSAGE_ASYNCSEND);

        OapiMessageCorpconversationAsyncsendV2Request messageRequest = new OapiMessageCorpconversationAsyncsendV2Request();
        messageRequest.setUseridList(userIdList);
        messageRequest.setAgentId(agentId);
        messageRequest.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setMessageUrl("http://fyycg2.vaiwan.com/index/findAll");
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().getHead().setText("摩尔易购");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setContent(TimeUtil.getNowDateTime()+"有新的采购订单来了，快去报价吧！");
        msg.getOa().getBody().setImage("http://140.249.22.202:8082/static/upload/imgs/supplier/ask.png");
        msg.getOa().getBody().setTitle("新订单");
        msg.setMsgtype("oa");
        messageRequest.setMsg(msg);

//        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(messageRequest,microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY));
        OapiMessageCorpconversationAsyncsendV2Response rsp = null;
        try {
            rsp = client.execute(messageRequest,token);
            return ServiceResult.success("发送成功");
        } catch (ApiException e) {
            e.printStackTrace();
            return ServiceResult.failure("发送失败");
        }
    }

    /**
     * 专家平台
     * @param userIdList 发送人id的string ，如 111,222
     * @param agentId 应用agentId
     * @param token
     * @return
     */
    @Override
    public ServiceResult sendOaFromExpert(String userIdList,Long agentId,String token){
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.MESSAGE_ASYNCSEND);

        OapiMessageCorpconversationAsyncsendV2Request messageRequest = new OapiMessageCorpconversationAsyncsendV2Request();
        messageRequest.setUseridList(userIdList);
        messageRequest.setAgentId(agentId);
        messageRequest.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setMessageUrl("http://fyycg2.vaiwan.com/index/findAll");
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().getHead().setText("摩尔易购");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setContent(TimeUtil.getNowDateTime()+"有新的采购订单来了，快去报价吧！");
        msg.getOa().getBody().setImage("http://140.249.22.202:8082/static/upload/imgs/supplier/ask.png");
        msg.getOa().getBody().setTitle("新订单");
        msg.setMsgtype("oa");
        messageRequest.setMsg(msg);

//        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(messageRequest,microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY));
        OapiMessageCorpconversationAsyncsendV2Response rsp = null;
        try {
            rsp = client.execute(messageRequest,token);
            return ServiceResult.success("发送成功");
        } catch (ApiException e) {
            e.printStackTrace();
            return ServiceResult.failure("发送失败");
        }
    }

}
