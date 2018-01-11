package com.his.consul;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ConsulApplication {

    @RequestMapping("/hi")
    public String hi() {
        return "hello ConsulServer ";
    }

    @RequestMapping("/home")
    public Object home() {
        System.out.println("2222222222222222");
        return "OK22";
    }

    //健康检查的实现REST部分
    @RequestMapping("/health")
    public String health() {
        return "hello health ";
    }

    public static void main( String[] args ) {
        SpringApplication.run(ConsulApplication.class, args);
    }
}