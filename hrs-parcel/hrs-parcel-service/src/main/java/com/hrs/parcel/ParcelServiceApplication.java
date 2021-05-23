package com.hrs.parcel;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.hrs.security.EnableJwtSecurity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.hrs.parcel.mapper")
@EnableEurekaClient
@EnableJwtSecurity
public class ParcelServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParcelServiceApplication.class, args);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor =  new PaginationInterceptor();
        return paginationInterceptor;
    }
}
