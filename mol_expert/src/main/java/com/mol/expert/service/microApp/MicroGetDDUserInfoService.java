package com.mol.expert.service.microApp;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MicroGetDDUserInfoService {


    @Autowired
    private MicroTokenService microTokenService;

    private Logger logger = LoggerFactory.getLogger(MicroGetDDUserInfoService.class);
    /**
     * 根据免登授权码获取用户id
     * @param code
     * @return
     */
    public String getDDUserId(String code) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(code);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = new OapiUserGetuserinfoResponse();
        try{
            response = client.execute(request, microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY));
        }catch (Exception e){
            logger.error("通过免登授权码获取用户id失败（ddAPI）");
            e.printStackTrace();
        }
        String userId = response.getUserid();
        logger.info("userId:"+userId);
        return userId;
    }

    /**
     * 根据用户id获取用户详情
     * @param userId
     * @return
     */
    public OapiUserGetResponse getUserDetail(String userId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = new OapiUserGetResponse();
        try{
            response = client.execute(request, microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY));
        }catch (Exception e){
            logger.error("根据用户id获取用户详情失败（ddAPI）");
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 获取用户的部门信息
     * @param aLong
     * @return
     */
    public OapiDepartmentGetResponse getDeptInfo(Long aLong) {
        return getADeptInfo(aLong);
    }

    /**
     * 获取用户的企业信息
     * @return
     */
    public OapiDepartmentGetResponse getOrgInfo(Long along) {
        return getADeptInfo(along);
    }


    /**
     * 获取用户的某部门信息
     * @return
     */
    public OapiDepartmentGetResponse getADeptInfo(Long along) {
        if(along == -99L){
            along = 1L;
        }
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(along+"");
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = new OapiDepartmentGetResponse();
        try{
            response = client.execute(request, microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY));
        }catch (Exception e){
            logger.error("获取部门信息时出错（ddAPI）");
            e.printStackTrace();
        }
        return response;
    }



}
