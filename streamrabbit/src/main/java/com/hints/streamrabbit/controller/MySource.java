package com.hints.streamrabbit.controller;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

public interface MySource {
    String OUTPUT = "outchannel";

    @Output(OUTPUT)
    MessageChannel output();
}
