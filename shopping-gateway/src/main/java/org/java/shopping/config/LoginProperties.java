package org.java.shopping.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 该类表示哪些路由地址不需要做用户登录判断
 * 在用户名创建时，应禁止用户使用 “/”
 */
@ConfigurationProperties("shopping.filter")
public class LoginProperties {


    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }

    List<String> allowPaths;
}
