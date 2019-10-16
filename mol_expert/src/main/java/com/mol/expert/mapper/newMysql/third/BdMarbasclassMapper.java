package com.mol.expert.mapper.newMysql.third;

import com.mol.expert.base.BaseMapper;
import entity.BdMarbasclass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName:BdMarbasclassMapper
 * Package:com.purchase.mapper.newMysql.third
 * Description
 *
 * @date:2019/8/1 16:53
 * @author:yangjiangyan
 */
@Mapper
public interface BdMarbasclassMapper extends BaseMapper<BdMarbasclass> {

    List<BdMarbasclass> findMarbasFirstList();

    List<BdMarbasclass> findThreeMarListByPkmarbasClass(String goodsType);

    List<BdMarbasclass> findFourMarListByPkmarbasClass(String goodsType);

    List<BdMarbasclass> findListByCodeLike(String code);
}
