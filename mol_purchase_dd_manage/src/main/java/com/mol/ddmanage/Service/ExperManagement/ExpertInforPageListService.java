package com.mol.ddmanage.Service.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.ExpertInforPageListben;
import com.mol.ddmanage.mapper.ExperManagement.ExpertInforPageListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class ExpertInforPageListService
{
   @Resource
    ExpertInforPageListMapper expertInforPageListMapper;
   public ArrayList<ExpertInforPageListben> ExpertInforPageListShow(String expert_grade)
   {
       ArrayList<ExpertInforPageListben>expertInforPageListbens=expertInforPageListMapper.ExpertInforPageListShow(expert_grade);
       for (int n=0;n<expertInforPageListbens.size();n++)
       {
           expertInforPageListbens.get(n).setNumber(String.valueOf(n));
           if (expertInforPageListbens.get(n).getExpert_grade()!=null && expertInforPageListbens.get(n).getExpert_grade().equals(0))
           {
               expertInforPageListbens.get(n).setExpert_grade("普通专家");
           }
           else if (expertInforPageListbens.get(n).getExpert_grade()!=null && expertInforPageListbens.get(n).getExpert_grade().equals(1))
           {
               expertInforPageListbens.get(n).setExpert_grade("金牌专家");
           }
           else
           {
               expertInforPageListbens.get(n).setExpert_grade("暂无等级");
           }
       }
       return expertInforPageListbens;
   }

}
