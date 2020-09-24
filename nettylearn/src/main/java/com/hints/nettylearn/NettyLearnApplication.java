package com.hints.nettylearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@SpringBootApplication
@EnableEurekaClient
public class NettyLearnApplication {
    public static void main(String[] args) {
            SpringApplication.run(NettyLearnApplication.class, args);
        }
}
