package com.xuan.project.service.impl.inner;

import com.xuan.project.service.UserInterfaceInfoService;
import com.xuan.xuancommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author kixuan
 * @version 1.0
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 调用成功，次数+1
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 调用注入的 UserInterfaceInfoService 的 invokeCount 方法
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
