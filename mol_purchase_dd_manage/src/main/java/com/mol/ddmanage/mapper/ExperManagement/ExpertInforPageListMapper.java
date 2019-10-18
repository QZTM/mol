package com.mol.ddmanage.mapper.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.ExpertInforPageListben;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface ExpertInforPageListMapper
{
    ArrayList<ExpertInforPageListben> ExpertInforPageListShow(@Param(value = "expert_grade") String expert_grade);//参数专家等级
}
