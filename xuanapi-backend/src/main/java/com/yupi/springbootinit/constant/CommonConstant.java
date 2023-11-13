package com.yupi.springbootinit.constant;

/**
 * 通用常量
 */
// eee这也太通用了吧，为什么不直接放在pageRequest里面
// ①其他地方可能还会用到
// ②放在常量里里面避免硬编码问题
public interface CommonConstant {

    /**
     * 升序
     */
    String SORT_ORDER_ASC = "ascend";

    /**
     * 降序
     */
    String SORT_ORDER_DESC = " descend";

}
