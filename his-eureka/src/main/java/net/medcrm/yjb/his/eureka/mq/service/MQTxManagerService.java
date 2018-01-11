package net.medcrm.yjb.his.eureka.mq.service;

import net.medcrm.yjb.his.eureka.mq.model.TxGroup;

/**
 * Created by linsheng on 2017/6/7.
 */
public interface MQTxManagerService {



    /**
     * 创建事物组
     */
    TxGroup createTransactionGroup();


    /**
     * 添加事务组子对象
     *
     * @return
     */
    TxGroup addTransactionGroup(String groupId, String uniqueKey, String taskId, int isGroup, String modelName);


    int checkTransactionGroup(String groupId, String taskId);


    boolean closeTransactionGroup(String groupId, int state);



    int getDelayTime();

}
