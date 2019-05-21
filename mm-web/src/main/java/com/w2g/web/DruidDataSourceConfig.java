package com.w2g.web;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 阿里数据源配置类
 */
@Configuration
public class DruidDataSourceConfig implements EnvironmentAware{


    private RelaxedPropertyResolver propertyResolver;

    public void setEnvironment(Environment env) {
        this.propertyResolver = new RelaxedPropertyResolver(env,"spring.datasource.");
    }

    @Bean(name="dataSource")
    public DataSource dataSource(){
        System.out.println("注入druid !!!!!!!!!");

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(propertyResolver.getProperty("url"));
        dataSource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
        dataSource.setUsername(propertyResolver.getProperty("username"));
        dataSource.setPassword(propertyResolver.getProperty("password"));
        dataSource.setInitialSize(Integer.valueOf(propertyResolver.getProperty("initial-size")));
        dataSource.setMinIdle(Integer.valueOf(propertyResolver.getProperty("min-idle")));
        dataSource.setMaxWait(Long.valueOf(propertyResolver.getProperty("max-wait")));
        dataSource.setMaxActive(Integer.valueOf(propertyResolver.getProperty("max-active")));
        dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(propertyResolver.getProperty("min-evictable-idle-time-millis")));

        System.out.println("数据源注入成功！");
        try {
            dataSource.setFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}

