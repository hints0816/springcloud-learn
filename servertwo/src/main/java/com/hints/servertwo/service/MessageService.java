package com.hints.servertwo.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {
    @Output(value = "message")
    SubscribableChannel getOutput();
}
