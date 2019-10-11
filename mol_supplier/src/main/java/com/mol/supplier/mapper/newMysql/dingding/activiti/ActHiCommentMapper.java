package com.mol.supplier.mapper.newMysql.dingding.activiti;

import com.mol.supplier.entity.activiti.ActHiComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName:ActHiCommentMapper
 * Package:com.mol.supplier.mapper.newMysql.dingding.activiti
 * Description
 *
 * @date:2019/9/26 13:21
 * @author:yangjiangyan
 */
@Mapper
public interface ActHiCommentMapper {

    List<ActHiComment> getComByProDfId(String procInstId);
}
