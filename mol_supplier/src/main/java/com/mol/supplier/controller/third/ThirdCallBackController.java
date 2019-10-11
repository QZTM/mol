package com.mol.supplier.controller.third;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.dingtalk.oapi.lib.aes.Utils;
import com.mol.supplier.config.Constant;
import com.mol.supplier.service.third.callbackEventServices.*;
import com.mol.supplier.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 第三方微应用回调事件控制器
 */
@RestController
@CrossOrigin
public class ThirdCallBackController {

    private static final Logger bizLogger = LoggerFactory.getLogger(ThirdCallBackController.class);

    private BaseEventService eventService = null;

    /**
     * 授权开通事件
     */
    private static final String EVENTTYPE_TMP_AUTH_CODE="tmp_auth_code";

    /**
     * 解除授权事件
     */
    private static final String EVENTTYPE_SUITE_RELIEVE="suite_relieve";

    /**
     * 通讯录授权范围变更事件
     */
    private static final String EVENTTYPE_CHANGE_AUTH="change_auth";

    /**
     * 推送suite_ticket事件
     */
    private static final String EVENTTYPE_SUITE_TICKET="suite_ticket";

    /**
     * 逻辑启用应用
     */
    private static final String EVENTTYPE_APP_RESTORE="org_micro_app_restore";

    /**
     * 逻辑停用应用
     */
    private static final String TMP_APP_STOP="org_micro_app_stop";

    /**
     * 用户购买下单事件
     */
    private static final String TMP_MARKET_BUY="market_buy";


    /**
     * 相应钉钉回调时的值
     */
    private static final String CALLBACK_RESPONSE_SUCCESS = "success";


    @RequestMapping(value = "/callbackto", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> callback(@RequestParam(value = "signature", required = false) String signature,
                                        @RequestParam(value = "timestamp", required = false) String timestamp,
                                        @RequestParam(value = "nonce", required = false) String nonce,
                                        @RequestBody(required = false) JSONObject json) {
        try {
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(Constant.THIRD_TOKEN, Constant.THIRD_ENCODING_AES_KEY, Constant.THIRD_SUITEKEY);
            //从post请求的body中获取回调信息的加密数据进行解密处理
            String encryptMsg = json.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
            JSONObject obj = JSON.parseObject(plainText);

            //根据回调数据类型做不同的业务处理
            String eventType = obj.getString("EventType");
            //todo: 根据不同的回调时间类型调用不同的业务类
            switch(eventType){
                case EVENTTYPE_TMP_AUTH_CODE:
                    bizLogger.info("授权开通事件");
                    eventService = SpringContextUtil.getBean(TmpAuthCodeService.class);
                    break;
                case EVENTTYPE_SUITE_RELIEVE:
                    bizLogger.info("解除授权事件");
                    eventService = SpringContextUtil.getBean(SuiteRelieveService.class);
                    break;
                case EVENTTYPE_CHANGE_AUTH:
                    bizLogger.info("通讯录授权范围变更事件");
                    eventService = SpringContextUtil.getBean(ChangeAuthService.class);
                    break;
                case EVENTTYPE_SUITE_TICKET:
                    bizLogger.info("推送suite_ticket事件");
                    eventService = SpringContextUtil.getBean(SuiteTicketService.class);
                    break;
                case EVENTTYPE_APP_RESTORE:
                    bizLogger.info("逻辑启用应用");
                    eventService = SpringContextUtil.getBean(AppRestoreService.class);
                    break;
                case TMP_APP_STOP:
                    bizLogger.info("逻辑停用应用");
                    eventService = SpringContextUtil.getBean(AppStopService.class);
                    break;
                case TMP_MARKET_BUY:
                    bizLogger.info("用户购买下单事件");
                    eventService = SpringContextUtil.getBean(MarketBuyService.class);
                    break;
                default :
                    bizLogger.info("默认事件");
                    return dingTalkEncryptor.getEncryptedMap(CALLBACK_RESPONSE_SUCCESS, System.currentTimeMillis(), Utils.getRandomStr(8));
            }

            eventService.action(obj);

            // 返回success的加密信息表示回调处理成功
            return dingTalkEncryptor.getEncryptedMap(CALLBACK_RESPONSE_SUCCESS, System.currentTimeMillis(), Utils.getRandomStr(8));
        } catch (Exception e) {
            //失败的情况，应用的开发者应该通过告警感知，并干预修复
            throw new RuntimeException("回调事件发生异常，"+e.getMessage());
        }

    }
}
