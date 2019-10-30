package com.mol.ddmanage.mapper.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.SetExperApprovalben;
import org.apache.ibatis.annotations.Param;

public interface SetExperApprovalMapper
{
    SetExperApprovalben SetExperApprovalInfor (@Param(value = "id") String id);
    void PassOrRejectMapper(@Param(value = "id") String id,@Param(value = "authentication") String authentication);
}
