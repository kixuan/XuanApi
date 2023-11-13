package com.yupi.springbootinit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Spring MVC Json 配置
 */
@JsonComponent
public class JsonConfig {

    /**
     * 添加 Long 转 json 精度丢失的配置
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        // 不要使用 XML 数据格式创建 ObjectMapper
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        // SimpleModule 对象用于配置自定义的 Jackson 模块，以定制 JSON 转换规则
        SimpleModule module = new SimpleModule();
        // 注册一个自定义的序列化器，它将 Long 类型或其对应的原始类型（Long.TYPE）转换为字符串，而不是默认的整数。
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // 将module注册到之前创建的objectMapper中
        objectMapper.registerModule(module);
        return objectMapper;
    }
}