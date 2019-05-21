/*
package com.w2g.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

*/
/**
 * Created by W2G on 2018/4/14.
 *//*

@Configuration
@EnableWebMvc
*/
/*@Import({Swagger2.class})*//*

@EnableAutoConfiguration
@EnableSwagger2
public class WebSpringConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;
*/
/*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static*//*
*/
/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars*//*
*/
/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }*//*


    @Bean
    public ViewResolver configureViewResolver() {
        InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
        viewResolve.setPrefix("/WEB-INF/views/");
        viewResolve.setSuffix(".jsp");

        return viewResolve;
    }
}
*/
