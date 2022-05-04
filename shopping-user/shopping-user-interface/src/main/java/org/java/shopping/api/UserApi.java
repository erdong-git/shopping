package org.java.shopping.api;


import org.java.shopping.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface UserApi {

    @PostMapping("user/register")
    public Void registerUser(@Valid User user);

    @GetMapping("user/checkData")
    public Boolean checkUserData(@RequestParam("data") String data,
                                                 @RequestParam("type") Integer type);

    @GetMapping("user/query")
    public User queryUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password);
}
