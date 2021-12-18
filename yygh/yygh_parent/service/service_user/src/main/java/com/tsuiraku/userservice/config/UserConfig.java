package com.tsuiraku.userservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.tsuiraku.userservice.mapper")
public class UserConfig {
}
