package com.mol.purchase.service.uploadAndDownload;

import com.mol.purchase.config.Constant;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.UploadUtils;

import java.io.File;

/**
 * 删除文件
 */
@Service
public class DelFileService {

    private Logger logger = LoggerFactory.getLogger(DelFileService.class);

    public ServiceResult delFile(String httpFilePath){
        //根据网络路径获取真实路径
        logger.debug(httpFilePath);
        httpFilePath = httpFilePath.substring(httpFilePath.indexOf(UploadUtils.PATH_PREFIX)-1);
        File file = new File(Constant.ROOT_DIR +"/src/main/resources/"+httpFilePath);
        if(file.exists()){
            if(file.delete()){
                return ServiceResult.success("删除成功！");
            }
        }
        return ServiceResult.failure("删除失败");
    }
}
