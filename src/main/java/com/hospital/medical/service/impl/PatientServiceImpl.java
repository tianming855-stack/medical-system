package com.hospital.medical.service.impl;

import com.hospital.medical.entity.Patient;
import com.hospital.medical.mapper.PatientMapper;
import com.hospital.medical.service.PatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 患者基本信息表 服务实现类
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

}
