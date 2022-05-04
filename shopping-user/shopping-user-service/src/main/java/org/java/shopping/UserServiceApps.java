package org.java.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
//将mapper扫描添加
@MapperScan(basePackages = "org.java.shopping.mapper")
public class UserServiceApps {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApps.class);
    }
}
