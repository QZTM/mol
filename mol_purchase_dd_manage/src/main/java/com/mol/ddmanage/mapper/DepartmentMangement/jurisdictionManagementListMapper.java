package com.mol.ddmanage.mapper.DepartmentMangement;

import com.mol.ddmanage.Ben.DepartmentManagement.jurisdictionManagementben;

import java.util.ArrayList;

public interface jurisdictionManagementListMapper
{
    ArrayList<jurisdictionManagementben> GetPositionList();
    void DeleteJurisdiction(String jurisdictionId);
    void Deleteuser_position(String jurisdictionId);
}
