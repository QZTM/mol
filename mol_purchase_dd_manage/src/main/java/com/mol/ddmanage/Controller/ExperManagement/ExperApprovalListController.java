package com.mol.ddmanage.Controller.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.ExpertInforPageListben;
import com.mol.ddmanage.Service.ExperManagement.ExpertInforPageListService;
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
   ExpertInforPageListService expertInforPageListService;
   @RequestMapping("/ShowList")
   public ArrayList<ExpertInforPageListben> ShowList(@RequestParam String authentication)//专家审核列表
   {
       return expertInforPageListService.ExpertInforPageListShow(authentication);
   }
}
