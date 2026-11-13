package com.hospital.medical.controller;

import com.hospital.medical.service.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 患者接口：验证 Controller - Service - DAO 三层结构与 MySQL 连通性。
 */
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam(required = false) String keyword) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("code", 200);
        result.put("message", "查询成功");
        result.put("data", patientService.list(keyword));
        return result;
    }

    @GetMapping("/db-check")
    public Map<String, Object> dbCheck() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("code", 200);
        result.put("message", "数据库连接正常");
        result.put("data", patientService.dbCheck());
        return result;
    }
}
