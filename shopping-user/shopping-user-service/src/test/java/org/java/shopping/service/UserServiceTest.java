package org.java.shopping.service;

import org.java.shopping.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.spring.annotation.MapperScan;

import static org.junit.jupiter.api.Assertions.*;

@MapperScan(basePackages = "org.java.shopping.mapper")
class UserServiceTest {



    @org.junit.jupiter.api.Test
    public void registerUser() {
        User user =new User();
        user.setUsername("zhangsan");
        user.setPassword("123456");
        user.setPhone("13345678888");
        UserService userService =new UserService();
        userService.registerUser(user);


    }


}