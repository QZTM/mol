package com.mol.ddmanage.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.mol.ddmanage.config.Dingding_config;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static com.dingtalk.api.DingTalkSignatureUtil.urlEncode;

public class Dingding_Tools
{
    public static String GetAPPdingding_token()//使用app的token
    {
        try
        {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(Dingding_config.AppKey);
            request.setAppsecret(Dingding_config.AppSecret);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        }
        catch (Exception e)
        {
            return null;
        }
    }


    private final static String SDINGTALKSERVICE="https://oapi.dingtalk.com";


    //获取扫码登录的token
    public static String getAccessToken() throws ApiException {
        OapiSnsGettokenResponse response = null;

        DingTalkClient client = new DefaultDingTalkClient(SDINGTALKSERVICE + "/sns/gettoken");
        OapiSnsGettokenRequest request = new OapiSnsGettokenRequest();
        request.setAppid("dingoa95guzn8q0hi2h69z");
        request.setAppsecret("Gvg-lNJOSZ_q_CsxXJEnZDntE5ivCVKDm8kFiMsckTvMy_yNWvZOhcXZiLPZIEIQ");
        request.setHttpMethod("GET");
        response = client.execute(request);
        String body = response.getBody();
        JSONObject jo = JSON.parseObject(body);
        String errcode = jo.getString("errcode");
        String access_token = null;
        if ("0".equals(errcode)) {
            access_token = (String) jo.get("access_token");
        }
        return access_token;
    }

    public static String GetScanCodeUserinfor(String loginCode)//获取扫码登录个人信息
    {

        try {
            DefaultDingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
            OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
            req.setTmpAuthCode(loginCode);
            OapiSnsGetuserinfoBycodeResponse response = client.execute(req,"dingoa95guzn8q0hi2h69z","Gvg-lNJOSZ_q_CsxXJEnZDntE5ivCVKDm8kFiMsckTvMy_yNWvZOhcXZiLPZIEIQ");
            return   response.getBody();
        }
        catch (Exception e)
        {
      return "erre";
        }

    }


    //获取持久登录
    public  static String get_persistent_code(String accessToken, String code)//手机扫描登录
    {
        OapiSnsGetPersistentCodeResponse response = null;
        try {
            DingTalkClient client = new DefaultDingTalkClient(SDINGTALKSERVICE+"/sns/get_persistent_code");
            OapiSnsGetPersistentCodeRequest request = new OapiSnsGetPersistentCodeRequest();
            request.setTmpAuthCode(code);
            response= client.execute(request,accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }

       return response.getBody();
    }

    public  static String get_OApersistent_code( String code)//OA后台登录获取授权码
    {
        //OapiSnsGetPersistentCodeResponse response = null;
        try {
            String stringToSign = DataUtil.GetTimestamp();
            Mac mac = Mac.getInstance("HmacSHA256");
            String appSecret="-JIuLf10RziuY_bqIYuOac2CHnBKRdE9eLfEmLQ6GE38QNx0K9L6h7eWIam-MG_a";
            mac.init(new SecretKeySpec(appSecret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String signature = new String(Base64.encodeBase64(signatureBytes));
            String urlEncodeSignature = urlEncode(signature,"utf-8");

            DefaultDingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
            OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
            req.setTmpAuthCode(urlEncodeSignature);
            OapiSnsGetuserinfoBycodeResponse response = client.execute(req, "dingoahnrus1n2u9i3duvx","-JIuLf10RziuY_bqIYuOac2CHnBKRdE9eLfEmLQ6GE38QNx0K9L6h7eWIam-MG_a");
            return response.getBody();
        }
        catch (Exception e) {
           return e.toString();
        }


    }



    // 获取用户授权的SNS_TOKEN

    public static String get_sns_token(String openId, String persistentCode,String accessToken) {
        OapiSnsGetSnsTokenResponse response = null;
        try {
            DingTalkClient client = new DefaultDingTalkClient(SDINGTALKSERVICE+"/sns/get_sns_token");
            OapiSnsGetSnsTokenRequest request = new OapiSnsGetSnsTokenRequest();
           // request.setHttpMethod("POST");
            request.setOpenid(openId);
            request.setPersistentCode(persistentCode);
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return response.getSnsToken();
    }

    //当前登录的个人信息
    public  static String get_sns_userinfo_unionid(String snsToken) {
        OapiSnsGetuserinfoResponse response = null;
        try {
            DingTalkClient client = new DefaultDingTalkClient(SDINGTALKSERVICE + "/sns/getuserinfo");
            OapiSnsGetuserinfoRequest request = new OapiSnsGetuserinfoRequest();
            request.setSnsToken(snsToken);
            request.setHttpMethod("GET");
            response = client.execute(request);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return response.getBody();
    }

    //获取userid
    public static String getUseridByUnionid(String accessToken,String unionid) {
        OapiUserGetUseridByUnionidResponse response = null;
        try {
            DingTalkClient client = new DefaultDingTalkClient(SDINGTALKSERVICE + "/user/getUseridByUnionid");
            OapiUserGetUseridByUnionidRequest request = new OapiUserGetUseridByUnionidRequest();
            request.setUnionid(unionid);
            request.setHttpMethod("GET");
            response = client.execute(request, accessToken);
        }
        catch (ApiException e)
        {
            e.printStackTrace();
        }
        return response.getBody();
    }


    //获取用户详细信息
    public  static String Get_User_infor(String userid)
    {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userid);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, Dingding_config.DingdingAPP_Token);
            return response.getBody();
        }
        catch (Exception e)
        {
            return "";
        }
    }


    public static String GetDepartmentInfor(String DepartmentId)//获取当前部门的子部门
    {
        try
        {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
            OapiDepartmentListRequest request = new OapiDepartmentListRequest();
            request.setId(DepartmentId);
            request.setHttpMethod("GET");
            OapiDepartmentListResponse response = client.execute(request, Dingding_config.DingdingAPP_Token);
            return response.getBody();
        }
        catch (Exception e)
        {
            return "errer";
        }
    }

    public static void CreateDepartment()//创建部门
    {
        try
        {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/create");
            OapiDepartmentCreateRequest request = new OapiDepartmentCreateRequest();
            request.setParentid("133809769");
            request.setCreateDeptGroup(true);
            request.setOrder("100");
            request.setName("test部门2");
            OapiDepartmentCreateResponse response = client.execute(request,Dingding_config.DingdingAPP_Token);
        }
        catch (Exception e)
        {

        }

    }

    public static String GetDepartmentAllUser(String DepartmentId)
    {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
            OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
            req.setDeptId( DepartmentId);
            req.setHttpMethod("GET");
            OapiUserGetDeptMemberResponse rsp = client.execute(req, Dingding_config.DingdingAPP_Token);
            return rsp.getBody();

        }
        catch (Exception e)
        {
            return e.toString();
        }
    }

    public static String GetDepartmentUser(long userId)//获取部门里的用户
    {
        try
        {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
            OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
            request.setDepartmentId(userId);
            request.setOffset(0L);
            request.setSize(10L);
            request.setHttpMethod("GET");

            OapiUserSimplelistResponse response = client.execute(request,Dingding_config.DingdingAPP_Token);
            return response.getBody();
        }
        catch (Exception e)
        {
            return "errer";
        }
    }
}
