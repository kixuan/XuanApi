package com.xuan.springbootinit.exception;

import com.xuan.springbootinit.common.ErrorCode;

/**
 * 抛异常工具类
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param runtimeException
     */
    // 这里用到了什么设计模式
    // 答：策略模式
    // 策略模式的定义：定义一系列的算法，把它们一个个封装起来，并且使它们可以相互替换。
    // 该模式使得算法可独立于使用它的客户而变化。
    // 在这里的体现是：把抛异常的策略封装起来，使得它们可以相互替换。
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     * @param message
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
