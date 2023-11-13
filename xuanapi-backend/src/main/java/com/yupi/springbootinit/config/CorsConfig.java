package com.yupi.springbootinit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 覆盖所有请求
        registry.addMapping("/**")
                // 允许发送 Cookie
                .allowCredentials(true)
                // 放行哪些域名（必须用 patterns，否则 * 会和 allowCredentials 冲突）
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
// 解释【使用 allowedOriginPatterns("*") 而不是 allowedOrigin("*")】
// 当你使用通配符 "*"（例如 allowedOrigin("*")）来允许任何来源时，浏览器会阻止发送或接收 Cookies，以防止潜在的安全风险。这是因为允许来自任何来源的跨域请求并发送 Cookies 可能会导致潜在的安全问题。
// 因此，当你需要同时允许任何来源和跨域 Cookies 时，你必须使用 allowedOriginPatterns("*")，而不是 allowedOrigin("*")，以明确表示这是一个模式匹配。这告诉浏览器，虽然允许任何来源，但仍然需要符合模式规则。这有助于减少潜在的安全风险。
}
