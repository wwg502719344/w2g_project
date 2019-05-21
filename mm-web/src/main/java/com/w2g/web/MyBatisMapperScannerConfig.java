package com.w2g.web;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.w2g.mapper");
        //mapperScannerConfigurer.setMarkerInterface(com.w2g.core.markerMapper.CommonMapper.class);
        mapperScannerConfigurer.setMarkerInterface(com.w2g.core.markerMapper.CommonMapper.class);
        System.out.println("***********************************扫描mapper成功！");
        return mapperScannerConfigurer;
    }
}*/
