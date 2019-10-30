package com.mol.supplier.controller.microApp;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.mol.cache.CacheHandle;
import com.mol.supplier.config.MicroAttr;
import com.mol.supplier.entity.MicroApp.DDDept;
import com.mol.supplier.entity.MicroApp.DDUser;
import com.mol.supplier.entity.MicroApp.Salesman;
import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.exception.microApp.OApiException;
import com.mol.supplier.service.microApp.*;
import entity.ServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;
import util.RandomStr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/microApp/login")
public class MicroDDLoginController {

    private Integer getDDUserIdCounts = 5;

    private Integer tryCounts = 0;

    private Logger logger = LoggerFactory.getLogger(MicroDDLoginController.class);

    @Autowired
    private MicroGetDDUserInfoService microGetDDUserInfoService;

    @Autowired
    private MicroSupplierService microSupplierService;

    @Autowired
    private MicroSalesmanService microSalesmanService;

    @Autowired
    private MicroJsapiTicketService microJsapiTicketService;

    @Autowired
    private MicroTokenService microTokenService;

    @Autowired
    private CacheHandle cacheHandle;


    /**
     * 显示登陆/注册页面
     *
     * @param registType 注册类型，1为企业和业务员，2为添加业务员
     * @return
     */
    @RequestMapping("/show")
    public String login(String registType, Model model) {
        model.addAttribute("registType", registType);
        return "login";
    }

    /**
     * 显示普通供应商注册协议
     * @return
     */
    @RequestMapping("/showProtocolPage")
    public String showProtocolPage(){
        return "meygyhzcxy";
    }

    /**
     * 显示隐私协议
     * @return
     */
    @RequestMapping("/showprivacyagreePage")
    public String showPrivacyAgreePage(){
        return "privacyagreeprotocol";
    }


    @RequestMapping("/show1")
    public String login1(String registType, Model model) {
        model.addAttribute("registType", registType);
        return "login.1";
    }


    /**
     * 根据免登授权码和access_token获取用户信息
     * 往session中存放dduser,ddorg,dddept        supplier    salesman信息
     * 首先获取dduser，ddorg,dddept信息，根据人员的姓名和手机号码去供应商业务员表里面查询，如果查询到了记录，则直接封装 supplier  和   salesman
     * 如果没有查询到记录，那么根据ddorg的姓名去供应商表里面查询是否有记录，如果有记录，那么把该业务员绑定到该供应商下面，如果没有记录那么注册一个新的供应商，然后绑定业务员
     * <p>
     * 当前登录人员的状态可以分为以下几种：
     * <p>
     * 1.
     *
     * @param code
     * @return
     */
    @RequestMapping("/initUserInfo")
    @ResponseBody
    public ServiceResult getSticket(String code, HttpSession session, HttpServletRequest request) {
        logger.info("进入initUserInfo方法");
        /*获取钉钉用户id*/
        String userId = "";
        try{
             userId = microGetDDUserInfoService.getDDUserId(code);
        }catch (Exception e){
            logger.info(e.getMessage());
            tryCounts++;
            if(!(tryCounts>=getDDUserIdCounts)){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                getSticket(code,session,request);
            }else{
                logger.info("尝试"+tryCounts+"次依然没获取到用户的钉钉id");
                tryCounts = 0;
                return ServiceResult.failure("未获取到用户钉钉id");
            }


        }
        logger.info("getDDUserId()....return : userId:" + userId);

        /*获取钉钉用户详情：*/
        OapiUserGetResponse response = microGetDDUserInfoService.getUserDetail(userId);
        if (response == null) {
            throw new RuntimeException("获取用户信息失败");
        }
        DDUser ddUser = JSONObject.parseObject(JSONObject.toJSONString(response), DDUser.class);
        logger.info("获取到的用户详情：");
        logger.info(ddUser.toString());
        if (ddUser.getMobile() == null) {
            throw new RuntimeException("未获取到手机号码，请绑定手机号或开启通讯录权限后重试");
        }


        /*获取部门信息*/
        OapiDepartmentGetResponse departmentGetResponse = microGetDDUserInfoService.getDeptInfo(ddUser.getDepartment().get(0));
        if (departmentGetResponse == null) {
            throw new RuntimeException("获取用户部门信息失败");
        }

        OapiDepartmentGetResponse orgGetResponse = microGetDDUserInfoService.getOrgInfo(-99L);
        if (orgGetResponse == null) {
            throw new RuntimeException("获取组织信息失败");
        }
        DDDept ddDept = JSONObject.parseObject(JSONObject.toJSONString(departmentGetResponse), DDDept.class);
        logger.info("获取到的部门信息：");
        logger.info(ddDept.toString());

        /*获取组织信息*/
        DDDept org = JSONObject.parseObject(JSONObject.toJSONString(orgGetResponse), DDDept.class);
        logger.info("获取到的企业信息：");
        logger.info(org.toString());

        session.setAttribute("ddUser", ddUser);
        session.setAttribute("ddDept", ddDept);
        session.setAttribute("ddOrg", org);


        /**
         * 上面已经根据前端发过来的免登授权码获取了用户钉钉方面的user,dept,org信息，下面根据user的用户名、手机号，org的企业姓名获取该用户的注册情况：
         * 1.根据用户的用户名和手机号码去供应商业务员表里面查询，如果有记录，那么封装user 和org入session,返回success
         * 2.如果数据表中没有该信息，那么根据企业名称查询供应商表里面是否有对应信息，如果有企业的信息，那么封装org入session，返回用户未注册
         * 3.如果数据表中没有企业的信息，那么返回用户未注册，企业未注册，返回
         *
         */

        Supplier getdSupplier = null;
        Salesman salesman = null;

        /*根据用户名和手机号码验证业务员表中是否有该业务员*/
        Example example1 = new Example(Salesman.class);
        example1.and().andEqualTo("name", ddUser.getName()).andEqualTo("phone", ddUser.getMobile());
        salesman = microSalesmanService.getSalesman(example1);
        logger.info("salesman == null?---" + (salesman == null));

        if (salesman != null) {

            Example example = new Example(Supplier.class);
            example.and().andEqualTo("pkSupplier", salesman.getPkSupplier());
            getdSupplier = microSupplierService.getSupplierByExample(example);


            if (getdSupplier == null) {
                logger.info("不正常情况，企业未注册，用户已注册");
                return ServiceResult.failure("企业未注册，用户已注册");
            }

            session.setAttribute("user", salesman);
            session.setAttribute("supplier", getdSupplier);
            Integer getedVersion = getdSupplier.getVersion();
            Integer cacheVersion = 0;
            String supplierVersionStr = cacheHandle.getStr(getdSupplier.getPkSupplier()+"version");
            if(StringUtils.isEmpty(supplierVersionStr) || Integer.valueOf(supplierVersionStr)<getedVersion){
                cacheHandle.saveStr(getdSupplier.getPkSupplier()+"version",24*60*60,getedVersion+"");
            }
            Map map = new HashMap();
            map.put("user",salesman);
            map.put("supplier",getdSupplier);
            Map configValue = getConfig(request);
            map.putAll(configValue);


            return ServiceResult.success("初始化完成",map);


        } else {
            logger.info("企业未注册，用户未注册");
            return ServiceResult.failureMsg("企业未注册，用户未注册");
        }

    }



    /**
     * 计算当前请求的jsapi的签名数据<br/>
     * <p>
     * 如果签名数据是通过ajax异步请求的话，签名计算中的url必须是给用户展示页面的url
     *
     * @param request
     * @return
     */
    public Map getConfig(HttpServletRequest request) {
//        String urlString = request.getRequestURL().toString();
//        String queryString = request.getQueryString();
//
//        String queryStringEncode = null;
//        String url;
//        if (queryString != null) {
//            queryStringEncode = URLDecoder.decode(queryString);
//            url = urlString + "?" + queryStringEncode;
//        } else {
//            url = urlString;
//        }
        String url = "http://fyycg88.vaiwan.com/index/findAll";

        /**
         * 确认url与配置的应用首页地址一致
         */
        //System.out.println(url);

        /**
         * 随机字符串
         */
        String nonceStr = RandomStr.getRandom(7, RandomStr.TYPE.LETTER);
        long timeStamp = System.currentTimeMillis() / 1000;
        String signedUrl = url;
        //String accessToken = microTokenService.getToken();;
        String ticket = microJsapiTicketService.getJsApiTicket();;
        String signature = null;

        try {
            signature = sign(ticket, nonceStr, timeStamp, signedUrl);
        } catch (OApiException e) {
            e.printStackTrace();
        }

        Map<String, Object> configValue = new HashMap<>();
        configValue.put("jsticket", ticket);
        configValue.put("signature", signature);
        configValue.put("nonceStr", nonceStr);
        configValue.put("timeStamp", timeStamp);
        configValue.put("corpId", MicroAttr.CROPID);
        configValue.put("agentId", MicroAttr.AGENTID);
        //String config = JSON.toJSONString(configValue);
        return configValue;
    }



    public String sign(String ticket, String nonceStr, long timeStamp, String url) throws OApiException {
        try {
            return DingTalkJsApiSingnature.getJsApiSingnature(url, nonceStr, timeStamp, ticket);
        } catch (Exception ex) {
            throw new OApiException();
        }
    }




    private String check(String url,String corpId,String suiteKey) throws Exception{
        try {

            url = URLDecoder.decode(url,"UTF-8");
            URL urler = new URL(url);
            StringBuffer urlBuffer = new StringBuffer();
            urlBuffer.append(urler.getProtocol());
            urlBuffer.append(":");
            if (urler.getAuthority() != null && urler.getAuthority().length() > 0) {
                urlBuffer.append("//");
                urlBuffer.append(urler.getAuthority());
            }
            if (urler.getPath() != null) {
                urlBuffer.append(urler.getPath());
            }
            if (urler.getQuery() != null) {
                urlBuffer.append('?');
                urlBuffer.append(URLDecoder.decode(urler.getQuery(), "utf-8"));
            }
            url = urlBuffer.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("url非法");
        }
        return url;
    }





}
