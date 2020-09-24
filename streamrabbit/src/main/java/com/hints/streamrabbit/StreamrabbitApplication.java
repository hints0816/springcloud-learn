package com.hints.streamrabbit;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class StreamrabbitApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamrabbitApplication.class,args);
    }
//    private static Logger logger = LoggerFactory.getLogger(StreamrabbitApplication.class);
//
//    @StreamListener(Sink.INPUT)
//    public void receive(Object payload) {
//        logger.info("Received: " + payload);
//    }
}
