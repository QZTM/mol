package com.mol.ddmanage.Controller;

import com.mol.ddmanage.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/Login")
public class Login
{
    @Autowired
    LoginService loginService;
    @RequestMapping("/verification_login")
    public String  user_login(@RequestParam Map map, HttpSession session)
    {
       if (loginService.LoginService_dingding(map,session)!=false)
       {
           return "New_file";
       }
       else
           {
               return "New_file";
       }

    }


}
