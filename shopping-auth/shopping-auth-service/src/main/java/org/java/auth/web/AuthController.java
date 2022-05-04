package org.java.auth.web;

import org.apache.commons.lang.StringUtils;
import org.java.auth.config.JwtProperties;
import org.java.auth.pojo.UserInfo;
import org.java.auth.service.AuthService;
import org.java.auth.utils.JwtUtils;
import org.java.shopping.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * acrredit 授权
     * 通过调用UserService的Api对用户输入的账户密码做一个鉴权操作
     * 如果鉴权通过，则返回token存放至cookie交给客户端
     * @return
     * http://localhost:15000/auth/acrredit
     */
    @PostMapping("/acrredit")
    public ResponseEntity<Boolean> acrredit(@RequestParam("username") String username,
                                            @RequestParam("password") String password,
                                            HttpServletRequest request,
                                            HttpServletResponse response){

        //1.通过authSerivce对输入的username,password做一个鉴权,如果通过,则返回token
        String token = authService.acrredit(username,password);
        //2.authSerivce返回的结果如果为空，则说明用户输入的账户或密码有误
        if(token==null){
            return ResponseEntity.badRequest().build();
        }
        //3.如果通过了鉴定,则将token设置到cookie中
        CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,jwtProperties.getExpire()*60);
        //4.返回给客户端
        return ResponseEntity.ok().build();
    }


    /**
     * 当用户登录至任何页面时，检查用户的登陆状态，获取用户的Cookie来做判断
     * 如果从Cookie中获取到了合规的用户数据，则判断该用户已登录
     * @param token
     * @return
     */
    @GetMapping("/verify")
    public ResponseEntity<UserInfo> verify(@CookieValue(name="shopping-token")String token,
                                           HttpServletRequest request,
                                           HttpServletResponse response){

        //判断cookie是否为空
        if(StringUtils.isBlank(token)){
            //用户没有传递一个名为shopping-token的Cookie
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //使用公钥解析cookie判断数据是否合法
        try {
            //解析Cookie获得用户信息
            UserInfo userInfo=JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
            //用户信息验证通过，重新设置token有效时间
            token =JwtUtils.generateToken(userInfo,jwtProperties.getPrivateKey(),jwtProperties.getExpire()*60);
            //重新设置cookie的有效时间
            CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,jwtProperties.getExpire()*60);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            //如果解析失败则用户没有登录
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
