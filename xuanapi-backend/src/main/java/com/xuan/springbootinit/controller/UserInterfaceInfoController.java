package com.xuan.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuan.springbootinit.annotation.AuthCheck;
import com.xuan.springbootinit.common.BaseResponse;
import com.xuan.springbootinit.common.DeleteRequest;
import com.xuan.springbootinit.common.ErrorCode;
import com.xuan.springbootinit.common.ResultUtils;
import com.xuan.springbootinit.constant.CommonConstant;
import com.xuan.springbootinit.constant.UserConstant;
import com.xuan.springbootinit.exception.BusinessException;
import com.xuan.springbootinit.exception.ThrowUtils;
import com.xuan.springbootinit.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.xuan.springbootinit.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.xuan.springbootinit.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.xuan.springbootinit.model.entity.User;
import com.xuan.springbootinit.model.entity.UserInterfaceInfo;
import com.xuan.springbootinit.service.UserInterfaceInfoService;
import com.xuan.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 */
@RestController
@RequestMapping("/UserInterfaceInfo")
@Slf4j
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userUserInterfaceInfoService;

    @Resource
    private UserService userService;


    /**
     * 创建
     *
     * @param interfaceAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUserInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest interfaceAddRequest, HttpServletRequest request) {
        if (interfaceAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userUserInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(interfaceAddRequest, userUserInterfaceInfo);
        userUserInterfaceInfoService.validUserInterfaceInfo(userUserInterfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        userUserInterfaceInfo.setUserId(loginUser.getId());
        boolean result = userUserInterfaceInfoService.save(userUserInterfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newUserInterfaceInfoId = userUserInterfaceInfo.getId();
        return ResultUtils.success(newUserInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUserInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userUserInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldUserInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = userUserInterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param interfaceUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUserInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest interfaceUpdateRequest) {
        if (interfaceUpdateRequest == null || interfaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userUserInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(interfaceUpdateRequest, userUserInterfaceInfo);
        // 参数校验
        userUserInterfaceInfoService.validUserInterfaceInfo(userUserInterfaceInfo, false);
        long id = interfaceUpdateRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userUserInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldUserInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = userUserInterfaceInfoService.updateById(userUserInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<UserInterfaceInfo> getUserInterfaceInfoVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userUserInterfaceInfo = userUserInterfaceInfoService.getById(id);
        if (userUserInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(userUserInterfaceInfoService.getById(id));
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param userUserInterfaceInfoQueryRequest
     * @return
     */
    @GetMapping("/list")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<UserInterfaceInfo>> listUserInterfaceInfo(UserInterfaceInfoQueryRequest userUserInterfaceInfoQueryRequest) {
        UserInterfaceInfo userUserInterfaceInfoQuery = new UserInterfaceInfo();
        if (userUserInterfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(userUserInterfaceInfoQueryRequest, userUserInterfaceInfoQuery);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userUserInterfaceInfoQuery);
        List<UserInterfaceInfo> userUserInterfaceInfoList = userUserInterfaceInfoService.list(queryWrapper);
        return ResultUtils.success(userUserInterfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param userUserInterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserInterfaceInfo>> listUserInterfaceInfoByPage(UserInterfaceInfoQueryRequest userUserInterfaceInfoQueryRequest, HttpServletRequest request) {
        if (userUserInterfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userUserInterfaceInfoQuery = new UserInterfaceInfo();
        BeanUtils.copyProperties(userUserInterfaceInfoQueryRequest, userUserInterfaceInfoQuery);
        long current = userUserInterfaceInfoQueryRequest.getCurrent();
        long size = userUserInterfaceInfoQueryRequest.getPageSize();
        String sortField = userUserInterfaceInfoQueryRequest.getSortField();
        String sortOrder = userUserInterfaceInfoQueryRequest.getSortOrder();

        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userUserInterfaceInfoQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<UserInterfaceInfo> userUserInterfaceInfoPage = userUserInterfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(userUserInterfaceInfoPage);
    }

}