package com.his.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * Created by Administrator on 2018/1/15.
 */
public abstract class EndPoint {

    protected Channel channel;
    protected Connection connection;
    protected String endPointName;

    public EndPoint(String endPointName)  throws IOException,TimeoutException{
        this.endPointName = endPointName;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        connection = factory.newConnection();

        channel = connection.createChannel();
        channel.queueDeclare(endPointName,false,false,false, null);
    }

    /**

     * 关闭channel和connection。并非必须，因为隐含是自动调用的。

     * @throws IOException

     */

    public void close() throws IOException,TimeoutException{

        this.channel.close();

        this.connection.close();

    }
}
