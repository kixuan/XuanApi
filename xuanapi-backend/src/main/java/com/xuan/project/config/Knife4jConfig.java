package com.xuan.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4j 接口文档配置
 * https://doc.xiaominfo.com/knife4j/documentation/get_start.html
 */
@Configuration  // 标记配置类
@EnableSwagger2  //开启Swagger功能，可以生成api接口文档
@Profile({"dev", "test"}) // 这个配置类仅在激活了 "dev" 或 "test" 配置文件时生效
public class Knife4jConfig {
    @Bean
    // Docket：创建和配置 Swagger 的 Docket 对象。Docket 是 Swagger 的主要配置类，它允许你定义生成 API 文档的详细规则
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                // apiInfo：配置 API 文档的基本信息，如标题、描述和版本号
                .apiInfo(new ApiInfoBuilder()
                        .title("接口文档")
                        .description("xuanapi-backend")
                        .version("1.0")
                        .build())
                .select()// 配置 Swagger 如何选择哪些接口和路径用于生成文档
                // 指定 Controller 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.xuan.springbootinit.controller"))
                .paths(PathSelectors.any())  //这一行表示选择所有的路径，不进行过滤，将所有接口包括在文档中。
                .build();
    }
}