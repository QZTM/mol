package com.mol.purchase.mapper.newMysql.dingding.activiti;

import com.mol.base.BaseMapper;
import com.mol.purchase.entity.activiti.ActHiProcinst;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName:ActHiProcinstMapper
 * Package:com.purchase.mapper.newMysql.dingding.activiti
 * Description
 *
 * @date:2019/9/26 13:11
 * @author:yangjiangyan
 */
@Mapper
public interface ActHiProcinstMapper extends BaseMapper<ActHiProcinst> {

    ActHiProcinst getProByPurId(String purId);
    ActHiProcinst getProByProId(String proId);
}
