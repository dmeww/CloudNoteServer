package com.dygticky.cnoteserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@MapperScan("com.dygticky.cnoteserver.mapper")
@SpringBootApplication
public class CNoteServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CNoteServerApplication.class, args);
    }


    @Value("${file.upload-dir}")
    private String uploadDir;


}


