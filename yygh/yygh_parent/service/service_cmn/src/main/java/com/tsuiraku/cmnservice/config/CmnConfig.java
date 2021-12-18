package com.tsuiraku.cmnservice.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.tsuiraku.cmnservice.mapper")
public class CmnConfig {

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/11 下午10:11
     *  @Description: 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        /* 最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制 */
        /* paginationInterceptor.setLimit(2); */
        return paginationInterceptor;
    }
}
