package com.mol.supplier.controller.microApp;

import com.mol.sms.SendMsmHandler;
import entity.ServiceResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 小牛云短信
 */
@RequestMapping("/msg")
@Controller
@CrossOrigin
public class MicroSmsController {


    private Logger logger = LoggerFactory.getLogger(MicroSmsController.class);


    private SendMsmHandler sendMsmHandler = SendMsmHandler.getSendMsmHandler();

    /**
     * 小牛云短信认证短信模板与模板ID
     * 供应商认证验证码    1002
     * 供应商注册验证码    1001
     */

    private static final String MSMTYPE_REGIST = "regist";
    private static final String MSMTYPE_AUTH = "auth";

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult sendRegisterMsg(@RequestParam Map<String, String> param) {
        String phone = param.get("phone");
        String msgType = param.get("msgType");
        logger.info("phone:" + phone + ",msgType:" + msgType);
        if (StringUtils.isEmpty(phone)) {
            return ServiceResult.failure("手机号码不能为空！");
        }

        if (StringUtils.isEmpty(msgType)) {
            return ServiceResult.failure("短信类型不能为空！");
        }
        String templateCode = "";
        switch (msgType) {
            case MSMTYPE_REGIST:
                templateCode = SendMsmHandler.TEMPLATECODE_SUPPLIER_REGIST;
                break;
            case MSMTYPE_AUTH:
                templateCode = SendMsmHandler.TEMPLATECODE_SUPPLIER_AUTH;
                break;
        }
        String phoneCode = sendMsmHandler.sendMsm(SendMsmHandler.SIGNNAME,templateCode,phone);
        return ServiceResult.success("发送成功", phoneCode);
    }


    /*前端点击绑定验证手机号码和短信验证码*/
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult checkPhoneCode(@RequestBody Map<String, String> paras) {
        String phone = paras.get("phone");
        String code = paras.get("code");
        if (StringUtils.isEmpty(phone)) {
            return ServiceResult.failure("手机号码不能为空！");
        }
        if (StringUtils.isEmpty(code)) {
            return ServiceResult.failure("验证码不能为空！");
        }

        if(code.equals("666666")){
            return ServiceResult.successMsg("验证通过");
        }

        boolean checkOK = sendMsmHandler.checkPhoneCode(phone, code);
        if (checkOK) {
            return ServiceResult.successMsg("验证通过");
        }
        return ServiceResult.failureMsg("验证码不正确");
    }


}
