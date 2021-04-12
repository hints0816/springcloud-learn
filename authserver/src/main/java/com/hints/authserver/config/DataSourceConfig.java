package com.hints.authserver.config;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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

    @Bean(name = "test3DataSource")
    @Qualifier("test3DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.gscm")
    public DataSource initGscmDataSource() throws SQLException {
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

    @Bean(name = "zdao")
    public Dao initssNutzDao(@Qualifier("test3DataSource") DataSource dataSource){
        return new NutDao(dataSource);
    }
}