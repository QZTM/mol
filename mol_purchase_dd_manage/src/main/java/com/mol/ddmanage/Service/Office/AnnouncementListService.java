package com.mol.ddmanage.Service.Office;

import com.mol.ddmanage.Ben.Office.AnnouncementEditPageben;
import com.mol.ddmanage.mapper.Office.AnnouncementListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnnouncementListService
{
    @Resource
    AnnouncementListMapper announcementListMapper;
    public ArrayList<AnnouncementEditPageben> AnnouncementList()
    {
       ArrayList<AnnouncementEditPageben> announcementEditPageben=announcementListMapper.AnnouncementMessage();
       for (int n=0;n<announcementEditPageben.size();n++)
       {
           announcementEditPageben.get(n).setNumber(String.valueOf(n));
           announcementEditPageben.get(n).setMessageType("消息通知");
       }
      return announcementEditPageben;
    }

    public Map DeleteAnnouncementListLogic(String announcemenId,String messageType)
    {
        Map map=new HashMap();
        try
        {
            announcementListMapper.DeleteAnnouncementList(announcemenId,messageType);
            map.put("statu",true);
            return map;
        }
        catch (Exception e)
        {
            map.put("statu",false);
            return map;
        }
    }
}
