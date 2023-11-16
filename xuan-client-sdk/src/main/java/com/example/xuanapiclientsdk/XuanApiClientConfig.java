package com.example.xuanapiclientsdk;

import com.example.xuanapiclientsdk.client.XuanApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author kixuan
 * @version 1.0
 */

@Configuration
@ConfigurationProperties("xuanapi.client")
@Data
@ComponentScan
public class XuanApiClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public XuanApiClient xuanApiClient() {
        return new XuanApiClient(accessKey, secretKey);
    }

}
