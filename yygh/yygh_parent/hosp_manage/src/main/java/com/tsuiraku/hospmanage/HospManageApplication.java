package com.tsuiraku.hospmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tsuiraku")
public class HospManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospManageApplication.class, args);
    }

}
