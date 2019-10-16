package com.mol.sms;

/**
 * 发送短信处理器
 */
public interface SendMsmHandler {
    /**
     * 发送短信验证码
     * @param signName          应用名称
     * @param template          模板实例对象
     * @param phoneNumber       手机号码
     * @return
     */
    public String sendMsm(String signName,MsmTemplate template,String phoneNumber);


    /**
     * 验证短信验证码
     * @param phone
     * @param code
     * @return
     */
    public boolean checkPhoneCode(String phone,String code);


    public static SendMsmHandler getSendMsmHandler(){
        return XiaoNiuMsm.getXiaoNiuMsm();
    };




}
