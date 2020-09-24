package com.hints.rabbit.message;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void send(){
        String content = "hello"+new Date();
        rabbitmqTemplate.convertAndSend("exchange","topic.message","hello, rabbit.");
    }

    public void send1(){
        String content = "hello"+new Date();
        rabbitmqTemplate.convertAndSend("exchange","topic.messages","hello, rabbit.");
    }
}
