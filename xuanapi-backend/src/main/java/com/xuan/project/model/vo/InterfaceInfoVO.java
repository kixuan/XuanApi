package com.xuan.project.model.vo;

import com.xuan.xuancommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 接口信息封装视图
 *
 * @author kixuan
 * @version 1.0
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {
    /**
     * 调用次数
     */

    private Integer totalNum;

    private static final long serialVersionUID = 1L;

}
