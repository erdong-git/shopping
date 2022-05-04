package org.java.shopping.web;

import org.java.shopping.entity.User;
import org.java.shopping.enums.ShoppingExceptionEnum;
import org.java.shopping.exception.ShoppingException;
import org.java.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
/**
 * 用户中心的控制器类
 */
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param user
     * @return
     * 直接访问地址：http://localhost:14000/user/register?xx=xx
     * 网关访问地址(restful风格)：http://api.shopping.com/user/register/xxxx
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid User user){

        //创建用户接收是否创建成功
        boolean flag =userService.registerUser(user);
        //如果返回false说明返回没有成功,则返回badRequest
        if(flag==false){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        System.out.println("用户已创建，用户名是"+user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     *
     * @param data 用户的数据
     * @param type 用户检查的类型
     * 直接访问地址：http://localhost:14000/user/checkData?xx=xx
     * 网关访问地址(restful风格)：http://api.shopping.com/user/checkData/xxxx
     * @return
     */
    @GetMapping("/checkData")
    public ResponseEntity<Boolean> checkUserData(@RequestParam("data") String data,
                                                 @RequestParam("type") Integer type){
        //查询数据库返回结果
        Boolean flag =userService.checkUserNameAndPhone(data,type);
        //如果返回为false则返回badRequest
        if(flag==false){
            System.out.println("用户已存在");
            return ResponseEntity.badRequest().build();


        }
        System.out.println("用户可以注册");
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }

    /**
     * 根据用户输入的用户密码查询用户信息
     * @param username
     * @param password
     * 直接访问地址：http://localhost:14000/user/query?xx=xx
     * 网关访问地址(restful风格)：http://api.shopping.com/user/query/xxxx
     * @return
     */
    @GetMapping("/query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password){

        User user =userService.queryUser(username,password);
        //如果为空，则该用户不存在，返回badRequest
        if(user==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        System.out.println(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
