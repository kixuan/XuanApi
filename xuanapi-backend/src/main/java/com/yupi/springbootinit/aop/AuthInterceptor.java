package com.yupi.springbootinit.aop;

import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.enums.UserRoleEnum;
import com.yupi.springbootinit.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验 AOP
 */
@Aspect  //作用是把当前类标识为一个切面供容器读取 和下面的@Around是一套的
@Order(1)  // 用来标识切面的优先级，值越小优先级越高
@Component  // 将该类交给 Spring 来管理
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    // TODO 有点没看懂、、、
    //      mustRole是根据AuthCheck确定的，所以要先判断 mustUserRoleEnum的值有哪些，然后再判断当前登录用户的角色是否在其中？
    @Around("@annotation(authCheck)")  //环绕增强，相当于MethodInterceptor
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 获取请求路径
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String mustRole = authCheck.mustRole();
        if (StringUtils.isBlank(mustRole)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        // 如果是被封号，则没有权限不能登录
        if (mustUserRoleEnum == null || UserRoleEnum.BAN.equals(mustUserRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 当前登录用户
        String userRole = userService.getLoginUser(request).getUserRole();
        if (!mustRole.equals(userRole)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

