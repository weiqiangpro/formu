package com.formu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.formu.mapper")
public class FormuApplication {

    public static void main(String[] args) {
        SpringApplication.run(FormuApplication.class, args);
    }

}
