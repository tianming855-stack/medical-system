package com.hospital.medical.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hospital.medical.common.ValidGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 科室信息表
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
@Getter
@Setter
@TableName("department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 科室主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 科室编码，业务唯一标识
     */
    @NotBlank(message = "科室编码不能为空", groups = ValidGroups.Create.class)
    @Size(max = 20, message = "科室编码长度不能超过 20 位",
          groups = {ValidGroups.Create.class, ValidGroups.Update.class})
    @TableField("dept_code")
    private String deptCode;

    /**
     * 科室名称
     */
    @NotBlank(message = "科室名称不能为空", groups = ValidGroups.Create.class)
    @Size(max = 50, message = "科室名称长度不能超过 50 个字符",
          groups = {ValidGroups.Create.class, ValidGroups.Update.class})
    @TableField("dept_name")
    private String deptName;

    /**
     * 科室位置
     */
    @TableField("location")
    private String location;

    /**
     * 科室联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 状态 1启用 0停用
     */
    @TableField("status")
    private Byte status;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;
}
