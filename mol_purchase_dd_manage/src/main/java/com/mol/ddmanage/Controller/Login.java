package com.mol.ddmanage.Controller;

import com.mol.ddmanage.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String  user_login(@RequestParam Map map, HttpSession session, Model model)
    {
        Map map1=loginService.LoginService_dingding(map,session);
       if ((Boolean) map1.get("rest")!=false)
       {
           model.addAttribute("name",map1.get("name").toString());//登录人的名子
           return "New_file";
       }
       else
           {
               return "New_file";
       }

    }


}
