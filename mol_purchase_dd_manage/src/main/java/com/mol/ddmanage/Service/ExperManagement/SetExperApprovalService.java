package com.mol.ddmanage.Service.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.SetExperApprovalben;
import com.mol.ddmanage.mapper.ExperManagement.SetExperApprovalMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SetExperApprovalService
{
    @Resource
    SetExperApprovalMapper setExperApprovalMapper;
    public SetExperApprovalben SetExperApprovalInfor(String id)
    {
        SetExperApprovalben setExperApprovalben=setExperApprovalMapper.SetExperApprovalInfor(id);
        return setExperApprovalben;
    }
    public String PassOrRejectLogic(String id,String authentication)
    {
        try
        {
            setExperApprovalMapper.PassOrRejectMapper(id,authentication);
            return "提交成功";
        }
        catch (Exception e)
        {
            return "提交失败";
        }

    }
}
