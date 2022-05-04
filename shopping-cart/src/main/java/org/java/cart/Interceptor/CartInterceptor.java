package org.java.cart.Interceptor;

import org.java.auth.pojo.UserInfo;
import org.java.auth.utils.JwtUtils;
import org.java.cart.config.JwtProperties;
import org.java.shopping.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**

 * 拦截器方法，在方法进入到控制器之前，先对其进行拦截
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class CartInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    JwtProperties jwtProperties;

    //线程变量，用于储存数据，在一个请求(线程)中生效
    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();
    //前置拦截器，在请求进入控制器之前进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从request中获取token
        String token = CookieUtils.getCookieValue(request,jwtProperties.getCookieName());
        //解密token获取userinfo
        UserInfo userInfo = JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
        //如果userinfo为空，则不通过，返回false，不进入控制器
        if(userInfo==null){
            return false;
        }
        //如果不为空，则将userinfo存入LOCAL_THREAD
        THREAD_LOCAL.set(userInfo);
        return super.preHandle(request, response, handler);
    }
    //后置拦截器，在离开控制器后运行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
    //请求即将结束时运行，一般用于释放资源
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /**
         * 一般情况下，线程变量会自动释放，但是在结束的时候
         * 但我们的线程允许在tomcat容器中，线程不会结束，因此，需要手动进行释放
         */
       THREAD_LOCAL.remove();//释放线程变量中的资源
        super.afterCompletion(request, response, handler, ex);
    }

    //用于获取线程变量中UserInfo的方法
    public static UserInfo getUserInfoFromThreadLocal(){

        return THREAD_LOCAL.get();
    }
}
