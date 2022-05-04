package org.java.cart.config;

import org.java.cart.Interceptor.CartInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器写好后，在此方法中启用
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    CartInterceptor cartInterceptor;

    //该方法表示所有请求都要进行拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cartInterceptor).addPathPatterns("/**");
    }
}
