package com.hints.serverone.export_api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "eurka-serverB",fallback = SchedualServiceHiHystric.class)

public interface FeignToGetUser {

    @GetMapping("user/{id}")
    public String getUser(@PathVariable("id") String id);

    @GetMapping("/user/insert")
    public void insertUser();
}
