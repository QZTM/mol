package com.mol.expert.util;

import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.mol.expert.entity.dingding.login.DDDept;
import com.mol.expert.entity.dingding.login.DDUser;

public class DDEntityChangeUtil {

    /**
     * 把钉钉内部的对象转换为自定义对象
     * 该方法适用于把（通过部门id获取该部门人员列表的）Userlist转换为自定义的DDUser
     * @param user
     * @return
     */
    public static DDUser changeUserlistToDDUser(OapiUserListbypageResponse.Userlist user){
        if(user== null){
            throw new RuntimeException("需要转换的对象为空");
        }
        DDUser ddUser = new DDUser();
        ddUser.setUserid(user.getUserid());
        ddUser.setUnionid(user.getUnionid());
        ddUser.setDingId(user.getDingId());
        ddUser.setName(user.getName());
        ddUser.setMobile(user.getMobile());
        ddUser.setEmail(user.getEmail());
        ddUser.setAvatar(user.getAvatar());
//        ddUser.setExtattr(user.getExtattr());
//        ddUser.setHiredDate(user.getHiredDate());
//        ddUser.setIsAdmin(user.getIsAdmin());
//        ddUser.setIsBoss(user.getIsBoss());
//        ddUser.setIsHide(user.getIsHide());
//        ddUser.setJobnumber(user.getJobnumber());
//        ddUser.setActive(user.getActive());
//        ddUser.setOrgEmail(user.getOrgEmail());
//        ddUser.setPosition(user.getPosition());
//        ddUser.setRemark(user.getRemark());
//        ddUser.setTel(user.getTel());
//        ddUser.setWorkPlace(user.getWorkPlace());
        return ddUser;
    }



    /**
     * 把钉钉内部的对象转换为自定义对象
     * 该方法适用于把（通过部门id获取人员详情）OapiUserGetResponse转换为自定义的DDUser
     * @param user
     * @return
     */
    public static DDUser changeOapiUserGetResponseToDDUser(OapiUserGetResponse user){
        if(user== null){
            throw new RuntimeException("需要转换的对象为空");
        }
        DDUser ddUser = new DDUser();
        ddUser.setUserid(user.getUserid());
        ddUser.setUnionid(user.getUnionid());
        ddUser.setDingId(user.getDingId());
        ddUser.setName(user.getName());
        ddUser.setMobile(user.getMobile());
        ddUser.setEmail(user.getEmail());
        ddUser.setAvatar(user.getAvatar());
        ddUser.setDepartment(user.getDepartment());
        ddUser.setStateCode(user.getStateCode());
//        ddUser.setExtattr(user.getExtattr());
//        ddUser.setHiredDate(user.getHiredDate());
//        ddUser.setIsAdmin(user.getIsAdmin());
//        ddUser.setIsBoss(user.getIsBoss());
//        ddUser.setIsHide(user.getIsHide());
//        ddUser.setJobnumber(user.getJobnumber());
//        ddUser.setActive(user.getActive());
//        ddUser.setOrgEmail(user.getOrgEmail());
//        ddUser.setPosition(user.getPosition());
//        ddUser.setRemark(user.getRemark());
//        ddUser.setTel(user.getTel());
//        ddUser.setWorkPlace(user.getWorkPlace());
        return ddUser;
    }


    /**
     * 把钉钉内部的对象转换为自定义对象
     * 该方法适用于把（通过部门id获取部门详情）OapiDepartmentGetResponse转换为自定义的DDDept
     * @param dept
     * @return
     */
    public static DDDept changeOapiDepartmentGetResponseToDDDept(OapiDepartmentGetResponse dept){
        if(dept == null){
            throw new RuntimeException("需要转换的对象为空");
        }
        DDDept ddDept = new DDDept();
        ddDept.setId(dept.getId());
        ddDept.setParentid(dept.getParentid());
        ddDept.setName(dept.getName());
        ddDept.setDeptManagerUseridList(dept.getDeptManagerUseridList());
        ddDept.setOrgDeptOwner(dept.getOrgDeptOwner());
        return ddDept;
    }


}
