package com.mol.quartz.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.mol.quartz.entity.DuridBean;

import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "spring.datasource.druid")
@MapperScan(basePackages = DuridDatasource.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DuridDatasource {
	
	@Autowired
    private DuridBean druidProperties;
	
	/**
     * dao层的包路径
     */
    static final String PACKAGE = "com.mol.quartz.mapper";
    
    /**
     * mapper文件的相对路径
     */
    private static final String MAPPER_LOCATION = "classpath:/mybatis/*.xml";
	
	
	@Bean(name="dataSource")
    @Primary
    public DataSource primaryDataSource (){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.druidProperties.getUrl());
        datasource.setUsername(this.druidProperties.getUsername());
        datasource.setPassword(this.druidProperties.getPassword());
        datasource.setDriverClassName(this.druidProperties.getDriverClassName());
        datasource.setInitialSize(this.druidProperties.getInitialSize());
        datasource.setMinIdle(this.druidProperties.getMinIdle());
        datasource.setMaxActive(this.druidProperties.getMaxActive());
        datasource.setMaxWait(this.druidProperties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(this.druidProperties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(this.druidProperties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(this.druidProperties.getValidationQuery());
        datasource.setTestWhileIdle(this.druidProperties.getTestWhileIdle());
        datasource.setTestOnBorrow(this.druidProperties.getTestOnBorrow());
        datasource.setTestOnReturn(this.druidProperties.getTestOnReturn());
        return datasource;
    }
	
	
	// 创建Mybatis的连接会话工厂实例
    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);  // 设置数据源bean
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DuridDatasource.MAPPER_LOCATION));  // 设置mapper文件路径
        sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory.getObject();
    }

}
