package com.mol.ddmanage.Controller.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.ExperApprovalListben;
import com.mol.ddmanage.Service.ExperManagement.ExperApprovalListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/ExperManagement/ExperApprovalList")
public class ExperApprovalListController
{
   @Resource
   ExperApprovalListService experApprovalListService;
   @RequestMapping("/ShowList")
   public ArrayList<ExperApprovalListben> ShowList(@RequestParam String authentication)//专家审核列表
   {
       return experApprovalListService.ShowList(authentication);
   }
}
