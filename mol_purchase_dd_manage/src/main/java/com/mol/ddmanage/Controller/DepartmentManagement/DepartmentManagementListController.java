package com.mol.ddmanage.Controller.DepartmentManagement;

import com.mol.ddmanage.Ben.DepartmentManagement.DeparmentManagementben;
import com.mol.ddmanage.Service.DepartmentManagement.DepartmentManagementListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/DepartmentManagementListController")
public class DepartmentManagementListController
{
    @Resource
    DepartmentManagementListService departmentManagementListService;
    @RequestMapping("/GetOrganization")
    public ArrayList<DeparmentManagementben> GetOrganization(@RequestParam String DeparmentId)//获取人员及部门组织架构
    {
       return departmentManagementListService.GetOrganizationService(DeparmentId);
    }
}
