package com.xuan.xuancommon.service;


import com.xuan.xuancommon.model.entity.InterfaceInfo;

/**
 * @author 醒酒器
 * @description 针对表【interface_info(接口信息)】的数据库操作Service
 * @createDate 2023-11-13 20:21:40
 */
public interface InnerInterfaceInfoService  {

    /**
     * 从数据库中查询模拟接口是否存interface在（请求路径、请求方法、请求参数）
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
