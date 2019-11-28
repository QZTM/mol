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

    /**
     * 注册法大大账号
     * @return
     */
     public Map RegisteredAccount()
     {
         Map map =new HashMap();
         ServiceResult serviceResult=RegistAndAuthHandler.regAccount("1161174896704700416","2");
         map.put("statu",serviceResult.isSuccess());
         return map;
     }

/*     public Map CertificationAccountLogic()
     {

     }*/
    /**
     *
     * @param file
     * @param map
     * @return
     */
    public Map Upload_Contract_Logic(MultipartFile file,Map map)
    {
        Map map1=new HashMap();
        try {
            if (RegistAndAuthHandler.checkIfRegisted("1161174896704700416","2").isSuccess()==false)//查询是否注册
            {
                map1.put("statu","2");//未注册
                return map1;
            }
            else if (RegistAndAuthHandler.checkIfAuthed("1161174896704700416","2").isSuccess()==false)//查询是否认证
            {
                map1.put("statu","3");//未认证
                return map1;
            }
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

                 ServiceResult result =ContractHandler.uploadContract(toFile.getName(),toFile);//调用法大上传合同接口上传
                 if(result.isSuccess()==true)
                 {
                     electronicContractSigninginforMapper.Upload_Contract(String.valueOf(new IdWorker().nextId()) ,result.getResult().toString(), DataUtil.GetNowSytemTime(),map.get("userid").toString());//保存上传合同的信息
                     //******自动签署接口预留位置******
                     electronicContractSigninginforMapper.Supplier_Contract(String.valueOf(new IdWorker().nextId()),map.get("purchase_id").toString(),map.get("supplier_id").toString(),result.getResult().toString(),"",DataUtil.GetNowSytemTime(),DataUtil.GetNowSytemTime(),"2");
                 }
                 ins.close();
            }

            map1.put("statu","0");
            return map1;
        }
        catch (Exception e)
        {
           map1.put("statu","1");
           return map1;
        }
    }

    /**
     * 查看合同返回url
     * @param purchasId  订单id
     * @param supplierid 供应商id
     * @return 合同地址url
     */
    public Map SetContractLogic(String purchasId,String supplierid)//查看合同url
    {
        Map map=new HashMap();
        try
        {
            Map map1=electronicContractSigninginforMapper.GetContractId(purchasId,supplierid);//获取合同id
            if (map1.get("contract_id")==null)//没有此供应商的合同id
            {
                map.put("statu",false);
                return map;
            }
            ServiceResult serviceResult= RegistAndAuthHandler.SetContract(map1.get("contract_id").toString());
            if (serviceResult.isSuccess()==true)
            {
                map.put("statu",serviceResult.isSuccess());
                map.put("url",serviceResult.getResult().toString());
                return map;
            }
            else
            {
                map.put("statu",serviceResult.isSuccess());
                return map;
            }
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
