package com.mol.expert.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析request中的user-agent的工具类
 */
public class ParseUserAgentUtil {

    /*判断是不是钉钉终端打开的*/
    public boolean isDD(HttpServletRequest request) {
        String userAge = request.getHeader("User-Agent").toLowerCase();
        if (userAge.contains("dingtalk")) {
            return true;
        }
        return false;
    }


}
