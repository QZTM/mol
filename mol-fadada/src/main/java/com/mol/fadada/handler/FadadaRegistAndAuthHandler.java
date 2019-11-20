package com.mol.fadada.handler;

import com.alibaba.fastjson.JSON;
import com.fadada.sdk.client.FddClientBase;
import com.fadada.sdk.client.authForfadada.GetCompanyVerifyUrl;
import com.fadada.sdk.client.authForfadada.GetPersonVerifyUrl;
import com.fadada.sdk.client.authForfadada.model.AgentInfoINO;
import com.fadada.sdk.client.authForfadada.model.BankInfoINO;
import com.fadada.sdk.client.authForfadada.model.CompanyInfoINO;
import com.fadada.sdk.client.authForfadada.model.LegalInfoINO;
import entity.ServiceResult;
import lombok.extern.java.Log;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 法大大接口处理类
 */
@Log
public class FadadaRegistAndAuthHandler {

    /**
     * 参数
     */
    private static String APP_ID = "402664";
    private static String APP_SECRET = "xeVHW4Cbn8nWgwCWC16VDbVe";
    private static String V = "2.0";
    private static String HOST = "https://testapi.fadada.com:8443/api/";
    private static FddClientBase base = new FddClientBase(APP_ID,APP_SECRET,V,HOST);

    private String customer_name;
    private String id_card = "";// 身份证号码;
    private String ident_type = "";// 身份证类型 ;
    private String mobile;
    private String contract_id;
    private String template_id;
    private String transaction_id;
    private String email;
    private File file;
    private static String result_type = "";
    private static String cert_flag = "";
    private StringBuffer response = new StringBuffer("==================Welcome ^_^ ==================");

    private static final String CALLBACK_PERSON_AUTH = "http://fyycg88.vaiwan.com/fddCallback/personAuth" ;
    private static final String PERSON_AUTH_TOPAGE = "http://fyycg88.vaiwan.com/fddCallback/personAuthTo" ;
    private static final String CALLBACK_ORG_AUTH = "http://fyycg88.vaiwan.com/fddCallback/orgAuth" ;
    private static final String ORG_AUTH_TOPAGE = "http://fyycg88.vaiwan.com/fddCallback/orgAuthTo" ;


    /**
     * 注册账号
     * @param openId            客户在平台的唯一id
     * @param accountType       注册类型，1个人，2公司
     * @return                  {"code":1,"data":"FBE4FDA10E2E05C8DAF88B4E64D7EA52","msg":"success"}
     *                          data为客户法大大id
     */
    public synchronized static ServiceResult regAccount(String openId, String accountType){
        if(StringUtils.isEmpty(accountType) || !accountType.matches("[1,2]")){
            return ServiceResult.failureMsg("accountType(注册类型)参数异常！");
        }
        log.info("注册法大大账号，注册类型为"+("1".equals(accountType)?"个人":"企业")+",openId:"+openId);
        String resultStr = base.invokeregisterAccount(openId, accountType);
        String resultMsg =  JSON.parseObject(resultStr).getString("msg");
        if("success".equals(resultMsg)){
            return ServiceResult.success(JSON.parseObject(resultStr).getString("data"));
        }else{
            return ServiceResult.failureMsg(resultMsg);
        }
    }


    /**
     *获取个人实名认证地址
     * @param customerId            法大大id
     * @param verifiedWay           实名认证套餐类型 0:三要素标准方案； 1:三要素补充方案；  2:四要素标准方案； 3:四要素补充方案',
     * @param pageModify            是否允许用户页面修改 1 允许， 2 不允许
     * @param notifyUrl             回调地址 异步通知认证结果
     * @param returnUrl             认证成功后跳转的页面url
     * @return                      实名认证地址
     */
    public synchronized static ServiceResult getAuthPersonurl(String customerId, String verifiedWay, String pageModify, String notifyUrl,String returnUrl){
        log.info("开始获取个人实名认证地址，customerId:"+customerId+",notifyUrl:"+notifyUrl);
        Map resultMap = new HashMap();
        ServiceResult failResult = ServiceResult.failure("");
        if(StringUtils.isEmpty(customerId)){
            failResult.setMessage("customerId不能未空！");
            return failResult;
        }
        if(StringUtils.isEmpty(verifiedWay) || !verifiedWay.matches("[0,1,2]")){
            failResult.setMessage("verifiedWay(实名认证套餐类型)参数异常！");
            return failResult;
        }
        if(StringUtils.isEmpty(pageModify) || !pageModify.matches("[1,2]")){
            failResult.setMessage("pageModify(是否允许用户页面修改)参数异常！");
            return failResult;
        }
        if(StringUtils.isEmpty(notifyUrl)){
            failResult.setMessage("notifyUrl不能未空！");
            return failResult;
        }
        GetPersonVerifyUrl personverify = new GetPersonVerifyUrl(APP_ID,APP_SECRET,V,HOST);
        String result = personverify.invokePersonVerifyUrl(customerId,verifiedWay,
                pageModify,CALLBACK_PERSON_AUTH,PERSON_AUTH_TOPAGE,"","",
                "","","","1","0");
        log.info("获取个人实名认证地址api返回：result:"+result);
        String resultCode = JSON.parseObject(result).getString("code");
        if(!StringUtils.isEmpty(resultCode) && "1".equals(resultCode)){
            String data = JSON.parseObject(result).getString("data");
            String url = "";
            if (null !=data){
                url = JSON.parseObject(data).getString("url");
                url = decode(url);
            }
            resultMap.put("url",url);
            resultMap.put("transactionNo",JSON.parseObject(data).getString("transactionNo"));
            return ServiceResult.success(resultMap);
        }else{
            failResult.setMessage(JSON.parseObject(result).getString("msg"));
            return failResult;
        }
    }

    public static synchronized ServiceResult getAuthPersonurl(String customerId,String notifyUrl,String returnUrl){
        return getAuthPersonurl(customerId,"0","1",notifyUrl,returnUrl);
    }


    /**
     * 获取企业实名认证地址
     * @param customerId            客户法大大id
     * @param pageModify            是否允许用户页面修改 1 允许， 2 不允许 如果传空或者不是1或者2，那么自动为1
     * @param companyPrincipalType  企业负责人身份: 1. 法人， 2. 代理人
     * @param notifyUrl             回调地址异步通知认证结果
     * @param resultType            刷脸是否显示结果页面 参数值为“1”：直接跳转到 return_url 或法大大指定页 面，
     *                                                 参数值为“2”：需要用户点击 确认后跳转到 return_url或法大大指定页面
     *                              不传参或参数不为1、2，则默认为 1
     * @param verifyedWay           实名认证套餐类型  0：标准方案（对公打款+纸质审核）默认0； 1：对公打款； 2：纸质审核',不传或者不为0、1、2则为0
     * @param certFlay              是否认证成功后自动申请实名证书 参数值为“0”：不申请，参数值为“1”：自动申请，如果不传或不为0或者1，那么为0
     * @param companyInfo           法大大sdk带的公司信息实体类，可为null
     * @param bankInfo              法大大sdk带的对公账户实体类，可为null
     * @param legalInfo             法大大sdk带的法人信息实体类，可为null
     * @param agentInfo             法大大sdk带的代理人信息实体类，可为null
     */
    public static ServiceResult getAuthCompanyurl(String customerId, String pageModify, String companyPrincipalType ,String verifyedWay, String notifyUrl, String resultType, String certFlay, CompanyInfoINO companyInfo, BankInfoINO bankInfo, LegalInfoINO legalInfo, AgentInfoINO agentInfo){
        log.info("获取企业认证url开始：customerId:"+customerId+",notifyUrl:"+notifyUrl);
        Map resultMap = new HashMap();
        ServiceResult failResult = ServiceResult.failure("");
        if(StringUtils.isEmpty(customerId)){
            failResult.setMessage("customerId为空！");
            return failResult;
        }
        if(StringUtils.isEmpty(pageModify) || !pageModify.matches("[1,2]")){
            pageModify = "1";
        }
        if(StringUtils.isEmpty(companyPrincipalType) || !companyPrincipalType.matches("[1,2]")){
            companyPrincipalType = "1";
        }
        if(StringUtils.isEmpty(verifyedWay) || !verifyedWay.matches("[0,1,2]")){
            verifyedWay = "0";
        }
        if(StringUtils.isEmpty(notifyUrl)){
            failResult.setMessage("notifyUrl(异步通知地址)为空！");
            return failResult;
        }
        if(StringUtils.isEmpty(verifyedWay) || !verifyedWay.matches("[0,1,2]")){
            failResult.setMessage("verifyedWay(实名认证套餐类型)参数错误！");
            return failResult;
        }
        if(StringUtils.isEmpty(resultType) || !resultType.matches("[1,2]")){
            resultType = "1";
        }
        if(StringUtils.isEmpty(certFlay) || !certFlay.matches("[1,0]")){
            certFlay = "0";
        }
        GetCompanyVerifyUrl comverify = new GetCompanyVerifyUrl(APP_ID,APP_SECRET,V,HOST);
        String result = comverify.invokeCompanyVerifyUrl(companyInfo,bankInfo,legalInfo
                ,agentInfo, customerId,verifyedWay,"",pageModify,
                "1",ORG_AUTH_TOPAGE,CALLBACK_ORG_AUTH,resultType,certFlay);
        log.info("获取企业实名认证url,,返回值："+result);
        String data = JSON.parseObject(result).getString("data");
        String resultCode = JSON.parseObject(result).getString("code");
        String url = "";
        if (null !=data){
            url = JSON.parseObject(data).getString("url");
            url = decode(url);
        }
        if("1".equals(resultCode)){
            resultMap.put("url",url);
            resultMap.put("transactionNo",JSON.parseObject(data).getString("transactionNo"));
            return ServiceResult.success(resultMap);
        }else{
            failResult.setMessage(JSON.parseObject(result).getString("msg"));
            return failResult;
        }
    }

    /**
     * 获取企业实名认证地址（默认配置版）
     * @param customerId
     * @param notifyUrl
     * @return
     */
    public static synchronized ServiceResult getAuthCompanyurl(String customerId,String notifyUrl,LegalInfoINO legalInfo){
        return getAuthCompanyurl(customerId,"","","",notifyUrl,"","",null,null,legalInfo,null);
    }


    /**
     * 测试
     * 个人法大大id:8FDB50D6DBC69A64A3A7FF7804930EF7
     * 企业法大大id:1CAC3BF68CDAEDC8B6D43D57BB7BD719
     *
     * @param args
     */
    public static void main(String[] args) {
        //ServiceResult s = regAccount("159862222", "2");
        ServiceResult map = getAuthPersonurl("8FDB50D6DBC69A64A3A7FF7804930EF7","0","2","http://fyycg88.vaiwan.com/callback/fdd","http://fyycg88.vaiwan.com/callback/fdd");
        //String s = regAccount("1598622567", "2");
//        LegalInfoINO legalInfo = new LegalInfoINO();
//        legalInfo.setLegal_name("刘洋");
//        legalInfo.setLegal_id("12345");
//        legalInfo.setLegal_mobile("15376375268");
//        ServiceResult map =getAuthCompanyurl("1CAC3BF68CDAEDC8B6D43D57BB7BD719","http://fyycg88.vaiwan.com/callback/fdd",legalInfo);
                System.out.println(map);
    }





    private static String decode(String bizContent) {
        try {
            bizContent = URLDecoder.decode(bizContent, "utf-8");
            bizContent = new String(Base64.decodeBase64(bizContent.getBytes()));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
        return bizContent;
    }



}
