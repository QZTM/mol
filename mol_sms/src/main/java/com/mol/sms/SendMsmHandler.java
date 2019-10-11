package com.mol.sms;

/**
 * 发送短信处理器
 */
public interface SendMsmHandler {



    public static final String SIGNNAME = "茉尔易购";
    /**
     * 模板名称
     */
    public static final String TEMPLATENAME_SUPPLIER_AUTH = "供应商认证验证码";
    public static final String TEMPLATENAME_SUPPLIER_REGIST = "供应商注册验证码";
    /**
     * 小牛云模板ID
     */
    public static final String TEMPLATECODE_SUPPLIER_AUTH = "1002";
    public static final String TEMPLATECODE_SUPPLIER_REGIST = "1001";




    /**
     * 发送短信验证码
     * @param templateName      模板名称
     * @param templateCode      模板ID
     * @param phoneNumber       手机号码
     * @return
     */
    public String sendMsm(String templateName,String templateCode,String phoneNumber);


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
