package net.medcrm.yjb;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/12/28.
 */
//注明服务端服务的名称，这种调用就是注册的服务，感觉上更像是httpclient通过接口，通过URL的方式进行注册的
@FeignClient(name = "consul-server")
public interface MyFeignClient {
    /**
     * value的值就是服务端定义的接口的名字
     * method也是服务端定义的方法请求的类型
     * String test1()这个方法名称的名字无所谓
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    String hi();

    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public Object home();
}
