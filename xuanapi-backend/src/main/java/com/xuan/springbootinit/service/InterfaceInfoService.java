package com.xuan.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuan.springbootinit.model.entity.InterfaceInfo;

/**
 * @author 醒酒器
 * @description 针对表【interface_info(接口信息)】的数据库操作Service
 * @createDate 2023-11-13 20:21:40
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {


    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
