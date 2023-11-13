package com.yupi.springbootinit.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 */
// 有点像是一个DTO，但是只有一个id，所以不用写DTO
// 就综测系统的时候都是单独写一个deleteParam，然后里面只有一个id，和这个就是一样的啦
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}