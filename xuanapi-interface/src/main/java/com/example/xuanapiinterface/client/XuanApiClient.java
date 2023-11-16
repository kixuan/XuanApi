package com.example.xuanapiinterface.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.xuanapiinterface.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kixuan
 * @version 1.0
 */
public class XuanApiClient {

    private String accessKey;
    private String secretKey;

    public XuanApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * @return
     */
    private Map<String, String> getHeaderMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        hashMap.put("secretKey", secretKey);
        return hashMap;
    }

    public String getNameByGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get("http://localhost:8123/api/name/’", paramMap);
        System.out.println("GET:" + result);
        return result;

    }

    public String getNameByPost(@RequestParam String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println("POST+URL:" + result);
        return result;
    }

    public String getUserNameByPost(@RequestBody User user) {

        String json = JSONUtil.toJsonStr(user);
        String url = "http://localhost:8123/api/name/user";
        HttpResponse httpResponse = HttpRequest.post(url)
                // 构建请求头
                .addHeaders(getHeaderMap())
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println("POST+JSON:" + result);
        return result;
    }
}
