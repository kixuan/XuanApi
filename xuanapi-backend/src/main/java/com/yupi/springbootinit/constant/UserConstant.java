package com.yupi.springbootinit.constant;

/**
 * 用户常量
 */
// todo 这个和UserRoleEnum有什么区别啊
//     避免硬编码问题？
public interface UserConstant {

    /**
     * 用户登录态键
     * 一般是Attribution和session的时候用到
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限
    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";
}
