package com.mol.purchase.mapper.newMysql.dingding.purchase;

import com.mol.base.BaseMapper;
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
