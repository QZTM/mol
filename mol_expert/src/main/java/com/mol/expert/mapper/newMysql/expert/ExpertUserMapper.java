package com.mol.expert.mapper.newMysql.expert;

import com.mol.expert.base.BaseMapper;
import com.mol.expert.entity.expert.ExpertUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName:ExpertUserMapper.xml
 * Package:com.purchase.mapper.newMysql.expert
 * Description
 *
 * @date:2019/10/8 15:07
 * @author:yangjiangyan
 */
@Mapper
public interface ExpertUserMapper extends BaseMapper<ExpertUser> {
    ExpertUser findExpertByDdId(String ddId);

    void updataReviewAndPassAndRateById(String id, String reviewCount, String successCount, String suAndFaCount);
}
