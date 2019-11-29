package com.mol.purchase.mapper.newMysql;

import com.mol.base.BaseMapper;
import com.mol.purchase.entity.ExpertUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExpertUserMapper extends BaseMapper<ExpertUser> {
    ExpertUser findExpertUserById(String expertId);

    Integer updataAwardByExpertId(String newAward, String expertId);
}
