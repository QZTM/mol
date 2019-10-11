package com.mol.supplier.mapper.newMysql.dingding.workBench;

import com.mol.supplier.entity.dingding.purchase.workBench.BigDataStar;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName:PoOrderMapper
 * Package:com.mol.supplier.mapper.newMysql.dingding.workBench
 * Description
 *
 * @date:2019/8/22 13:25
 * @author:yangjiangyan
 */
@Mapper
public interface PoOrderMapper {
    //获取某公司某物料的历史报价情况
    List<BigDataStar> getBigDataBySuppliedAndpkMaterialId(String supplierId, String pkMaterialId);

    List<Double> getNorigpriceBySupplierIdAndMaterialId(String supplierId, String id);
}
