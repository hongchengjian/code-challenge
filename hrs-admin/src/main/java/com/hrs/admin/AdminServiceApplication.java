package com.hrs.admin;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.hrs.security.EnableJwtSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.hrs.parcel.client.**"})
@EnableSwagger2
@EnableJwtSecurity
@ComponentScan(basePackages = {"com.hrs.admin.config","com.hrs.admin"})
public class AdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor =  new PaginationInterceptor();
        return paginationInterceptor;
    }

}
