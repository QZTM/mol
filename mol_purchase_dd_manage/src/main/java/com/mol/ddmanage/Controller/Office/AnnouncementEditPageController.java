package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Ben.Office.AnnouncementEditPageben;
import com.mol.ddmanage.Service.Office.AnnouncementEditPageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/AnnouncementEditPageController")
public class AnnouncementEditPageController
{

    @Resource
    AnnouncementEditPageService announcementEditPageService;
    @RequestMapping("/AnnouncementList")
    public Map AnnouncementList(@RequestBody AnnouncementEditPageben json)
    {
          return  announcementEditPageService.AnnouncementListLogic(json);
    }
}
