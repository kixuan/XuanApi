package com.example.xuanapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.xuanapiclientsdk.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

import static com.example.xuanapiclientsdk.utils.SignUtils.genSign;


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
     * 构建请求头
     */
    private Map<String, String> getHeaderMap(String body) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        hashMap.put("secretKey", secretKey);
        hashMap.put("body", body);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(body, secretKey));
        return hashMap;
    }

    public String getNameByGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.get("http://localhost:8123/api/name/’", paramMap);

    }

    public String getNameByPost(@RequestParam String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.post("http://localhost:8123/api/name/", paramMap);
    }

    public String getUserNameByPost(@RequestBody User user) {

        String json = JSONUtil.toJsonStr(user);
        String url = "http://localhost:8123/api/name/user";
        HttpResponse httpResponse = HttpRequest.post(url)
                // 构建请求头
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        return httpResponse.body();
    }
}
