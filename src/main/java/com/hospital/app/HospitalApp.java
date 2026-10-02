package com.hospital.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.hospital.app.dao")
@EnableTransactionManagement   //开启事务处理机制
public class HospitalApp {
    public static void main(String[] args) {
        SpringApplication.run(HospitalApp.class, args);
    }
}
