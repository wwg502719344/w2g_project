package com.w2g;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MmWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.getProperties().put("projectName", "springApp");
		SpringApplication.run(MmWebApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(MmWebApplication.class);
	}
}
