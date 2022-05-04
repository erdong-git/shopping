package org.java.auth.service;

import org.java.auth.config.JwtProperties;
import org.java.auth.feign.UserClient;
import org.java.auth.pojo.UserInfo;
import org.java.auth.utils.JwtUtils;
import org.java.shopping.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;


@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 控制器传递来的参数，通过Feign调用UserService的方法，来进行验证
     * @param username
     * @param password
     * @return
     */
    public String acrredit(String username,String password){

        //检查用户名与密码是否正确
        User user=userClient.queryUser(username,password);
        //如果为空，则表示用户名与密码不正确，返回不通过的信息
        if(user==null){
            return  null;
        }
        //验证通过，取到用户信息，生成Userinfo对象
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        String token = null;
        //通过用户信息生成token
        try {
            token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire() * 60);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //将token返回
        return token;

    }
}
