package com.mol.ddmanage.Service.Office;

import com.mol.ddmanage.Ben.Office.AnnouncementEditPageben;
import com.mol.ddmanage.Util.DataUtil;
import com.mol.ddmanage.Util.Dingding_Tools;
import com.mol.ddmanage.mapper.Office.announcementEditPageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnnouncementEditPageService
{
    @Resource
    private announcementEditPageMapper announcementEditPageMapper;
    public Map AnnouncementListLogic(AnnouncementEditPageben json)
    {
        Map map=new HashMap();
        try
        {
            json.setCreadtime(DataUtil.GetNowSytemTime());//添加创建时间
            json.setAnnouncemenId(DataUtil.GetTimestamp());//添加id
            if (json.getAlluser().equals("0"))
            {
                Dingding_Tools.PutMessageAnnouncement(json.getUserids(),json.getTitl()+"\n"+json.getText(),false);
            }
            else if (json.getAlluser().equals("1"))
            {
                Dingding_Tools.PutMessageAnnouncement(json.getUserids(),json.getTitl()+"\n"+json.getText(),true);
            }
            announcementEditPageMapper.Announcement(json);
            map.put("statu",true);
            return  map;
        }
        catch (Exception e)
        {
            map.put("statu",false);
            return map;
        }
    }
}
