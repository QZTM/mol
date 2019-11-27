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
    @RequestMapping("/Upload_Contract")
    public Map Upload_Contract(@RequestParam("file") MultipartFile file ,@RequestParam Map map)
    {
        return electronicContractSigninginforService.Upload_Contract_Logic(file,map);
    }
}
