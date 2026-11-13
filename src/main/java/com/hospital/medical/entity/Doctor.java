package com.hospital.medical.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hospital.medical.common.ValidGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 医生信息表
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
@Getter
@Setter
@TableName("doctor")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 医生主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 医生工号，业务唯一标识
     */
    @NotBlank(message = "医生工号不能为空", groups = ValidGroups.Create.class)
    @Size(max = 20, message = "医生工号长度不能超过 20 位",
          groups = {ValidGroups.Create.class, ValidGroups.Update.class})
    @TableField("doctor_no")
    private String doctorNo;

    /**
     * 姓名
     */
    @NotBlank(message = "医生姓名不能为空", groups = ValidGroups.Create.class)
    @Size(max = 30, message = "医生姓名长度不能超过 30 个字符",
          groups = {ValidGroups.Create.class, ValidGroups.Update.class})
    @TableField("name")
    private String name;

    /**
     * 性别 1男 2女
     */
    @NotNull(message = "性别不能为空", groups = ValidGroups.Create.class)
    @TableField("gender")
    private Byte gender;

    /**
     * 职称
     */
    @TableField("title")
    private String title;

    /**
     * 所属科室，外键
     */
    @NotNull(message = "所属科室不能为空", groups = ValidGroups.Create.class)
    @TableField("department_id")
    private Long departmentId;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空", groups = ValidGroups.Create.class)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "联系电话格式不正确，应为 11 位大陆手机号",
             groups = {ValidGroups.Create.class, ValidGroups.Update.class})
    @TableField("phone")
    private String phone;

    /**
     * 电子邮箱
     */
    @Email(message = "电子邮箱格式不正确",
           groups = {ValidGroups.Create.class, ValidGroups.Update.class})
    @TableField("email")
    private String email;

    /**
     * 擅长方向
     */
    @TableField("specialty")
    private String specialty;

    /**
     * 状态 1在职 0停诊
     */
    @TableField("status")
    private Byte status;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;
}
