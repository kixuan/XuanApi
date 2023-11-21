package com.xuan.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuan.xuancommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
 * @author 醒酒器
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
 * @createDate 2023-11-17 12:15:28
 * @Entity com.xuan.springbootinit.model.entity.UserInterfaceInfo
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}




