package net.medcrm.yjb.his.eureka.service;

import net.medcrm.yjb.his.eureka.service.model.TxServer;
import net.medcrm.yjb.his.eureka.service.model.TxState;

/**
 * Created by linsheng on 2017/7/1.
 */
public interface TxService {

    TxServer getServer();

    TxState getState();

    String sendMsg(String model, String msg);

    boolean checkClearGroup(String groupId, String taskId, int isGroup);

    int checkGroup(String groupId, String taskId);
}
