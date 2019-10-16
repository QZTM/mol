package com.mol.expert.util;
import java.io.File;
import java.io.FileInputStream;

import com.mol.expert.filter.FileExtFilter;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class FileUtils {

    /**
     * 缓冲
     */
    static final byte[] buffer = new byte[2048];


    /**
     * 筛选某路径下的某类型的文件
     *
     * @param file    目录路径
     * @param fileExt 文件扩展名
     * @return 符合条件的file数组
     */
    public static File[] listPath(File file, String fileExt) {
        if (!file.exists()) {
            throw new RuntimeException(file + "路径不存在");
        }
        return file.listFiles(new FileExtFilter(fileExt));
    }


    /**
     * 把多个文件压缩为zip
     * * @param files
     * 多个文件
     *
     * @param baseFolder 压缩到ZIP的父级目录(目录后面跟上File.separator)
     * @param zos        ZipOutputStream
     * @throws Exception
     */
    public static boolean zip(File[] files, String baseFolder, ZipOutputStream zos) {
        boolean isSuccess = false;
        try {
            // 输入
            FileInputStream fis = null;
            // 条目
            ZipEntry entry = null;
            // 数目
            int count = 0;
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归
                    zip(file.listFiles(), baseFolder + file.getName() + File.separator, zos);
                    continue;
                }
                entry = new ZipEntry(baseFolder + file.getName());
                // 加入
                zos.putNextEntry(entry);
                fis = new FileInputStream(file);
                // 读取
                while ((count = fis.read(buffer, 0, buffer.length)) != -1) {
                    // 写入
                    zos.write(buffer, 0, count);
                    zos.flush();
                }
                zos.closeEntry(); // 释放资源
            }
            zos.close();
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }


    /**
     * 强制删除
     *
     * @param f
     * @return
     */
    public static boolean forceDelete(File f) {
        boolean result = false;
        int tryCount = 0;
        while (!result && tryCount++ < 10) {
            System.gc();
            result = f.delete();
        }
        return result;
    }
}
