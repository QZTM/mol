package com.mol.base;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;

/**
 * @author HCJ
 */

public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T>,ConditionMapper<T>,RowBoundsMapper<T> {

}