package com.yupi.springbootinit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验
 */
@Target(ElementType.METHOD)   // 说明该注解只能声明在一个类的方法前
@Retention(RetentionPolicy.RUNTIME) // 说明该注解在运行时是可见的，可以通过反射来访问。
// 反射访问：将保留策略设置为 RUNTIME 允许你使用反射来检查方法上是否存在 @AuthCheck 注解，以及注解中的属性值。这对于在运行时基于注解来实现权限控制非常重要。
// 配置灵活性：通过在运行时访问注解，你可以更灵活地配置权限校验规则，而不必重新编译代码。这使得在不同环境或不同应用程序部署中调整权限要求更加容易。
public @interface AuthCheck {

    /**
     * 必须有某个角色
     */
    String mustRole() default "";

}

