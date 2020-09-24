package com.hints.servertwo.controller;

import com.hints.servertwo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService sendMessageService;

    @RequestMapping("/send")
    public String sendMessage(String messageInfo) {
        sendMessageService.getOutput()
                .send(MessageBuilder.withPayload(messageInfo).build());
        return "success";
    }
}
