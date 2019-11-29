package com.mol.fadada.handler;

import com.alibaba.fastjson.JSON;
import com.fadada.sdk.client.FddClientBase;
import com.mol.fadada.dao.ContractUploadMapper;
import com.mol.fadada.pojo.ContractUploadRecord;
import com.mol.oos.OOSConfig;
import com.mol.oos.TYOOSUtil;
import entity.ServiceResult;
import lombok.Synchronized;
import lombok.extern.java.Log;
import util.IdWorker;
import util.TimeUtil;
import java.io.File;
import java.io.IOException;

/**
 * 法大大合同管理
 */
@Log
public class ContractHandler {

    /**
     * 参数
     */
    private static String APP_ID = "402664";
    private static String APP_SECRET = "xeVHW4Cbn8nWgwCWC16VDbVe";
    private static String V = "2.0";
    private static String HOST = "https://testapi.fadada.com:8443/api/";
    private static FddClientBase clientBase = new FddClientBase(APP_ID,APP_SECRET,V,HOST);

    /**
     * 上传法大大合同
     * @param contractTitle     合同标题
     * @param file              合同文件
     * @return                  ServiceResult(success=true, code=1, message=success, result=20191122110558962)，result为法大大合同编码
     * @throws IOException
     */
    @Synchronized
    public static ServiceResult uploadContract(String contractTitle, File file) throws IOException {
        String contractId = TimeUtil.getNow(TimeUtil.payOrderFormat);
        String resultStr = clientBase.invokeUploadDocs(contractId,contractTitle , file, "", ".pdf");
        log.info(resultStr);
        String result =  JSON.parseObject(resultStr).getString("result");
        ServiceResult sr = null;
        if("success".equals(result))
        {
            //存入数据库
            ContractUploadRecord contractUploadRecord = new ContractUploadRecord();
            contractUploadRecord.setId(new IdWorker().nextId()+"");
            contractUploadRecord.setContractId(contractId);
            contractUploadRecord.setUploadTime(TimeUtil.getNowDateTime());
            ContractUploadMapper contractUploadMapper = RecordDbHandler.getContractUploadMapper();
            System.out.println(contractUploadMapper);
            try{
                int insert = contractUploadMapper.insert(contractUploadRecord);
                //存入oos
                TYOOSUtil.getUtil().uploadObjToBucket(OOSConfig.法大大文件夹,"contract/fadaddaUploadBackup/"+contractId+".pdf",file);
            }catch (Exception e)
            {
                log.warning("法大大合同上传成功，但是数据库记录或者上传入oos发生异常！");
                e.printStackTrace();
            }
            sr = ServiceResult.success(contractId);
        }else{
            sr = ServiceResult.failureMsg("上传失败！");
        }
        log.info(sr.toString());
        return sr;
    }

    public static void main(String[] args) {
        //上传合同
//        File file = new File("d:/跟千千公司的采购合同.pdf");
//        log.info(""+file.exists());
//        if(file.exists()){
//            try{
//                uploadContract("与大千公司的采购合同",file);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

        //上传模板：
    }
}
