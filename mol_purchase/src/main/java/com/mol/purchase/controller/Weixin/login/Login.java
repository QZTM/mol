package com.mol.purchase.controller.Weixin.login;

import com.mol.purchase.service.Weixin.login.Login_service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping("WeixinLogin")
@RestController
public class Login {
    @Resource
    Login_service login_service;
    @RequestMapping("login")//微信登陆
    public String login(@RequestParam Map map)//获取免登票据
    {
        return login_service.Get_openid(map);
    }
}
