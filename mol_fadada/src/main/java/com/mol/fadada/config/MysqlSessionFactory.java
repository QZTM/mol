package com.mol.fadada.config;

import com.mol.fadada.pojo.AuthRecord;
import com.mol.fadada.pojo.RegistRecord;
import com.mol.fadada.pojo.SignResultRecord;
import lombok.extern.java.Log;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@Log
public class MysqlSessionFactory {


    private static final String resource = "mybatis-config.xml";

    private static SqlSessionFactory sqlSessionFactory;

    static {
        // 加载配置文件
        InputStream in = MysqlSessionFactory.class.getClassLoader().getResourceAsStream(resource);
        // 创建SqlSessionFactory实例
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        // 创建一个MapperHelper
        MapperHelper mapperHelper = new MapperHelper();
        // 特殊配置
        Config config = new Config();
        // 主键自增回写方法,默认值MYSQL,详细说明请看文档
        config.setIDENTITY("MYSQL");
        // 支持getter和setter方法上的注解
        config.setEnableMethodAnnotation(true);
        // 设置 insert 和 update 中，是否判断字符串类型!=''
        config.setNotEmpty(true);
        // 校验Example中的类型和最终调用时Mapper的泛型是否一致
        config.setCheckExampleEntityClass(true);
        // 启用简单类型
        config.setUseSimpleType(true);
        // 枚举按简单类型处理
        config.setEnumAsSimpleType(true);
        // 自动处理关键字 - mysql
        config.setWrapKeyword("`{0}`");
        config.setUseJavaType(true);
        // 设置配置
        mapperHelper.setConfig(config);
        // 配置 mapperHelper 后，执行下面的操作
        mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());
    }

    public static Configuration getConfiguration() {
        return sqlSessionFactory.getConfiguration();
    }

    public static SqlSession getSqlSessionProxy() {
        return (SqlSession) Proxy.newProxyInstance(SqlSessionFactory.class.getClassLoader(),
                new Class[]{SqlSession.class},
                new SqlSessionInterceptor());
    }

    private static class SqlSessionInterceptor implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            Object object = null;
            try {
                object = method.invoke(sqlSession, args);
            } catch (Exception e) {
                log.warning(e.getMessage());
            } finally {
                sqlSession.close();
            }
            return object;
        }
    }

}
