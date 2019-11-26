package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Ben.Office.AnnouncementEditPageben;
import com.mol.ddmanage.Service.Office.AnnouncementListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/AnnouncementListController")
public class AnnouncementListController
{
    @Resource
    AnnouncementListService announcementListService;
    @RequestMapping("/AnnouncementList")
    public ArrayList<AnnouncementEditPageben> Announcement()//获取公告列表
    {
       return announcementListService.AnnouncementList();
    }
    @RequestMapping("/DeleteAnnouncementList") //删除公告
    public Map DeleteAnnouncementList(@RequestParam String announcemenId, @RequestParam String messageType)//参数 公告id 消息类型 1消息通知 2任务安排 3公司新闻
    {
        return announcementListService.DeleteAnnouncementListLogic(announcemenId,messageType);
    }
}
