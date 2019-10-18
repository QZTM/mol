package com.mol.ddmanage.Controller.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.ExpertInforPageListben;
import com.mol.ddmanage.Service.ExperManagement.ExpertInforPageListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/ExperManagement/ExpertInforPageList")
public class ExpertInforPageListController
{

    @Resource
    ExpertInforPageListService expertInforPageListService;
    @RequestMapping("/ShowList")
     public ArrayList<ExpertInforPageListben> ShowList(@RequestParam String expert_grade)//专家管理列表展示
     {
         return expertInforPageListService.ExpertInforPageListShow(expert_grade);
     }
}
