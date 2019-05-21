package com.w2g.web;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Created by w2g 2018-4-18
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {

    @Resource(name = "dataSource")
    DruidDataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.w2g.entity");
        //添加XML目录
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapperXml*//*.xml"));

        //分页插件
        //PageHelper pageHelper = new PageHelper();
        //Properties properties = new Properties();
        //properties.setProperty("reasonable", "true");
        //properties.setProperty("supportMethodsArguments", "true");
        //properties.setProperty("returnPageInfo", "check");
        //properties.setProperty("params", "count=countSql");
        //pageHelper.setProperties(properties);
        //bean.setPlugins(new Interceptor[]{pageHelper});
        
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
