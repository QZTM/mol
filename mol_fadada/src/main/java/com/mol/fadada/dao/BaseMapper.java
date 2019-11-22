package com.mol.fadada.dao;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;

public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T>,ConditionMapper<T>,RowBoundsMapper<T> {

}