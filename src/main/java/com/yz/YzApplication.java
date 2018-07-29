package com.yz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yz.mapper")
@SpringBootApplication
public class YzApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzApplication.class, args);
    }
}
