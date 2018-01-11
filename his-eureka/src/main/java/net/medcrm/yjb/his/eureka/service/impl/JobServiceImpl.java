package net.medcrm.yjb.his.eureka.service.impl;

import net.medcrm.yjb.his.eureka.service.JobService;
import net.medcrm.yjb.his.eureka.service.TxManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by linsheng on 2017/8/8
 */
@Service
public class JobServiceImpl implements JobService{

    @Autowired
    private TxManagerService txManagerService;

    @Override
    public void clearNotifyData() {

        new Thread(){
            @Override
            public void run() {

                int time = 5;
                while(true){
                    try {
                        Thread.sleep(1000*60*time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    txManagerService.clearNotifyData(time);
                }
            }
        }.start();

    }
}
