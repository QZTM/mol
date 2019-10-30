package com.mol.expert.mapper.newMysql.expert;

import com.mol.expert.base.BaseMapper;
import com.mol.expert.entity.dingding.solr.fyPurchase;
import com.mol.expert.entity.expert.ExpertRecommend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName:ExpertRecomendMapper
 * Package:com.purchase.mapper.newMysql.expert
 * Description
 *
 * @date:2019/10/7 14:03
 * @author:yangjiangyan
 */
@Mapper
public interface ExpertRecomendMapper extends BaseMapper<ExpertRecommend> {

    List<fyPurchase> findPurAndAdopt(String adopt, String expertId);
    List<fyPurchase> findPurAndAdoptSuccessOrFail(String expertId);
    List<fyPurchase> findPur(String expertId);
}
