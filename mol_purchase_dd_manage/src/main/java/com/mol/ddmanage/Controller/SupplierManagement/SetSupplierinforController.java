package com.mol.ddmanage.Controller.SupplierManagement;

import com.mol.ddmanage.Ben.SupplierManagement.SetSupplierinforben;
import com.mol.ddmanage.Service.SupplierManagement.SetSupplierinforService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/SetSupplierinfor")
public class SetSupplierinforController
{
    @Resource
    SetSupplierinforService setSupplierinforService;
   @RequestMapping("/LandingSupplierinfor")
    public SetSupplierinforben LandingSupplierinfor(@RequestParam String pk_supplier)//加载供应商的详细信息
   {
       return setSupplierinforService.LandingSupplierinforLogic(pk_supplier);
   }
}
