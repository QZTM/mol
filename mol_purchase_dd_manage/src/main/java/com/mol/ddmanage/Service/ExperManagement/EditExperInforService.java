package com.mol.ddmanage.Service.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.SetExperApprovalben;
import com.mol.ddmanage.mapper.ExperManagement.EditExperInforMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import util.IdWorker;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class EditExperInforService
{
    @Autowired
    IdWorker idWorker;
    @Resource
    EditExperInforMapper editExperInforMapper;
    public String ImageUploadLogic(ArrayList<MultipartFile> files, Map map) //编辑专家信息
    {
        try {
            ArrayList<byte[]> imas=new ArrayList<>();
            SetExperApprovalben setExperApprovalben=new SetExperApprovalben();//专家资料实体类
            for (MultipartFile file :files)
            {
                byte[] bytes =file.getBytes();// 获取数据流
                imas.add(bytes);
            }

            setExperApprovalben.setId(map.get("ExperId").toString());
            setExperApprovalben.setName(map.get("name").toString());
            setExperApprovalben.setBirthday(map.get("birthday").toString());
            setExperApprovalben.setAge(map.get("age").toString());
            setExperApprovalben.setWork_life(map.get("work_life").toString());
            setExperApprovalben.setId_number(map.get("id_number").toString());
            setExperApprovalben.setIndustry("123");



            setExperApprovalben.setFront_of_id(imas.get(0));
            setExperApprovalben.setReverse_of_id(imas.get(1));
            setExperApprovalben.setFront_of_business(imas.get(2));
            setExperApprovalben.setReverse_of_business(imas.get(3));
            setExperApprovalben.setOther_documents_one(imas.get(4));
            setExperApprovalben.setOther_documents_two(imas.get(5));

            editExperInforMapper.ExperInforUpload(setExperApprovalben);//更新文字

            String [] img_delete=  map.get("ima_delete").toString().split(",");//删除操作标志位
            for (int n=1;n<=imas.size();n++)
            {
                if (imas.get(n-1).length!=0)//当有图片时更新图片
                {
                    editExperInforMapper.ImageUpload(setExperApprovalben.getId(),imas.get(n-1),String.valueOf(n));//专家图片的二进制上传到数据库
                }
                else if (imas.get(n-1).length==0 && img_delete[n].equals("1"))//当没有图片并且前端返回的是删除操作时，清除数据库图片
                {
                    editExperInforMapper.ImageUpload(setExperApprovalben.getId(),imas.get(n-1),String.valueOf(n));//专家图片的二进制上传到数据库
                }
            }
            return "上传成功";
        }
        catch (Exception e)
        {
            return "上传失败";
        }
    }

    public String AddExperInforLogic(ArrayList<MultipartFile> files, Map map)//添加专家信息
    {
        try {
            ArrayList<byte[]> imas=new ArrayList<>();
            SetExperApprovalben setExperApprovalben=new SetExperApprovalben();//专家资料实体类
            for (MultipartFile file :files)
            {
                byte[] bytes =file.getBytes();// 获取数据流
                imas.add(bytes);
            }
            setExperApprovalben.setId(String.valueOf(idWorker.nextId()));
            setExperApprovalben.setName(map.get("name").toString());
            setExperApprovalben.setBirthday(map.get("birthday").toString());
            setExperApprovalben.setAge(map.get("age").toString());
            setExperApprovalben.setWork_life(map.get("work_life").toString());
            setExperApprovalben.setId_number(map.get("id_number").toString());
            setExperApprovalben.setIndustry("123");

            setExperApprovalben.setFront_of_id(imas.get(0));
            setExperApprovalben.setReverse_of_id(imas.get(1));
            setExperApprovalben.setFront_of_business(imas.get(2));
            setExperApprovalben.setReverse_of_business(imas.get(3));
            setExperApprovalben.setOther_documents_one(imas.get(4));
            setExperApprovalben.setOther_documents_two(imas.get(5));

            editExperInforMapper.AddExperInfor(setExperApprovalben.getId());//新建一张表
            editExperInforMapper.ExperInforUpload(setExperApprovalben);//新增供应商字段信息
            for (int n=1;n<=imas.size();n++)
            {
                if (imas.get(n-1).length!=0)//新增图片
                {
                    editExperInforMapper.ImageUpload(setExperApprovalben.getId(),imas.get(n-1),String.valueOf(n));//专家图片的二进制上传到数据库
                }
            }
            return "添加成功";
        }
        catch (Exception e)
        {
            return "添加失败";
        }
    }
}
