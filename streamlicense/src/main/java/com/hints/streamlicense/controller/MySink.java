package com.hints.streamlicense.controller;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {
    String INPUT = "inchannel";

    @Input(INPUT)
    SubscribableChannel input();
}
