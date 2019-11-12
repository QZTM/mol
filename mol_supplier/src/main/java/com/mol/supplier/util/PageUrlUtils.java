package com.mol.supplier.util;

import javax.servlet.http.HttpServletRequest;

public class PageUrlUtils {


    /**
     * 当前链接使用的协议 + "://" + 服务器地址 + ":" +端口号 + 应用名称，如果应用名称
     * @param request
     * @return
     */
    public static String getPageUrl(HttpServletRequest request){
        String domain = request.getScheme()
                + "://" + request.getServerName()
                + request.getRequestURI();
        return domain;
    }

}
