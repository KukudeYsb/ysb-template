package com.authority;

import com.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;


/**
 * @author 杨世博
 */
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 忽略拦截的Url
     */
    private final List<String> urls = Arrays.asList(
            "/error",
            "/user/login",
            "/user/register",
            "/swagger-ui",
            "/swagger-resources",
            "/v3/api-docs",
            "/test/demo"
    );

    public Boolean staticResource(HttpServletRequest httpServletRequest, Object handler) {
        if (HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())) {
            //options请求.放行
            return true;
        }
        if (!(handler instanceof HandlerMethod)) {
            //不是映射到方法直接通过
            return true;
        }
        //不拦截的路径
        String uri = httpServletRequest.getRequestURI();
        for (String url : urls) {
            if (uri.contains(url)) {
                log.info("Releasable Path:" + url);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
