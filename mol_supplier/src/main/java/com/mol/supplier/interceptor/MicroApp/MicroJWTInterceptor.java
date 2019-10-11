package com.mol.supplier.interceptor.MicroApp;

import com.alibaba.fastjson.JSON;
import entity.dd.DDUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class MicroJWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        // response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");

        HttpSession session = request.getSession();
        DDUser user = (DDUser) session.getAttribute("ddUser");

        if(user != null ){
            return  true;
        }
        //后台管理页面产生的token
//        String sticket = request.getHeader("sticket");
//        if(sticket != null && JWTUtil.getUserByRequest(request)!=null){
//            return true;
//        }
//        //判断是否过期
//        Optional.ofNullable(eticket)
//                .map(n -> {
//                    try {
//                        System.out.println("JWTInterceptor....requestUrl:"+request.getRequestURL()+"eticket:"+eticket);
//                         Claims claims = JWTUtil.parseJwt(eticket);
//                        System.out.println("JWT验证通过！");
//                         return claims;
//                    } catch (Exception e) {
//                        System.out.println("JWT验证没有通过");
//                        throw new RuntimeException("token不存在");
//                    }
//                });
        response.sendRedirect("/microApp/regist/show");
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(200);
//        ServiceResult sr = ServiceResult.failure("50001","未获取到供应商权限！");
//        response.getWriter().write(JSON.toJSONString(sr));
        return false;
    }
    }
