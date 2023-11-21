package com.xuan.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuan.project.common.ErrorCode;
import com.xuan.project.exception.BusinessException;
import com.xuan.project.mapper.InterfaceInfoMapper;
import com.xuan.project.service.InterfaceInfoService;
import com.xuan.xuancommon.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author 醒酒器
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2023-11-13 20:21:40
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        System.out.println("name = " + name);
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }

}




