package com.hospital.medical.tools;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * MyBatis-Plus 代码生成器。
 * 读取 medical_system 库的六张表，一键生成 entity、mapper、service、controller 全套基础代码。
 * 运行方式：mvn compile exec:java -Dexec.mainClass=com.hospital.medical.tools.CodeGenerator
 */
public class CodeGenerator {

    private static final String URL =
            "jdbc:mysql://localhost:3306/medical_system?useUnicode=true&characterEncoding=utf8"
            + "&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "medical";
    private static final String PWD = "Medical@2026";

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create(URL, USER, PWD)
                .globalConfig(builder -> builder
                        .author("田明")
                        .outputDir(projectPath + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                        .disableOpenDir())
                .packageConfig(builder -> builder
                        .parent("com.hospital.medical")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .xml("mapper")
                        .pathInfo(Collections.singletonMap(
                                OutputFile.xml, projectPath + "/src/main/resources/mapper")))
                .strategyConfig(builder -> {
                    builder.addInclude("department", "doctor", "patient", "registration",
                            "prescription", "prescription_item");
                    builder.entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation();
                    builder.mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList();
                    builder.serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl");
                    builder.controllerBuilder()
                            .enableRestStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

        System.out.println("code generation finished: " + projectPath);
    }
}
