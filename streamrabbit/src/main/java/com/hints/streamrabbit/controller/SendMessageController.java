package com.hints.streamrabbit.controller;

import com.hints.streamrabbit.StreamrabbitApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
@RestController
@EnableBinding(MySource.class)
public class SendMessageController {
    private static Logger logger = LoggerFactory.getLogger(StreamrabbitApplication.class);
    @Autowired
    private MySource mySource;

    @GetMapping("testMyStream")
    public void contextLoads() throws InterruptedException {
        for(int i=0;i<100;i++) {
            String message = "now" + new Date();
            logger.info("Send a message of type " + message);
            Thread.sleep(10);
            mySource.output().send(MessageBuilder.withPayload(message).build());
        }
    }
}