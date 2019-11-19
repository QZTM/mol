package com.mol.ddmanage.Controller.DepartmentManagement;

import com.mol.ddmanage.Ben.DepartmentManagement.AddJurisdictionben;
import com.mol.ddmanage.Service.DepartmentManagement.JurisdictionManagementEditService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/jurisdictionManagementEditController")
public class jurisdictionManagementEditController
{
    @Resource
    JurisdictionManagementEditService jurisdictionManagementEditService;
    @RequestMapping("/GetJurisdictionPosition")//获取角色原有权限
    public AddJurisdictionben GetJurisdictionPosition(@RequestParam String jurisdictionId)
    {
       return jurisdictionManagementEditService.GetJurisdictionPositionLogic(jurisdictionId);
    }
    @RequestMapping("/UpdateJurisdiction")//更新角色权限
    public Map UpdateJurisdiction(@RequestBody AddJurisdictionben json)
    {
       return jurisdictionManagementEditService.UpdateJurisdictionLogic(json);
    }

}
