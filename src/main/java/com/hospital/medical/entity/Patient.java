package com.hospital.medical.entity;

import java.time.LocalDate;

/**
 * 患者基本信息实体，字段与 medical_system.patient 表一一对应。
 */
public record Patient(
        Long id,
        String patientNo,
        String name,
        Integer gender,
        LocalDate birthDate,
        String phone) {
}
