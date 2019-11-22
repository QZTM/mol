package com.mol.expert.interceptor;

import com.mol.expert.entity.expert.ExpertUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SessionExpertInterceptor implements HandlerInterceptor {

    private static final Logger logger= LoggerFactory.getLogger(SessionExpertInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        logger.info("访问专家端的请求路径"+servletPath);

        response.setHeader("Access-Control-Allow-Origin", "*");

        HttpSession session = request.getSession();
        ExpertUser user = (ExpertUser) session.getAttribute("user");
        logger.info("获取session中的专家信息："+user);

        if (user==null){
            logger.info("session中没有专家的信息");
            //重定向到首页
            response.sendRedirect("http://"+request.getServerName()+"/index/findAll");
            return false;
        }
        return true;
    }
}
