package com.hospital.medical.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 挂号详情视图对象。
 * 科室名称、患者姓名、医生姓名均通过关联查询获得，不冗余存储在挂号表中。
 */
public class RegistrationDetail {

    private Long id;
    private String regNo;
    private LocalDateTime regTime;
    private Byte regType;
    private BigDecimal chargeAmount;
    private Byte status;
    private String patientName;
    private String patientPhone;
    private String doctorName;
    private String doctorTitle;
    private String deptName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }

    public Byte getRegType() {
        return regType;
    }

    public void setRegType(Byte regType) {
        this.regType = regType;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
