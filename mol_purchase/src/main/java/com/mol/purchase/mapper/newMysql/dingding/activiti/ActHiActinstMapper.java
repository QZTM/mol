package com.mol.purchase.mapper.newMysql.dingding.activiti;

import com.mol.purchase.entity.activiti.ActHiActinst;

import java.util.List;

/**
 * @Classname ActHiActinstMapper
 * @Description TODO
 * @Date 2019/11/8 14:03
 * @Created by Lenovo
 */
public interface ActHiActinstMapper {

    List<ActHiActinst> getListByPurId(String purId);
}
