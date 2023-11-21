package com.xuan.project.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;


    /**
     * 请求参数
     */
    private String requestParams;

    private static final long serialVersionUID = 1L;
}