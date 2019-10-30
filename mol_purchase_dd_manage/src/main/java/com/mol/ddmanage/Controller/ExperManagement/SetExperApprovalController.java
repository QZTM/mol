package com.mol.ddmanage.Controller.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.SetExperApprovalben;
import com.mol.ddmanage.Service.ExperManagement.SetExperApprovalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ExperManagement/SetExperApproval")
public class SetExperApprovalController
{
     @Resource
    SetExperApprovalService setExperApprovalService;
     @RequestMapping("/setExperApprovalben")//拿出专家详细信息
     public SetExperApprovalben setExperApprovalben(@RequestParam String id)
     {
         return setExperApprovalService.SetExperApprovalInfor(id);
     }

     @RequestMapping("/PassOrReject")//专家审核通过或者拒绝
    public Map PassOrReject(@RequestParam String id, String authentication)//认证  authentication  0=未认证 1 =审核中 2=认证成功 3=认证失败
     {
         Map map=new HashMap();
         map.put("status", setExperApprovalService.PassOrRejectLogic(id,authentication));
         return map;

     }
}
