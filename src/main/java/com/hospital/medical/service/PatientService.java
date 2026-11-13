package com.hospital.medical.service;

import com.hospital.medical.dao.PatientDao;
import com.hospital.medical.entity.Patient;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 患者业务层：负责组装对外返回的数据结构，隔离 Controller 与 DAO。
 */
@Service
public class PatientService {

    private final PatientDao patientDao;

    public PatientService(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public List<Patient> list(String keyword) {
        return patientDao.findAll(keyword);
    }

    public Map<String, Object> dbCheck() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("database", patientDao.databaseInfo());
        data.put("patientCount", patientDao.count());
        return data;
    }
}
