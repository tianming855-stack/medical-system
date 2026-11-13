package com.hospital.medical.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 挂号记录表
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
@Getter
@Setter
@TableName("registration")
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 挂号主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 挂号单号，业务唯一标识
     */
    @TableField("reg_no")
    private String regNo;

    /**
     * 患者，外键
     */
    @TableField("patient_id")
    private Long patientId;

    /**
     * 医生，外键
     */
    @TableField("doctor_id")
    private Long doctorId;

    /**
     * 挂号时间
     */
    @TableField("reg_time")
    private LocalDateTime regTime;

    /**
     * 类型 1普通 2专家 3急诊
     */
    @TableField("reg_type")
    private Byte regType;

    /**
     * 实收挂号费
     */
    @TableField("charge_amount")
    private BigDecimal chargeAmount;

    /**
     * 状态 0待诊 1就诊中 2已完成 3已取消
     */
    @TableField("status")
    private Byte status;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;
}
