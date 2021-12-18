package com.tsuiraku.educms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tsuiraku")
public class CmsApplication {
    public static void main(String[] args) { SpringApplication.run(CmsApplication.class, args); }
}