package com.mol.purchase.mapper.newMysql.dingding.activiti;

import com.mol.base.BaseMapper;
import com.mol.purchase.entity.activiti.ActHiComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName:ActHiCommentMapper
 * Package:com.purchase.mapper.newMysql.dingding.activiti
 * Description
 *
 * @date:2019/9/26 13:21
 * @author:yangjiangyan
 */
@Mapper
public interface ActHiCommentMapper extends BaseMapper<ActHiComment> {

    List<ActHiComment> getComByProDfId(String procInstId);
}
