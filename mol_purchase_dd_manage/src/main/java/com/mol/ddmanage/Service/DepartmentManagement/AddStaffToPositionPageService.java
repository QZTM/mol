package com.mol.ddmanage.Service.DepartmentManagement;

import com.mol.ddmanage.Util.DataUtil;
import com.mol.ddmanage.mapper.DepartmentMangement.AddStaffToPositionPageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class AddStaffToPositionPageService
{

    @Resource
    AddStaffToPositionPageMapper addStaffToPositionPageMapper;
    public boolean AddStaffToPositionLogic(String ids, String jurisdictionId)
    {
        try
        {
            String []idss=ids.split(",");
            addStaffToPositionPageMapper.DeleteStaffToPosition(jurisdictionId);
            for (int n=1;n<idss.length;n++)
            {
                addStaffToPositionPageMapper.AddStaffToPosition(DataUtil.GetTimestamp()+n,idss[n],jurisdictionId);
            }
            return true;
        }
        catch (Exception e)
        {
        return false;
        }
    }


    public ArrayList<Map> GetPositionPeolpeLogic(String jurisdictionId)
    {
        ArrayList <Map> sss=new ArrayList<>();
        return addStaffToPositionPageMapper.GetPositionPeolpeLogic(jurisdictionId);
    }
}
