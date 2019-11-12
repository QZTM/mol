package com.mol.ddmanage.Hander;

import com.mol.ddmanage.Util.Token_hand;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class intercept implements HandlerInterceptor{

    public intercept(){

    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception //接到请求首先执行此函数体，如果为true再执行contorler
    {
        try {
            HttpSession session=request.getSession();
            if (session.getAttribute("token")!=null)
            {
                if (Token_hand.Review_My_Token(session.getAttribute("token").toString()).equals("errer"))
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                return true;
            }
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
