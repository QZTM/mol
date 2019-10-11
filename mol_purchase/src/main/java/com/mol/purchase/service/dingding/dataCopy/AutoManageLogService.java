package com.mol.purchase.service.dingding.dataCopy;

import org.apache.tools.zip.ZipOutputStream;
import org.springframework.stereotype.Service;
import util.FileUtils;
import util.TimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
public class AutoManageLogService {

    private static String logFilePath="C:\\Program Files\\pdi-ce-8.2.0.0-342\\log\\";
    //private static String logFilePath="E:\\pdi-ce-8.2.0.0-342\\log\\";
    private static String fileExt = ".txt";

    public void zipLog() throws FileNotFoundException {
        System.out.println("zipLog........");
        File[] files = FileUtils.listPath(new File(logFilePath),fileExt);
        System.out.println(files.length);
        if(files.length == 0){
            return;
        }
        File file = new File(logFilePath + TimeUtil.getNow("yyyy-MM-dd HH mm ss")+".zip");
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(logFilePath + TimeUtil.getNow("yyyy-MM-dd HH mm ss")+".zip"));
        zos.setEncoding("GBK");
        try {
            boolean isSuccess = FileUtils.zip(files,"",zos);
            if(isSuccess){
                for(File f:files){
                    FileUtils.forceDelete(f);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
