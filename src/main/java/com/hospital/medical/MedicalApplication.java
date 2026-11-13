package com.hospital.medical;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 医疗系统启动类。
 * Mapper 接口由代码生成器生成，此处统一扫描 mapper 包。
 */
@SpringBootApplication
@MapperScan("com.hospital.medical.mapper")
public class MedicalApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalApplication.class, args);
    }
}
