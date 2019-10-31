package com.mol.supplier.mapper.dingding.activiti;

import com.mol.supplier.entity.activiti.ActHiProcinst;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName:ActHiProcinstMapper
 * Package:com.mol.supplier.mapper.newMysql.dingding.activiti
 * Description
 *
 * @date:2019/9/26 13:11
 * @author:yangjiangyan
 */
@Mapper
public interface ActHiProcinstMapper {

    ActHiProcinst getProByPurId(String purId);
}
