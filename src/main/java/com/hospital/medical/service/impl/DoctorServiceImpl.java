package com.hospital.medical.service.impl;

import com.hospital.medical.entity.Doctor;
import com.hospital.medical.mapper.DoctorMapper;
import com.hospital.medical.service.DoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 医生信息表 服务实现类
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {

}
