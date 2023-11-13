package com.yupi.springbootinit.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 请求响应日志 AOP
 **/
@Aspect
@Component
@Slf4j
public class LogInterceptor {

    /**
     * 执行拦截
     */
    // TODO 运行的时候看看效果
    @Around("execution(* com.yupi.springbootinit.controller.*.*(..))")//execution是路径拦截，第一个*是返回值，第二个*是类名，第三个*是方法名，(..)是参数
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        // StopWatch：测量运行时间
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 获取请求路径
        // RequestContextHolder --> RequestAttributes --> ServletRequestAttributes --> HttpServletRequest -->具体参数
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        String url = httpServletRequest.getRequestURI();
        // 获取请求参数
        Object[] args = point.getArgs();
        String reqParam = "[" + StringUtils.join(args, ", ") + "]";

        // 输出请求日志
        // 生成请求唯一 id
        String requestId = UUID.randomUUID().toString();
        log.info("request start，id: {}, path: {}, ip: {}, params: {}", requestId, url, httpServletRequest.getRemoteHost(), reqParam);
        // 执行原方法
        Object result = point.proceed();
        // 输出响应日志
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("request end, id: {}, cost: {}ms", requestId, totalTimeMillis);
        return result;
    }
}

