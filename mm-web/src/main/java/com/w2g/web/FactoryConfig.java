package com.w2g.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 用于声明一个bean，注入校验器到springboot的运行环境中
 */
@Configuration
@EnableAutoConfiguration
public class FactoryConfig {

    final static Logger logger= LoggerFactory.getLogger(FactoryConfig.class);

    /**
     * 可以在controller的方法级别直接使用@Validator注解校验参数
     * @return
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }

}
