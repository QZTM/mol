package com.mol.expert.mapper.newMysql.dingding.activiti;

import com.mol.expert.entity.activiti.ActHiProcinst;
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
public interface ActHiProcinstMapper {

    ActHiProcinst getProByPurId(String purId);
}
