package com.mol.ddmanage.Controller.DepartmentManagement;

import com.mol.ddmanage.Ben.DepartmentManagement.AddJurisdictionben;
import com.mol.ddmanage.Service.DepartmentManagement.AddJurisdictionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/AddJurisdictionController")//添加角色权限
public class AddJurisdictionController
{
    @Resource
    AddJurisdictionService addJurisdictionService;
    @RequestMapping("/AddJurisdiction")
    public Map AddJurisdiction(@RequestBody AddJurisdictionben json, HttpServletRequest httpServletRequest)
    {
        Map map=new  HashMap();
        map.put("status",addJurisdictionService.AddJurisdictionLogic(json,httpServletRequest.getSession()));
        return map;
    }
}
