package com.xuan.project.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL 工具
 */
public class SqlUtils {

    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     *
     * @param sortField
     * @return
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");

        //  更多防止sql注入方法
        //  定义一个排序字段白名单，只允许合法的排序字段
        // List<String> allowedSortFields = Arrays.asList("name", "date", "price");
        //
        // // 检查排序字段是否在白名单中
        // if (!allowedSortFields.contains(sortField)) {
        //     return false;
        // }
    }
}
