package com.hints.serverone.controller;//package service.controller;


import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiveController {
    @StreamListener("message")
    public void onReceive(String messageInfo) {
        System.out.println("接受到的消息3：" + messageInfo);
    }
}
