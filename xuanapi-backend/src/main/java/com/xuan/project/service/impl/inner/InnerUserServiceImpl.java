package com.xuan.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuan.project.common.ErrorCode;
import com.xuan.project.exception.BusinessException;
import com.xuan.project.mapper.UserMapper;
import com.xuan.xuancommon.model.entity.User;
import com.xuan.xuancommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author kixuan
 * @version 1.0
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 找到accessKey
     *
     * @param accessKey
     * @return
     */
    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
