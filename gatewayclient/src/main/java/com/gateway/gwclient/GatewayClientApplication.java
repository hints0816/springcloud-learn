package com.gateway.gwclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayClientApplication {

    @Value("${test.uri}")
    private String uri;

    /*这是一个路由转发*/
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                //basic proxy
//                .route(r -> r.path("/order/**")
//                        .filters(f -> f.addRequestHeader("NAME", "World"))
//                        .uri(uri)
//                )
//                .route(r -> r.path("/user/**")
//                        .filters(f -> f
//                                .hystrix(config -> config
//                                        .setName("myserviceOne")
//                                        .setFallbackUri("forward:/user/fallback")))
//                        .uri(uri)).build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayClientApplication.class, args);
    }
}