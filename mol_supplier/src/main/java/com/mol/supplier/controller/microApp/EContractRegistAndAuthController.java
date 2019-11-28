package com.mol.supplier.controller.microApp;

import com.mol.fadada.handler.RegistAndAuthHandler;
import com.mol.fadada.pojo.RegistRecord;
import com.mol.supplier.config.Constant;
import com.mol.supplier.entity.MicroApp.Salesman;
import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.service.microApp.MicroUserService;
import entity.ServiceResult;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 电子合同平台注册及认证
 */
@Controller
@RequestMapping("/econtract")
@Log
public class EContractRegistAndAuthController {

    @Autowired
    private MicroUserService microUserService;

    @RequestMapping("/checkRegist")
    @ResponseBody
    public ServiceResult checkRegist(HttpSession session, @RequestParam String registType) throws InterruptedException {
        Supplier supplier = microUserService.getSupplierFromSession(session);
        Salesman salesman = microUserService.getUserFromSession(session);
        //判断该单位是否注册了：
        RegistRecord rr = new RegistRecord();
        ServiceResult registResult = RegistAndAuthHandler.checkIfRegisted(supplier.getPkSupplier(), registType);
        if (!registResult.isSuccess()) {
            return ServiceResult.failure("没有注册");
        } else {
            rr = (RegistRecord)registResult.getResult();
            return ServiceResult.success(rr.getCustomerId());
        }
    }


    /**
     * 企业注册
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult toRegistOrg(String registType, HttpSession session) {
        Supplier supplier = microUserService.getSupplierFromSession(session);
        Salesman salesman = microUserService.getUserFromSession(session);
        return RegistAndAuthHandler.regAccount(supplier.getPkSupplier(), registType);
    }


    @RequestMapping("/checkAuth")
    @ResponseBody
    public ServiceResult checkAuth(HttpSession session, @RequestParam String customerId, String authType) throws InterruptedException {
        Supplier supplier = microUserService.getSupplierFromSession(session);
        Salesman salesman = microUserService.getUserFromSession(session);
        //验证是否认证了：
        ServiceResult authResult = RegistAndAuthHandler.checkIfAuthedByCustomerId(customerId, authType);
        if (!authResult.isSuccess()) {
            return ServiceResult.failure("30000","没有认证",customerId);
        } else {
            return ServiceResult.success(authResult);
        }
    }


    @RequestMapping(value = "/getAuthUrl", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult getAuthUrl(String customerId, String authType) {
        log.info("getAuthUrl:...customerId:"+customerId+",authType:"+authType);
        if ("1".equals(authType)) {
            return RegistAndAuthHandler.getAuthPersonurl(customerId, "", null);
        } else if ("2".equals(authType)) {
            ServiceResult sr = RegistAndAuthHandler.getAuthCompanyurl(customerId, Constant.domain + "/fddCallback/orgAuth", Constant.domain + "/fddCallback/orgAuthTo");
            if(sr.isSuccess()) {
                Map resultMap = (Map)sr.getResult();
                return ServiceResult.success(resultMap.get("url"));
            }else {
                return ServiceResult.failure("获取失败");
            }
        }
        return null;
    }

    @RequestMapping("/showAuthSuccess")
    public String showAuthSuccessPage(){
        return "fadada_auth_success";
    }
}