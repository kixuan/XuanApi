package com.xuan.springbootinit.model.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口信息状态枚举
 *
 * @author kixuan
 * @version 1.0
 */

@Getter
public enum InterfaceInfoStatusEnum {

    OFFLINE("关闭", 0),
    ONLINE("上线", 1);

    private final String text;
    private final String value;

    InterfaceInfoStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value.toString();
    }

    /**
     * 枚举类型中的所有枚举值的 value 属性提取出来，并以字符串列表的形式返回
     */
    public static List<String> getValues() {
        // values() 方法返回枚举类中所有的枚举值【枚举类型自动生成的方法】
        // Arrays.stream(values()) 将这个枚举值数组转换成一个流（Stream）对象
        // map(item -> item.value) 将流中的每个枚举值映射成枚举值的 value 属性
        // collect(Collectors.toList()) 将流中的每个枚举值映射成枚举值的 value 属性
        // toList() 表示将流中的元素收集到一个列表（List）中，最终生成一个 List<String> 对象
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }


    /**
     * 根据 value 获取枚举
     */
    public static InterfaceInfoStatusEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (InterfaceInfoStatusEnum anEnum : InterfaceInfoStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
