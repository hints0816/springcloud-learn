package com.hints.servertwo;

import com.hints.servertwo.service.MessageService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

@SpringBootApplication
@MapperScan("com.hints.servertwo.dao")
@EnableBinding(MessageService.class)


@EnableEurekaClient
public class ServiceTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTwoApplication.class, args);
    }

    private DiscoveryClient discoveryClient;

    // 得到DiscoveryClient
    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @RequestMapping(value = "/discovery", method = RequestMethod.GET)
    public Object discovery() {
        // 获取服务名称列表
        List<String> names = discoveryClient.getServices();
        System.out.println("*******************" + names);
        StringBuilder builder = new StringBuilder();
        for (String name : names) {
            // 根据服务名称获取服务实例列表,可能是多个实例.
            List<ServiceInstance> instances = discoveryClient.getInstances(name);
            for (ServiceInstance instance : instances) {
                String info = instance.getServiceId() + "，" +
                        instance.getHost() + "，" +
                        instance.getPort() + "，" +
                        instance.getUri() + "<br/>";
                System.out.println(info);
                builder.append(info);
            }
        }

        return builder.toString();
    }
}