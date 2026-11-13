package com.hospital.medical.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hospital.medical.entity.Registration;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.medical.vo.RegistrationDetail;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 挂号记录表 Mapper 接口
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
public interface RegistrationMapper extends BaseMapper<Registration> {

    /**
     * 挂号详情分页查询：关联患者、医生、科室三张表。
     * 科室名称由 doctor.department_id 关联得到，挂号表中未冗余存储。
     */
    IPage<RegistrationDetail> selectDetailPage(IPage<RegistrationDetail> page,
                                               @Param("patientName") String patientName,
                                               @Param("deptId") Long deptId,
                                               @Param("status") Byte status);
}
