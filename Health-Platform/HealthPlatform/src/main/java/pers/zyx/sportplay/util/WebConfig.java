package pers.zyx.sportplay.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 全局配置类 -- 配置跨域请求
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 访问路径
                .allowedOrigins("Http://localhost:8080","null") // 请求来源
                .allowedMethods("GET","POST","PUT","OPTIONS","DELETE") // 请求方式
                .allowCredentials(true) // 允许携带信息
                .maxAge(3600); // 最大响应时间
    }
}
