package com.mol.supplier.controller.uploadAndDownload;
import com.mol.supplier.service.uploadAndDownload.DelFileService;
import com.mol.supplier.service.uploadAndDownload.UploadService;
import entity.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传控制器
 */
@Controller
@RequestMapping("/file")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Resource
    private UploadService uploadService;

    @Autowired
    private DelFileService delFileService;

    //文件上传相关代码
    @RequestMapping(value = "upload")
    @ResponseBody
    public ServiceResult<String> upload(@RequestParam("file") MultipartFile file) {
        String newPath = uploadService.upload(file);
        logger.info("UploadController返回值："+newPath);
        return ServiceResult.success(newPath);

    }

    //文件下载相关代码
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {

        return uploadService.downloadFile(request,response);

    }

    //多文件上传
    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        return uploadService.handleFileUpload(request);
    }


    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    @ResponseBody
    public ServiceResult delFile(String filePath, HttpServletRequest request){
        String path = request.getServletContext().getRealPath("/");
        return delFileService.delFile(filePath);
    }

}