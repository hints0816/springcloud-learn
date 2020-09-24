package com.hints.serverone.export_api;

import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiHystric implements FeignToGetUser {

    @Override
    public String getUser(String id) {
        return "sorry "+id;
    }

    @Override
    public void insertUser() {
        System.out.println("sorry");
    }
}
