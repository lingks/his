package com.his.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/1/15.
 */
public class Producer1 extends EndPoint {

    public Producer1(String endPointName) throws IOException, TimeoutException{

        super(endPointName);
    }

    public void sendMessage(byte[] msg) throws IOException {

        channel.basicPublish("",endPointName, null, msg);

    }
}
