package org.java.auth;


import org.java.auth.config.JwtProperties;
import org.java.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class AuthServiceApps {



    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApps.class);
    }
}
