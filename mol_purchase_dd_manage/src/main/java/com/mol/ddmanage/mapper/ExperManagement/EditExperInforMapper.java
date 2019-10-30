package com.mol.ddmanage.mapper.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.SetExperApprovalben;
import org.apache.ibatis.annotations.Param;

public interface EditExperInforMapper
{
    void ExperInforUpload(SetExperApprovalben setExperApprovalben);
    void AddExperInfor(String ExperId);//新建一张表
    void ImageUpload(@Param(value = "ExperId") String ExperId,@Param(value = "image") byte[] image,@Param(value = "number") String number);//上传专家图片
}
