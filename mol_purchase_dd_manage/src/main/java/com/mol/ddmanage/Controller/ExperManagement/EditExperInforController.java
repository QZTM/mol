package com.mol.ddmanage.Controller.ExperManagement;

import com.mol.ddmanage.Service.ExperManagement.EditExperInforService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/ExperManagement")
public class EditExperInforController
{
    @Resource
    EditExperInforService editExperInforService;
   @RequestMapping("/ImageUpload") //修改专家信息
    public String ImageUpload(@RequestParam("file") ArrayList<MultipartFile> files, @RequestParam Map map)
   {
       return editExperInforService.ImageUploadLogic(files,map);
   }

    @RequestMapping("/AddExperInfor")//添加专家信息
    public String ExpertInfor(@RequestParam("file") ArrayList<MultipartFile> files, @RequestParam Map map)
    {
        return editExperInforService.AddExperInforLogic(files,map);
    }
}
