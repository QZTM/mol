package com.mol.ddmanage.Service.Office;

import com.mol.ddmanage.Util.DataUtil;
import com.mol.ddmanage.mapper.Office.ElectronicContractSigninginforMapper;
import com.mol.fadada.handler.ContractHandler;
import com.mol.fadada.handler.RegistAndAuthHandler;
import entity.ServiceResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import util.IdWorker;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ElectronicContractSigninginforService
{
    @Resource
    ElectronicContractSigninginforMapper electronicContractSigninginforMapper;
    public Map Upload_Contract_Logic(MultipartFile file,Map map)
    {
        Map map1=new HashMap();

        try {
            ServiceResult sss=RegistAndAuthHandler.checkIfRegisted("1161174896704700416","2");//查询是否注册
            ServiceResult ddd=RegistAndAuthHandler.checkIfAuthed("1161174896704700416","2");//查询是否注册
            File toFile = null;//准备上传的合同
            if (file.equals("") || file.getSize() <= 0)
            {
                file = null;
            }
            else
             {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);

                 ServiceResult ss =ContractHandler.uploadContract("1111",toFile);//调用法大上传合同接口上传
                 electronicContractSigninginforMapper.Upload_Contract(String.valueOf(new  IdWorker().nextId()) ,"", DataUtil.GetNowSytemTime(),map.get("userid").toString());//保存上传合同的信息
                ins.close();
            }

            map1.put("statu",true);
            return map1;
        }
        catch (Exception e)
        {
           map1.put("statu",false);
           return map1;
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
