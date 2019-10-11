package entity;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 包装返回结果
 */
public final class MsgBuilder {

    /**
     * 包装正常返回的数据
     * @param obj
     * @return
     */
    public static <T> Map<String, Object> buildReturnMessage(T obj){
        Map<String, Object> result = new HashMap<>();
        result.put("retCode", 0);
        result.put("message", obj);
        return result;
    }

    /**
     * 返回异常提示信息
     * @param msg 异常信息
     * @return
     */
    public static Map<String, Object> buildReturnErrorMessage(String msg){
        Map<String, Object> result = new HashMap<>();
        result.put("retCode", 1);
        result.put("message", msg);
        return result;
    }

    /**
     * 复杂返回数据结构包装方法
     * @param status
     * @param retInfo
     * @param client
     * @return
     */
    public static JSONObject buildMessageConent(int status, JSONObject retInfo, String... client){
        JSONObject j = new JSONObject();
        j.put("type","message");
        JSONObject message_content = new JSONObject();
        message_content.put("retCode",status);
        JSONObject message = new JSONObject();
        message_content.put("retInfo",retInfo);
        message.put("message_content",message_content);

        message.put("to","web_1");
        message.put("from","web_1");
        j.put("message",message);
        j.put("model","jq");

        if(client!=null && client.length >=1){
            j.put("model",client[0]);
            if(client.length >= 3) {
                message.put("to", client[1]);
                message.put("from", client[2]);
            }
        }
        return j;
    }


}
