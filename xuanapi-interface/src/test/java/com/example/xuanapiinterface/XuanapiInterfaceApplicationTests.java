package com.example.xuanapiinterface;

import com.example.xuanapiclientsdk.client.XuanApiClient;
import com.example.xuanapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class XuanapiInterfaceApplicationTests {
    @Resource
    private XuanApiClient xuanApiClient;

    @Test
        // 测试这个前记得先运行xuanapi-interface项目
    void contextLoads() {
        // 1. GET
        String nameByGet = xuanApiClient.getNameByGet("xuanapi-GET");
        System.out.println(nameByGet);

        // 2.POST+URL
        String nameByPost = xuanApiClient.getNameByPost("xuanapi-POST1");
        System.out.println(nameByPost);

        //3.POST+JSON
        User user = new User();
        user.setUsername("xuanzai-POST2");
        String userNameByPost = xuanApiClient.getUserNameByPost(user);
        System.out.println(userNameByPost);
    }

}
