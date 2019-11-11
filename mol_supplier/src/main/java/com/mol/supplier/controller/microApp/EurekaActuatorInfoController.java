package com.mol.supplier.controller.microApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/actuator")
public class EurekaActuatorInfoController {

    @RequestMapping("/info")
    public String actuatorInfo(){
        return "actuator";
    }

}
