package com.mol.purchase.mapper.newMysql;

import com.mol.base.BaseMapper;
import com.mol.purchase.entity.QuotePayresult;

import java.util.List;

/**
 * @Classname QuotePayresultMapper
 * @Description TODO
 * @Date 2019/11/5 11:45
 * @Created by Lenovo
 */
public interface QuotePayresultMapper extends BaseMapper<QuotePayresult> {

    Integer updataStatusByPurId(String status, String time, String purId);

    List<QuotePayresult> findPayResultListByStatus(String status);
}
