package com.example.xuanapiinterface;

import com.example.xuanapiinterface.client.XuanApiClient;
import com.example.xuanapiinterface.model.User;

/**
 * @author kixuan
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        String accesskey = "123";
        String secretkey = "123";
        XuanApiClient xuanApiClient = new XuanApiClient(accesskey, secretkey);
        String nameByGet = xuanApiClient.getNameByGet("kixuan");
        String nameByPost = xuanApiClient.getNameByPost("kixuan");

        User user = new User();
        user.setUsername("kixuan");
        String userNameByPost = xuanApiClient.getUserNameByPost(user);

        System.out.println(nameByGet);
        System.out.println("====================================");
        System.out.println(nameByPost);
        System.out.println("====================================");
        System.out.println(userNameByPost);
    }
}
