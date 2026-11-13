package com.hospital.medical.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 电子处方表
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
@Getter
@Setter
@TableName("prescription")
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 处方主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 处方编号，业务唯一标识
     */
    @TableField("presc_no")
    private String prescNo;

    /**
     * 对应挂号记录，外键
     */
    @TableField("registration_id")
    private Long registrationId;

    /**
     * 临床诊断
     */
    @TableField("diagnosis")
    private String diagnosis;

    /**
     * 医嘱
     */
    @TableField("advice")
    private String advice;

    /**
     * 状态 0草稿 1已开具 2已发药 3已作废
     */
    @TableField("status")
    private Byte status;

    /**
     * 开方时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;
}
