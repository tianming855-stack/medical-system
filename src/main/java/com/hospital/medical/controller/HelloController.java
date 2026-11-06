package com.hospital.medical.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 环境验证接口：用于确认 Spring Boot 已启动、路由可达。
 */
@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/hello")
    public Map<String, Object> hello(@RequestParam(defaultValue = "管理员") String name) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("system", "medical-system");
        data.put("operator", name);
        data.put("serverTime", LocalDateTime.now().toString());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("code", 200);
        result.put("message", "医疗系统运行正常");
        result.put("data", data);
        return result;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
