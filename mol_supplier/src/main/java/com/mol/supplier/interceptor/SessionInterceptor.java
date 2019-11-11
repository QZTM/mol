package com.mol.supplier.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器，验证session中是否有supplier,没有的拦截
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器访问方法："+request.getServletPath());
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");

        HttpSession session = request.getSession();
        Object supplierObj = session.getAttribute("supplier");
        System.out.println("session中是否有supplier信息：supplierObj:"+(supplierObj == null));
        if(supplierObj == null){
            System.out.println("session中没有supplier,重定向中：需要重定向的链接：：：：http://"+request.getServerName()+"/index/findAll");
            response.sendRedirect("http://"+request.getServerName()+"/index/findAll");
            return false;
        }

        return true;
    }
}
