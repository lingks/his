package net.medcrm.yjb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class App 
{
    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private MyFeignClient feignClient;
    /**
     * 从所有服务中选择一个服务（轮询）
     */
    @RequestMapping("/discover")
    public Object discover() {
        return loadBalancer.choose("consul-server").getUri().toString();
    }

    /**
     * 获取所有服务
     */
    @RequestMapping("/services")
    public Object services() {
        return discoveryClient.getInstances("consul-server");
    }

    /**
     * 获取feignClient服务
     */
    @RequestMapping("/hi")
    public Object hi() {
        System.out.println(feignClient.home());
        return feignClient.hi();
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
