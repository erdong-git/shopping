package org.java.shopping.fliter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.java.auth.utils.JwtUtils;
import org.java.shopping.config.JwtProperties;
import org.java.shopping.config.LoginProperties;
import org.java.shopping.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

//添加该注解，该过滤器才会被注入Web
@Component
@EnableConfigurationProperties({JwtProperties.class, LoginProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    LoginProperties loginProperties;


    //设置该过滤器是属于前置过滤，后置过滤，还是错误过滤
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    //表示该过滤器的优先级，数字越小优先级越高
    @Override
    public int filterOrder() {
        return 10;
    }

    /**
     * 在此处过滤不必登录也可也访问的微服务的请求
     * true:进入
     * false：不进入
     * @return
     */
    @Override
    public boolean shouldFilter() {

        //获取上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        //获得request对象
        HttpServletRequest request = context.getRequest();
        //获得URL
        String path =request.getRequestURL().toString();
        //得到白名单集合
        List<String> allowPaths = loginProperties.getAllowPaths();
        //检查Url是否包含白名单
        for (String val:allowPaths){
            //该方法检查A参中是否包含B参，如果包含则返回true
            if(StringUtils.contains(path,val)){
                    return true;
            }
        }
        //不包含则进入Run方法，做登录判定
        return false;
    }

    /**
     * 过滤器的Run()方法，当有请求访问需要登录的微服务时，进入此方法，判断用户的登录状态
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run(){
        //获取上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        //获得request对象
        HttpServletRequest request = context.getRequest();
        //使用CookieUtils工具从Request中获取token
        String token = CookieUtils.getCookieValue(request,jwtProperties.getCookieName());
        //产生一个响应对象response，用于进行重定向
        HttpServletResponse response = context.getResponse();
        //如果token为空,则用户没有登录，跳转到登录页面
        if(StringUtils.isBlank(token)){
            //该方法表示请求不再继续向下执行，不再进行路由
            context.setSendZuulResponse(false);
            //当前用户未通过身份验证
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            //重定向到登录页面
//            try {
//                response.sendRedirect("http://www.shopping.com/login");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        //用公钥解析token
        try {
            JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
        } catch (Exception e) {
            //如果产生异常，表示解析不通过，不再继续路由
            e.printStackTrace();
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        //没有异常则用户通过解析，继续向下执行
        return null;
    }
}
