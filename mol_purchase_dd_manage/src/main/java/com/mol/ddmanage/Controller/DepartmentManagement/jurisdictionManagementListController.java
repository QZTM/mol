package com.mol.ddmanage.Controller.DepartmentManagement;

import com.mol.ddmanage.Ben.DepartmentManagement.jurisdictionManagementben;
import com.mol.ddmanage.Service.DepartmentManagement.jurisdictionManagementListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/jurisdictionManagementListController")
public class jurisdictionManagementListController
{
    @Resource
    jurisdictionManagementListService jurisdictionManagement;
    @RequestMapping("/GetPositionList")//获取职位列表
    public ArrayList<jurisdictionManagementben>GetPositionList()
    {
        return jurisdictionManagement.GetPositionListLogic();
    }
    @RequestMapping("/DeleteJurisdiction")//删除此职位
    public Map DeleteJurisdiction(@RequestParam String jurisdictionId)
    {
        return jurisdictionManagement.DeleteJurisdictionLogic(jurisdictionId);
    }
}
