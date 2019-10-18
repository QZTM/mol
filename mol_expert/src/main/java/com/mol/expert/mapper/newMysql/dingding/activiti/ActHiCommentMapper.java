package com.mol.expert.mapper.newMysql.dingding.activiti;

import com.mol.expert.entity.activiti.ActHiComment;
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
public interface ActHiCommentMapper {

    List<ActHiComment> getComByProDfId(String procInstId);
}
