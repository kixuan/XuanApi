package com.xuan.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuan.project.model.entity.UserInterfaceInfo;

/**
 * @author 醒酒器
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
 * @createDate 2023-11-17 12:15:28
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userUserInterfaceInfo, boolean b);

    boolean invokeCount(long interfaceInfoId, long userId);
}
