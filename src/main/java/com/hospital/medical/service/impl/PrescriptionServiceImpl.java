package com.hospital.medical.service.impl;

import com.hospital.medical.entity.Prescription;
import com.hospital.medical.mapper.PrescriptionMapper;
import com.hospital.medical.service.PrescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 电子处方表 服务实现类
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
@Service
public class PrescriptionServiceImpl extends ServiceImpl<PrescriptionMapper, Prescription> implements PrescriptionService {

}
