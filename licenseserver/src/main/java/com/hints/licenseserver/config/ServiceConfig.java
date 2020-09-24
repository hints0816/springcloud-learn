package com.hints.licenseserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ServiceConfig {
    @Value("${example.property}")
    private String exampleProperty;

    @GetMapping("/hints")
    public String getExampleProperty(){
        return "first port:"+exampleProperty;
    }
}