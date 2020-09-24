package com.hints.streamlicense.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MySink.class)

public class ReceiverMessageController {
    private static final Logger logger = LoggerFactory.getLogger(ReceiverMessageController.class);

    @StreamListener("inchannel")
    public void handle(String message) {
        logger.info("1Received a message of type " + message);
    }

    @StreamListener("inchannel")
    public void handle2(String message) {
        logger.info("2Received a message of type " + message);
    }
}
