package com.mol.ddmanage.Service.DepartmentManagement;

import com.mol.ddmanage.Ben.DepartmentManagement.AddJurisdictionben;
import com.mol.ddmanage.mapper.DepartmentMangement.JurisdictionManagementEditMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class JurisdictionManagementEditService
{
    @Resource
    JurisdictionManagementEditMapper jurisdictionManagementEditMapper;
   public AddJurisdictionben GetJurisdictionPositionLogic(String jurisdictionId)
   {
       AddJurisdictionben addJurisdictionben=new AddJurisdictionben();
       addJurisdictionben= jurisdictionManagementEditMapper.GetJurisdictionPosition(jurisdictionId);
      return   addJurisdictionben;
   }

   public Map UpdateJurisdictionLogic(AddJurisdictionben json)
   {
       Map map=new HashMap();
      try
      {
          map.put("status",true);
        jurisdictionManagementEditMapper.UpdateJurisdiction(json);
        return map;
      }
      catch (Exception e)
      {
          map.put("status",false);
          return map;
      }
   }
}
