package net.medcrm.yjb.his.eureka.mq.service.impl;

import net.medcrm.yjb.his.eureka.mq.model.TxGroup;
import net.medcrm.yjb.his.eureka.mq.service.MQTxManagerService;
import net.medcrm.yjb.his.eureka.service.TxManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by linsheng on 2017/6/7.
 */
@Service
public class MQTxManagerServiceImpl implements MQTxManagerService {


    @Autowired
    private TxManagerService txManagerService;


    @Override
    public TxGroup createTransactionGroup() {
        return txManagerService.createTransactionGroup();
    }

    @Override
    public TxGroup addTransactionGroup(String groupId,String uniqueKey, String taskId,int isGroup, String modelName) {
        return txManagerService.addTransactionGroup(groupId,uniqueKey, taskId,isGroup, modelName);
    }

    @Override
    public boolean closeTransactionGroup(String groupId,int state) {
        return txManagerService.closeTransactionGroup(groupId,state);
    }

    @Override
    public int checkTransactionGroup(String groupId, String taskId) {
        return txManagerService.checkTransactionGroup(groupId, taskId);
    }


    @Override
    public int getDelayTime() {
        return txManagerService.getDelayTime();
    }
}
