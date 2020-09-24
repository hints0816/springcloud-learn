package com.hints.rabbit.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    //1: 初始化消息队列topic.message
    @Bean(name = "message")
    public Queue queueMessage(){
        return new Queue("topic.message");
    }

    //2: 初始化消息队列topic.messages
    @Bean(name = "messages")
    public Queue queueMessages(){
        return new Queue("topic.messages");
    }

    //3: 配置交换机
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("exchange");
    }

    //绑定消息到交换机上, 需要匹配topic.message
    @Bean
    Binding bindingExchangeMessage(@Qualifier("message") Queue queueMessage, TopicExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    //绑定消息到交换机上, 需要匹配topic即可
    @Bean
    Binding bindingExchangeMessages(@Qualifier("messages") Queue queueMessages, TopicExchange exchange){
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }
}
