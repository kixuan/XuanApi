package com.example.xuanapiinterface.controller;


import com.example.xuanapiclientsdk.model.User;
import com.example.xuanapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kixuan
 * @version 1.0
 */

// 哇趣注意这里不用/name直接name
@RequestMapping("/name")
@RestController
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String username, HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.getHeader("xuan"));
        return "GET:" + username;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String username) {
        return "POST+URL：" + username;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest httpServletRequest) {
        // 不能直接获取secretKey，这样很容易被截胡到
        // String accessKey = httpServletRequest.getHeader("accessKey");
        // String secretKey = httpServletRequest.getHeader("secretKey");
        // String body = httpServletRequest.getHeader("body");
        // String nonce = httpServletRequest.getHeader("nonce");
        // String sign = httpServletRequest.getHeader("sign");
        // String timestamp = httpServletRequest.getHeader("timestamp");
        //
        // if (user == null || user.getUsername() == null) {
        //     // 处理空值情况，例如返回错误响应或抛出自定义异常
        //     System.out.println("==================user空值");
        // }
        //
        // if (!accessKey.equals("123")) {
        //     throw new RuntimeException("无权限");
        // }
        //
        // // 检测随机数（）正常来说应该是放进redis或者数据库中，这里简单处理
        // if (Integer.parseInt(nonce) > 10000) {
        //     throw new RuntimeException("随机数错误");
        // }
        //
        // // 检验时间戳与当前时间差距不能超过5分钟
        // if (Long.parseLong(timestamp) - System.currentTimeMillis() / 1000 > 5 * 60) {
        //     throw new RuntimeException("请求超时");
        // }
        //
        // // 这个时候再检验sign，从数据库中查出secretKey，并且和现在的进行比较
        // String serverSign = SignUtils.genSign(body, "123");
        // if (!sign.equals(serverSign)) {
        //     throw new RuntimeException("无权限");
        // }

        return "POST+JSON 用户名字是" + user.getUsername();
    }

}
