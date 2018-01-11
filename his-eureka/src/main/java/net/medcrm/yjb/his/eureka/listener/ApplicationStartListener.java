package net.medcrm.yjb.his.eureka.listener;

import net.medcrm.yjb.his.eureka.utils.Constants;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * create by linsheng on 2017/8/7
 */
@Component
public class ApplicationStartListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {


    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        int serverPort = event.getEmbeddedServletContainer().getPort();
        String ip = getIp();
        Constants.address = ip+":"+serverPort;
    }



    private String getIp(){
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }
}
