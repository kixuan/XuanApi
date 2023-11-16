package com.example.xuanapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @author kixuan
 * @version 1.0
 */
public class SignUtils {
    /**
     * 生成签名
     *
     * @param body      包含需要签名的参数的哈希映射
     * @param secretKey 密钥
     * @return 生成的签名字符串
     */
    public static String genSign(String body, String secretKey) {
        Digester sha526 = new Digester(DigestAlgorithm.SHA256);
        String context = body + "." + secretKey;
        //  计算签名的摘要并返回摘要的十六进制表示形式
        return sha526.digestHex(context);
    }
}
