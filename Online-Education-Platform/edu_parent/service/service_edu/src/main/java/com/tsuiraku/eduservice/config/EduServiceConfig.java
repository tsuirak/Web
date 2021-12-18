package com.tsuiraku.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.tsuiraku.eduservice.mapper")
public class EduServiceConfig {

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 上午8:16
     *  @Description: mybatis-plus逻辑删除插件
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/8/26 上午8:16
     *  @Description: mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
