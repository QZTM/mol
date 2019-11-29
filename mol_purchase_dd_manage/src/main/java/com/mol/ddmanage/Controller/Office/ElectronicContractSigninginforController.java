package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Service.Office.ElectronicContractSigninginforService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/ElectronicContractSigninginforController")
public class ElectronicContractSigninginforController
{
    @Resource
    ElectronicContractSigninginforService electronicContractSigninginforService;
    @RequestMapping("/RegisteredAccount")
    public Map RegisteredAccount()//注册法大大账号
    {
        return electronicContractSigninginforService.RegisteredAccount();
    }

/*    @RequestMapping("/CertificationAccount")
    public Map CertificationAccount()//此企业在法大大账号的认证
    {
        return
    }*/

    @RequestMapping("/Upload_Contract")//上传合同
    public Map Upload_Contract(@RequestParam("file") MultipartFile file ,@RequestParam Map map)
    {
        return electronicContractSigninginforService.Upload_Contract_Logic(file,map);
    }

    @RequestMapping("/SetContract")//查看合同
    public Map SetContract(@RequestParam String purchasId,@RequestParam String supplierid)
    {
        return electronicContractSigninginforService.SetContractLogic(purchasId,supplierid);
    }

}
