package com.his.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发送消息
 * Created by Administrator on 2018/1/16.
 */
public class Sender06 {

    public static void main(String[] args) throws IOException,TimeoutException{

        ConnectionFactory factory = new ConnectionFactory();

        //RabbitMQ-Server安装在本机，所以直接用127.0.0.1
        factory.setHost("127.0.0.1");

        //创建一个连接
        Connection conn = factory.newConnection() ;

        //创建一个渠道
        Channel channel = conn.createChannel() ;

        String exchangeName = "exchange03";

        String messageType = "fs.type01";
        channel.exchangeDeclare(exchangeName, "topic") ;

        //定义Queue名
        String msg = "Hello World!";

        //发送消息
        channel.basicPublish( exchangeName , messageType , null , msg.getBytes());

        System.out.println("send message[" + msg + "] to "+ exchangeName +" success!");

        channel.close();
        conn.close();
    }
}
