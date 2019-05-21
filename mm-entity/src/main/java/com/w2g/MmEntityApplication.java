package com.w2g;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.w2g.mapper")
@EnableCaching
public class MmEntityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmEntityApplication.class, args);
	}
}
