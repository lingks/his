package net.medcrm.yjb.his;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */

@Configuration
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigServer
public class HisConfigCenterApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(HisConfigCenterApplication.class,args);
    }
}
