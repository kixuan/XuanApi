package com.xuan.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuan.project.common.ErrorCode;
import com.xuan.project.exception.BusinessException;
import com.xuan.project.mapper.UserInterfaceInfoMapper;
import com.xuan.project.model.entity.UserInterfaceInfo;
import com.xuan.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 醒酒器
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2023-11-17 12:15:28
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userUserInterfaceInfo, boolean add) {
        if (userUserInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (add) {
            if (userUserInterfaceInfo.getInterfaceInfoId() < 0 || userUserInterfaceInfo.getUserId() < 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (userUserInterfaceInfo.getLeftNum() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 更新剩余次数和总次数
        // todo 逻辑待完善，如加锁等
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<UserInterfaceInfo>()
                .setSql("leftNum = leftNum - 1")
                .setSql("totalNum = totalNum + 1")
                .eq("interfaceInfoId", interfaceInfoId)
                .eq("userId", userId);

        return this.update(updateWrapper);
    }
}




