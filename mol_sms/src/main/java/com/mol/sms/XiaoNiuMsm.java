package com.mol.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.mol.cache.CacheHandle;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import util.RandomStr;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 小牛云发送短信
 * 单例
 */
@Log
public class XiaoNiuMsm implements SendMsmHandler{
    private volatile static XiaoNiuMsm xiaoNiuMsm;
    private XiaoNiuMsm(){

    }
    public static XiaoNiuMsm getXiaoNiuMsm() {
        if (xiaoNiuMsm == null) {
            synchronized (XiaoNiuMsm.class) {
                if (xiaoNiuMsm == null) {
                    xiaoNiuMsm = new XiaoNiuMsm();
                }
            }
        }
        return xiaoNiuMsm;
    }



    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "sms11.hzgxr.com:40081";

    @Setter
    private String phone = "";
    @Setter
    private String signName = "";
    @Setter
    private String templateCode = "";
    @Setter
    private String code = "";


    // TODO 此处需要替换成开发者自己的AK
    static final String accessKeyId = "XNY2_IU4=A2oXAH0qQF0=IwlfCw==";
    static final String accessKeySecret = "c81tY+jFcUc=GG8BGbeJkdY=";


    private CacheHandle cacheHandle = CacheHandle.getCacheHandle();



    @Override
    public String sendMsm(String signName, String templateCode, String phoneNumber) {
        String oldPhoneCode = cacheHandle.getStr(phoneNumber);
        if(oldPhoneCode == null){
            oldPhoneCode = RandomStr.getRandom(6, RandomStr.TYPE.LETTER);
        }
        return this.setSendParam(signName,templateCode,phoneNumber,oldPhoneCode).sendMes();
    }




    /**
     * 设置发送参数
     * @param signName
     * @param templateCode
     * @param phone
     * @param code
     * @return
     */
    private XiaoNiuMsm setSendParam(String signName,String templateCode,String phone,String code){
        this.setSignName(signName);
        this.setTemplateCode(templateCode);
        this.setPhone(phone);
        this.setCode(code);
        return this;
    }


    /**
     * 发送短信
     *
     * @return 如果发送成功则返回该验证码
     */
    public String sendMes() {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(signName) || StringUtils.isEmpty(templateCode) || StringUtils.isEmpty(code)) {
            throw new RuntimeException("发送短信时的参数有误");
        }
        try {
//            SendSmsResponse response = MicroSmsService.sendSms();
//            if("OK".equals(response.getMessage())){
//                return code;
//            }
            if (true) {
                saveToCache(phone,code);
                return code;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("发送短信时出错");
        }
        return null;
    }


    /*去缓存中获取*/
    public Object getCacheCode(String phone) {
        return cacheHandle.getStr(phone);
    }

    /*存入缓存*/
    public void saveToCache(String phone, String code) {
        cacheHandle.saveStr(phone,5*60, code);
    }

    public SendSmsResponse sendSms() throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setMethod(MethodType.POST);
        request.setAcceptFormat(FormatType.JSON);
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        String code = RandomStr.getRandom(6, RandomStr.TYPE.NUMBER);
        System.out.println("短信验证码：" + code);
        request.setTemplateParam("{ \"code\":\"" + code + "\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (Exception e) {
            throw new RuntimeException("发送短信验证码失败");
        }
        return sendSmsResponse;
    }


    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("13675862119");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }


    /*验证手机号及对应的验证码*/
    public boolean checkPhoneCode(String phone, String code) {
        if (StringUtils.isEmpty(phone)) {
            throw new RuntimeException("手机号码不能为空");
        }
        if (StringUtils.isEmpty(code)) {
            throw new RuntimeException("验证码不能为空");
        }
        Object savedCode = getCacheCode(phone);
        if (savedCode == null || "".equals((String) savedCode)) {
            return false;
        }
        if (code.equals((String) savedCode)) {
            return true;
        }
        return false;
    }




//    public static void main(String[] args) throws ClientException, InterruptedException {
//
//        //发短信
//        System.out.println("1");
//        SendSmsResponse response = sendSms();
//        System.out.println("短信接口返回的数据----------------");
//        System.out.println("Code=" + response.getCode());
//        System.out.println("Message=" + response.getMessage());
//        System.out.println("RequestId=" + response.getRequestId());
//        System.out.println("BizId=" + response.getBizId());
//
//        Thread.sleep(3000L);
//
//        //查明细
//        if(response.getCode() != null && response.getCode().equals("OK")) {
//            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
//            System.out.println("短信明细查询接口返回数据----------------");
//            System.out.println("Code=" + querySendDetailsResponse.getCode());
//            System.out.println("Message=" + querySendDetailsResponse.getMessage());
//            int i = 0;
//            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
//            {
//                System.out.println("SmsSendDetailDTO["+i+"]:");
//                System.out.println("Content=" + smsSendDetailDTO.getContent());
//                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
//                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
//                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
//                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
//                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
//                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
//                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
//            }
//            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
//            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
//        }
//
//    }
}
