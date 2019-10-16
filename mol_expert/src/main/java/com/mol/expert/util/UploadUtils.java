package com.mol.expert.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UploadUtils {
        // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
        public final static String PATH_PREFIX = "static/upload";

        public final static String img_dir = "/imgs";
        public final static String video_dir = "/videos";
        public final static String document_dir = "/documents";
        public final static String sound_dir = "/sounds";

        public final static List<String> img_suffixNames = new ArrayList<>(Arrays.asList("bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
                "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf"));
        public final static List<String> video_suffixNames = new ArrayList<>(Arrays.asList("mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm"));
        public final static List<String> document_suffixNames = new ArrayList<>(Arrays.asList( "txt", "doc", "docx", "xls", "htm", "html", "jsp", "rtf", "wpd", "pdf", "ppt" ));
        public final static List<String> sound_suffixNames = new ArrayList<>(Arrays.asList("mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
                "m4a", "vqf"));

        public static File getDirFile(){
            // 构建上传文件的存放 "文件夹" 路径
            String fileDirPath = new String("src/main/resources/" + PATH_PREFIX);
            File fileDir = new File(fileDirPath);
            if(!fileDir.exists()){
                // 递归生成文件夹
                fileDir.mkdirs();
            }
            //System.out.println(fileDir.getAbsolutePath());
            return fileDir;
        }


        //根据文件扩展名获取对应的上传文件夹的末尾文件夹名称
        public static String getRealDir(String suffixName) {
            suffixName = suffixName.toLowerCase().trim();
            if(suffixName.contains(".")){
                suffixName = suffixName.substring(1);
            }
            String realDir = "default";
            if(img_suffixNames.contains(suffixName)){
                realDir = img_dir;
            }else if(video_suffixNames.contains(suffixName)){
                realDir = video_dir;
            }else if(document_suffixNames.contains(suffixName)){
                realDir = document_dir;
            }else if(sound_suffixNames.contains(suffixName)){
                realDir = sound_dir;
            }
            return realDir;
        }

        //获取上传文件所在文件夹
    public static File getFileDirOfSave(String suffixName) {
            String realDir = getRealDir(suffixName);
            String fileDirPath = new String("src/main/resources/" + PATH_PREFIX + realDir);
            File fileDir = new File(fileDirPath);
            if(!fileDir.exists()){
                // 递归生成文件夹
                fileDir.mkdirs();
            }
            return fileDir;
    }

}
