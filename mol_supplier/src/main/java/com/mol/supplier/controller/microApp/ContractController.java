package com.mol.supplier.controller.microApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 合同管理控制器
 */
@Controller
@RequestMapping("/contract")
public class ContractController {

    @RequestMapping("/index")
    public String showContractIndex(){
        return "contract_index";
    }

}
