package com.mol.ddmanage.mapper.DepartmentMangement;

import com.mol.ddmanage.Ben.DepartmentManagement.AddJurisdictionben;

public interface JurisdictionManagementEditMapper
{
    AddJurisdictionben GetJurisdictionPosition(String jurisdictionId);
    void UpdateJurisdiction(AddJurisdictionben json);
}
