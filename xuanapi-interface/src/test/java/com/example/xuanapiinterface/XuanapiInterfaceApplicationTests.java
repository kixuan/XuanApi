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
        String result = xuanApiClient.getNameByGet("xuanapi");
        User user = new User();
        user.setUsername("xuanzai");
        // 调用xuanApiClient的getUserNameByPost方法，并传入user对象作为参数，将返回的结果赋值给usernameByPost变量
        String usernameByPost = xuanApiClient.getUserNameByPost(user);
        System.out.println(result);
        System.out.println(usernameByPost);
    }

}
