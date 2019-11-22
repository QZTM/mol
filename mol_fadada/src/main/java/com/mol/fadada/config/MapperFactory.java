package com.mol.fadada.config;

import org.apache.ibatis.session.SqlSession;
import tk.mybatis.mapper.common.Mapper;

public class MapperFactory {

    /**
     * 创建 mapper 实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createMapper(Class<? extends Mapper> clazz) {
        SqlSession sqlSessionProxy = MysqlSessionFactory.getSqlSessionProxy();
        Mapper mapper = MysqlSessionFactory.getConfiguration().getMapper(clazz, sqlSessionProxy);
        return (T) mapper;
    }

}
