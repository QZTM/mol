package com.mol.ddmanage.Service.Office;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ElectronicContractSigninginforService
{
    public Map Upload_Contract_Logic( MultipartFile file)
    {
        Map map=new HashMap();
        try {
            File toFile = null;
            if (file.equals("") || file.getSize() <= 0) {
                file = null;
            }
            else
             {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            }
            map.put("statu",true);
            return map;
        }
        catch (Exception e)
        {
           map.put("statu",false);
           return map;
        }
    }

    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
