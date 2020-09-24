package com.hints.servertwo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "testDataSource")
    @Qualifier("testDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.main")
    public DataSource initMainDataSource() throws SQLException {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test2DataSource")
    @Qualifier("test2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.subo")
    public DataSource initSuboDataSource() throws SQLException {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public Dao initNutzDao(@Qualifier("testDataSource") DataSource dataSource){
        return new NutDao(dataSource);
    }

    @Bean(name = "sdao")
    public Dao initsNutzDao(@Qualifier("test2DataSource") DataSource dataSource){
        return new NutDao(dataSource);
    }


}