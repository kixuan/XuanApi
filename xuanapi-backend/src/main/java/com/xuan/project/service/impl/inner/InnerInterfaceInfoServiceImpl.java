package com.xuan.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuan.project.common.ErrorCode;
import com.xuan.project.exception.BusinessException;
import com.xuan.project.mapper.InterfaceInfoMapper;
import com.xuan.xuancommon.model.entity.InterfaceInfo;
import com.xuan.xuancommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author kixuan
 * @version 1.0
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {


    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 根据请求路径和方法名获取接口信息
     *
     * @param path
     * @param method
     * @return
     */

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if (StringUtils.isAnyBlank(path, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", path);
        queryWrapper.eq("method", method);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
