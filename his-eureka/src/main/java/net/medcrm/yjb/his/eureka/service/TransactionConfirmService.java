package net.medcrm.yjb.his.eureka.service;

import net.medcrm.yjb.his.eureka.mq.model.TxGroup;

/**
 * Created by linsheng on 2017/6/9.
 */
public interface TransactionConfirmService {

    void confirm(TxGroup group);
}
