package com.his.rabbitmq.controller;

import com.his.rabbitmq.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/16.
 */

@RequestMapping("/send")
@RestController
public class SendController {

    @Autowired
    SendService sendService;


    @GetMapping("/msg")
    public void send(){
        sendService.sendMsg("发送一条消息到消息队列！");
    }
}
