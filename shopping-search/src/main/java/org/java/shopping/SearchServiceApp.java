package org.java.shopping;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients //启用Feign，它可以扫描所有@FeignClient组件
@EnableDiscoveryClient
@SpringBootApplication
public class SearchServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApp.class);
    }
}
