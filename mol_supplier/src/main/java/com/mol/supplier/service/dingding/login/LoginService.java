package com.mol.supplier.service.dingding.login;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.mol.supplier.config.Constant;
import com.mol.supplier.config.URLConstant;
import com.mol.supplier.entity.dingding.login.AppAuthOrg;
import com.mol.supplier.entity.dingding.login.AppUser;
import com.mol.supplier.entity.dingding.login.DDDept;
import com.mol.supplier.entity.dingding.login.DDUser;
import com.mol.supplier.mapper.newMysql.dingding.org.AppOrgMapper;
import com.mol.supplier.mapper.newMysql.dingding.user.AppUserMapper;
import com.mol.supplier.util.DDEntityChangeUtil;
import com.mol.supplier.util.JWTUtil;
import com.taobao.api.ApiException;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;
import util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {


    private Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AppOrgMapper appOrgMapper;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * 登陆
     * @param requestAuthCode
     * @return
     */
    public ServiceResult login(String requestAuthCode) {
        Map resultMap = new HashMap();
        /*获取access_token*/
        String accessToken = tokenService.getToken("token");

        /*根据access_token获取用户信息*/
        String userId = getUserIdByAuthCode(accessToken,requestAuthCode);
        if(userId == null){
            throw new RuntimeException("获取用户信息失败，请稍后再试");
        }

        /*根据userId获取用户详情*/
        OapiUserGetResponse userProfile = getUserProfile(accessToken, userId);
        if(userProfile == null){
            throw new RuntimeException("获取用户信息失败，请稍后再试");
        }
        System.out.println(userProfile.getDepartment().toString());
        DDUser ddUser = DDEntityChangeUtil.changeOapiUserGetResponseToDDUser(userProfile);
        resultMap.put("ddUser",ddUser);
        System.out.println("ddUser:");
        System.out.println(ddUser);

        DDDept org = new DDDept();
        DDDept dept = new DDDept();
        /*根据部门id获取部门信息*/
        Long deptId = userProfile.getDepartment().get(0);
        System.out.println(deptId);
        // 审批里的部门id，1和-1要互相转换一下

        dept = getDeptDetail(deptId);
        if((deptId+"") == (1L+"")){
            org = dept;
        }else{
            org = getDeptDetail(1L);
        }

        /*根据企业钉钉验证一下*/
        Example orgExample = new Example(AppAuthOrg.class);
        orgExample.and().andEqualTo("orgName",org.getName());
        List<AppAuthOrg> appAuthOrg = appOrgMapper.selectByExample(orgExample);
        Map saveResultMap = new HashMap();
        if(appAuthOrg.size() == 0){
            //todo: 如需验证注册状态写在这里(暂时写注册的逻辑)
            saveResultMap = saveOrgAndUser(ddUser,org);
        }else{

           Example userExample = new Example(AppUser.class);
           userExample.and().andEqualTo("userName",ddUser.getName());
           userExample.and().andEqualTo("appAuthOrgId",appAuthOrg.get(0).getId());
           List<AppUser> appUserlist = appUserMapper.selectByExample(userExample);
           if(appUserlist.size() == 0){
               AppUser newAppUser = saveUser(ddUser,appAuthOrg.get(0).getId());
               appUserlist.add(newAppUser);
           }
            saveResultMap.put("user",appUserlist.get(0));
            saveResultMap.put("org",appAuthOrg.get(0));
        }

        /*获取部门人员列表*/
        List<DDUser> usersOfDept = getDeptUsersByDeptId(deptId);
        System.out.println("org:");
        System.out.println(org);
        System.out.println("dept");
        System.out.println(dept);
        System.out.println("usersOfDept:");
        System.out.println();
        for(DDUser ddUser1:usersOfDept){
            System.out.println("ddUser1:");
            System.out.println(ddUser1);
        }
        System.out.println();
        resultMap.put("ddOrg",org);
        resultMap.put("ddDept", dept);
        resultMap.put("ddUsersOfDept",usersOfDept);
        resultMap.putAll(saveResultMap);
        //生成钉钉E应用与服务端通信的ticket
        Map claimMap = new HashMap<>();
        claimMap.put("user", JSONObject.toJSONString(ddUser));
        //claimMap.put("user",ddUser);
        String eticket = JWTUtil.createJwt(claimMap,userId+userProfile.getName(),-1);
        resultMap.put("eticket",eticket);
        return ServiceResult.success(resultMap);
    }


    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public Map saveOrgAndUser(DDUser ddUser,DDDept org){
        AppAuthOrg newOrg = saveOrg(org);
        AppUser newUser = saveUser(ddUser,newOrg.getId());
        Map resultMap = new HashMap();
        resultMap.put("org",newOrg);
        resultMap.put("user",newUser);
        return resultMap;
    }


    /**
     * 把企业信息保存入数据库，成功返回企业自定义id
     * @param ddDept
     * @return
     */
    public AppAuthOrg saveOrg(DDDept ddDept){
        AppAuthOrg newAppAuthOrg = new AppAuthOrg();
        String newOrgId = idWorker.nextId()+"";
        newAppAuthOrg.setId(newOrgId);
        newAppAuthOrg.setDdOrgCorpId(Constant.CORP_ID);
        newAppAuthOrg.setDdOrgAgentId(Constant.AGENTID+"");
        newAppAuthOrg.setAuthDd(1);
        newAppAuthOrg.setOrgName(ddDept.getName());
        newAppAuthOrg.setLastLoginTime(TimeUtil.getNowDateTime());
        try{
            appOrgMapper.insert(newAppAuthOrg);
            return newAppAuthOrg;
        }catch (Exception e){
            throw new RuntimeException("企业数据插入数据库是出错");
        }
    }


    /**
     * 把人员信息存入数据库
     * @param user
     * @param orgId
     * @return
     */
    public AppUser saveUser(DDUser user,String orgId){
        AppUser appUser = new AppUser();
        String newUserId = idWorker.nextId()+"";
        appUser.setId(newUserId);
        appUser.setUserName(user.getName());
        appUser.setMobile(user.getMobile());
        appUser.setEmail(user.getEmail());
        appUser.setAppAuthOrgId(orgId);
        appUser.setDdUserId(user.getUserid());
        try{
            appUserMapper.insert(appUser);
        }catch (Exception e){
            throw new RuntimeException("人员数据插入数据库是出错");
        }
        return appUser;
    }


    /**
     * 根据临时授权码获取userid
     */
    public String getUserIdByAuthCode(String accessToken,String authCode){
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(authCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
            throw new RuntimeException("服务器通讯异常，请稍后再试");
        }
        //3.查询得到当前用户的userId
        // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
        return response.getUserid();
    }



    /**
     * 获取用户详情
     *
     * @param accessToken
     * @param userId
     * @return
     */
    private OapiUserGetResponse getUserProfile(String accessToken, String userId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_USER_GET);
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);

            return response;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据部门id获取该部门下的人员列表
     * @param deptId
     * @return
     */
    public List<DDUser> getDeptUsersByDeptId(Long deptId){
        logger.info("去钉钉服务器获取部门人员信息！");
        List<DDUser> ddUsers = new ArrayList();
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
            OapiUserListbypageRequest request = new OapiUserListbypageRequest();
            request.setDepartmentId(deptId);
            request.setOffset(0L);
            request.setSize(10L);
            request.setHttpMethod("GET");
            OapiUserListbypageResponse response = client.execute(request, tokenService.getToken("token"));
            if(response == null){
                throw new RuntimeException("获取部门人员列表时出错");
            }
            List<OapiUserListbypageResponse.Userlist> userlist = response.getUserlist();

            for(OapiUserListbypageResponse.Userlist user:userlist){
                ddUsers.add(DDEntityChangeUtil.changeUserlistToDDUser(user));
            }
            return ddUsers;
        } catch (ApiException e) {
            e.printStackTrace();
            throw new RuntimeException("获取部门人员列表时出错");
        }
    }


    /**
     * 根据部门id获取部门详情
     * @param deptId
     * @return
     */
    public DDDept getDeptDetail(Long deptId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(deptId+"");
        request.setHttpMethod("GET");
        try {
            OapiDepartmentGetResponse response = client.execute(request, tokenService.getToken("token"));
            DDDept ddDept = JSONObject.parseObject(JSONObject.toJSONString(response), DDDept.class);
            return ddDept;
        } catch (ApiException e) {
            e.printStackTrace();
            throw new RuntimeException("获取部门详情时出错");
        }
    }

    /**
     * 根据公司id查询公司对象信息
     * @param orgId
     * @return
     */
    public AppAuthOrg AppAuthOrgByOrgId(String orgId) {
        if (orgId==null||orgId==""){
            return null;
        }
        AppAuthOrg appAuthOrg= new AppAuthOrg();
        appAuthOrg.setId(orgId);
        appAuthOrg = appOrgMapper.selectOne(appAuthOrg);
        return appAuthOrg;
    }

    //测试使用
    public AppUser one(String id) {
        AppUser appUser = new AppUser();
        appUser.setId(id);
        appUser=appUserMapper.selectOne(appUser);
        return appUser;
    }


}
