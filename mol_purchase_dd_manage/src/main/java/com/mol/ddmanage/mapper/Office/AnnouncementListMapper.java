package com.mol.ddmanage.mapper.Office;

import com.mol.ddmanage.Ben.Office.AnnouncementEditPageben;

import java.util.ArrayList;

public interface AnnouncementListMapper
{
    ArrayList<AnnouncementEditPageben> AnnouncementMessage();//消息通知接口
    void DeleteAnnouncementList(String announcemenId,String messageType);//删除消息
}
