package com.mol.supplier.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class PageUrlUtils {


    /**
     * 当前链接使用的协议 + "://" + 服务器地址 + ":" +端口号 + 应用名称，如果应用名称
     * @param request
     * @return
     */
    public static String getPageUrl(HttpServletRequest request){
        String queryString = request.getQueryString();
        if(!StringUtils.isEmpty(queryString) || "null".equals(queryString)){
            queryString = "?"+queryString;
        }else{
            queryString = "";
        }
        String domain = request.getScheme()
                + "://" + request.getServerName()
                + request.getRequestURI()
                +queryString;
        return domain;
    }

}
