package com.hospital.medical.service.impl;

import com.hospital.medical.entity.Registration;
import com.hospital.medical.mapper.RegistrationMapper;
import com.hospital.medical.service.RegistrationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 挂号记录表 服务实现类
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
@Service
public class RegistrationServiceImpl extends ServiceImpl<RegistrationMapper, Registration> implements RegistrationService {

}
