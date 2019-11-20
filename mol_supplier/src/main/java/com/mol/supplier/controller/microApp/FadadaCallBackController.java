package com.mol.supplier.controller.microApp;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Log
@Controller
@CrossOrigin
@RequestMapping("/fddCallback")
public class FadadaCallBackController {

    @RequestMapping(value = "/personAuth")
    @ResponseBody
    public void personRegistCallback(@RequestParam Map paraMap){
        log.info("法大大个人认证回调事件：，，返回值："+paraMap.toString());
    }

    @RequestMapping(value = "/personAuthTo")
    public String personRegistTo(@RequestParam Map paraMap){
        log.info("法大大个人认证return url,,,"+paraMap.toString());
        return "/index/findAll";
    }

    @RequestMapping(value = "/orgAuth")
    @ResponseBody
    public void fddCallback(@RequestParam Map paraMap){
        log.info("法大大企业认证回调事件：，，返回值："+paraMap.toString());
    }

    @RequestMapping(value = "/orgAuthTo")
    public String fddAuthCallBack(@RequestParam Map paraMap){
        log.info("法大大认证return url,,,"+paraMap.toString());
        return "/index/findAll";
    }



}
