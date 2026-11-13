package com.hospital.medical.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hospital.medical.common.ValidGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 患者基本信息表
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
@Getter
@Setter
@TableName("patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 患者主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 病历号，业务唯一标识
     */
    @NotBlank(message = "病历号不能为空", groups = ValidGroups.Create.class)
    @Size(max = 20, message = "病历号长度不能超过 20 位",
          groups = {ValidGroups.Create.class, ValidGroups.Update.class})
    @TableField("patient_no")
    private String patientNo;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = ValidGroups.Create.class)
    @Size(max = 30, message = "姓名长度不能超过 30 个字符",
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
     * 出生日期
     */
    @TableField("birth_date")
    private LocalDate birthDate;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空", groups = ValidGroups.Create.class)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确，应为 11 位大陆手机号",
             groups = {ValidGroups.Create.class, ValidGroups.Update.class})
    @TableField("phone")
    private String phone;

    /**
     * 身份证号
     */
    @Pattern(regexp = "^(\\d{17}[\\dXx])?$", message = "身份证号格式不正确，应为 18 位且末位可为 X",
             groups = {ValidGroups.Create.class, ValidGroups.Update.class})
    @TableField("id_card")
    private String idCard;

    /**
     * 联系地址
     */
    @Size(max = 200, message = "联系地址长度不能超过 200 个字符",
          groups = {ValidGroups.Create.class, ValidGroups.Update.class})
    @TableField("address")
    private String address;

    /**
     * 状态 1正常 0停用
     */
    @TableField("status")
    private Byte status;

    /**
     * 建档时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}
