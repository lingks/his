package com.his.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by lings on 2017/10/26.
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * 使用enableMVC注解的话,该配置必须,否则无法映射资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()                                //com.his.foundation.controller
                .apis(RequestHandlerSelectors.basePackage("com.his.manager.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("访问控制管理的RESTful APIs")
                .description("医极棒管理系统")
                .contact("林盛")
                .version("1.0")
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        System.out.println("=============================Access-Control-Allow-Origin=================================");
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(
                        "GET",
                        "POST",
                        "HEAD",
                        "OPTIONS",
                        "PUT",
                        "DELETE")
                .allowedHeaders(
                        "Content-Type",
                        "X-Requested-With",
                        "accept",
                        "Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers",
                        "Authorization").allowCredentials(true);
        System.out.println("============================Access-Control-Allow-Origin==================================");
    }
}
