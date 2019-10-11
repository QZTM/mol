package com.mol.supplier.controller.third;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ToPage {

    @RequestMapping(value = "/toPage",method = RequestMethod.GET)
    public String topage(HttpServletRequest request){
        String url = request.getParameter("url");
        //url="index_"+url;
        System.out.println(url);
        return url;
    }

}
