package com.mol.purchase.mapper.newMysql;

import com.mol.base.BaseMapper;
import com.mol.purchase.entity.ExpertRecommend;
import com.mol.purchase.entity.dingding.solr.fyPurchase;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExpertRecommendMapper extends BaseMapper<ExpertRecommend> {
    void updataAdoptByPurIdAndExpertId(String purId, String expertId);

    void updataAdoptNotChecked(String purId);
}
