package net.medcrm.yjb.his.eureka.utils;

import net.medcrm.yjb.his.eureka.HisEurekaApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by linsheng on 2017/7/3.
 */
public class ServletInitializer extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HisEurekaApplication.class);
    }


}
