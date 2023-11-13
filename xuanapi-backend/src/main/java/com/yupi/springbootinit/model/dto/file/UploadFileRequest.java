package com.yupi.springbootinit.model.dto.file;

import java.io.Serializable;

import lombok.Data;

/**
 * 文件上传请求
 */
@Data
public class UploadFileRequest implements Serializable {

    /**
     * 业务  --  这里的话目前只有用户头像:user_avatar
     */
    private String biz;

    private static final long serialVersionUID = 1L;
}