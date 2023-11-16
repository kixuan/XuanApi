package com.example.xuanapiinterface.controller;

import com.example.xuanapiinterface.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kixuan
 * @version 1.0
 */

// 哇趣注意这里不用/name直接name
@RequestMapping("name")
@RestController
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String username) {


        return "get:" + username;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String username) {
        return "post+url" + username;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest httpServletRequest) {
        String accessKey = httpServletRequest.getHeader("accessKey");
        String secretKey = httpServletRequest.getHeader("secretKey");
        if (!accessKey.equals("asd") || !secretKey.equals("123")) {
            throw new RuntimeException("无权限");
        }
        String username = user.getUsername();
        return "post+json" + username;
    }

}
