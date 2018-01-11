package com.his.manager;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("com.his")
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan("com.his.manager.mapper")
public class HisManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HisManagerApplication.class, args);
	}

}
