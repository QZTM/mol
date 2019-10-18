package com.mol.ddmanage.mapper.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.ExperApprovalListben;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface ExperApprovalListMapper
{
    ArrayList<ExperApprovalListben> ExperApprovalListShow(@Param(value = "authentication") String authentication);//专家审核列表数据  参数认证 认证   0=未认证 1 =审核中 2=认证成功 3=认证失败
}
