package filter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 文件名过滤器
 */
public class FileExtFilter implements FilenameFilter {
    public String fileExt ;
    public FileExtFilter(String fileExt){
        this.fileExt = fileExt;
    }
    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(fileExt);
    }

}
