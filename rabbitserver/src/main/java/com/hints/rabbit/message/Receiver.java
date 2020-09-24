package com.hints.rabbit.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Receiver {
    @RabbitListener(queues = "topic.message")
    public void process1(String msg){
        log.info("Message:{}", msg);
    }

    @RabbitListener(queues = "topic.messages")
    public void process2(String msgs){
        log.info("Messages:{}", msgs);
    }
}
