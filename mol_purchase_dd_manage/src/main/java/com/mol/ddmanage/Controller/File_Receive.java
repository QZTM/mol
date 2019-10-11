package com.mol.ddmanage.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;

@RequestMapping("/File_Receive")
@RestController
public class File_Receive
{
    @RequestMapping("/Receive_default_file")//文件上传
    public String Receive_default_file(@RequestParam("file") ArrayList<MultipartFile> files)
    {
        try {
            for (MultipartFile file :files)
            {
                String fileName = file.getOriginalFilename();
                String dir=System.getProperty("user.dir");
                String destFileName=dir+ File.separator +"uploadedfiles_"+fileName;
                System.out.println(destFileName);
                File destFile = new File(destFileName);
                file.transferTo(destFile);
            }
            return "上传成功";
          //  System.out.println("开始读取EXCEL内容");
        }
        catch (Exception e)
        {
            return "上传失败";
        }
    }
}
