package com.mol.expert.mapper.newMysql.dingding.purchase;

import com.mol.expert.entity.thirdPlatform.BdMaterialRepair;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName:BdMaterialRepairMapper
 * Package:com.purchase.mapper.newMysql.dingding.purchase
 * Description
 *  加工维修
 * @date:2019/9/16 16:55
 * @author:yangjiangyan
 */
@Mapper
public interface BdMaterialRepairMapper {
    void insert(BdMaterialRepair mr);
}
