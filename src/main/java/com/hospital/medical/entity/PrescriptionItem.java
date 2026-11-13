package com.hospital.medical.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 处方明细表
 * </p>
 *
 * @author 田明
 * @since 2026-11-13
 */
@Getter
@Setter
@TableName("prescription_item")
public class PrescriptionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 明细主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属处方，外键
     */
    @TableField("prescription_id")
    private Long prescriptionId;

    /**
     * 药品名称
     */
    @TableField("drug_name")
    private String drugName;

    /**
     * 规格
     */
    @TableField("spec")
    private String spec;

    /**
     * 单次用量
     */
    @TableField("dosage")
    private String dosage;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 单价
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;

    /**
     * 用法
     */
    @TableField("usage_method")
    private String usageMethod;
}
