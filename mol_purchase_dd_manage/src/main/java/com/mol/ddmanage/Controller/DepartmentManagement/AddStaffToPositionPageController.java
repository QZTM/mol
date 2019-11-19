package com.mol.ddmanage.Controller.DepartmentManagement;

import com.mol.ddmanage.Service.DepartmentManagement.AddStaffToPositionPageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/AddStaffToPositionPageController")
public class AddStaffToPositionPageController
{
    @Resource
    AddStaffToPositionPageService addStaffToPositionPageService;
    @RequestMapping("/AddStaffToPosition")//向角色里添加员工
    public Map AddStaffToPosition(@RequestParam String ids,@RequestParam String jurisdictionId)//该角色的员工id数组，该角色id
    {
        Map map=new HashMap();
        map.put("status",addStaffToPositionPageService.AddStaffToPositionLogic(ids,jurisdictionId));
        return map;
    }

    @RequestMapping("/GetPositionPeolpe")
    public ArrayList<Map> GetPositionPeolpe(@RequestParam String jurisdictionId)//获取权限已有的人员以及id
    {
        return addStaffToPositionPageService.GetPositionPeolpeLogic(jurisdictionId);
    }
}
