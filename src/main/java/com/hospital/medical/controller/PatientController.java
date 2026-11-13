package com.hospital.medical.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.medical.common.Result;
import com.hospital.medical.common.ValidGroups;
import com.hospital.medical.entity.Patient;
import com.hospital.medical.service.PatientService;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 患者管理：多条件组合分页查询、新增、修改、删除。
 * 查询条件通过方法参数绑定，由 MyBatis-Plus 生成预编译语句，不拼接 SQL 字符串。
 */
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    @Resource
    private PatientService patientService;

    /** 分页查询，支持姓名模糊、病历号模糊、手机号精确、性别过滤 */
    @GetMapping("/page")
    public Result<Page<Patient>> page(@RequestParam(defaultValue = "1") long pageNum,
                                      @RequestParam(defaultValue = "5") long pageSize,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String patientNo,
                                      @RequestParam(required = false) String phone,
                                      @RequestParam(required = false) Byte gender) {
        Page<Patient> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Patient> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name), Patient::getName, name)
               .like(StringUtils.hasText(patientNo), Patient::getPatientNo, patientNo)
               .eq(StringUtils.hasText(phone), Patient::getPhone, phone)
               .eq(gender != null, Patient::getGender, gender)
               .orderByDesc(Patient::getId);
        return Result.ok(patientService.page(page, wrapper));
    }

    /** 查询全部，供接口联调与实验一说明延续 */
    @GetMapping("/list")
    public Result<List<Patient>> list() {
        return Result.ok(patientService.list(
                new LambdaQueryWrapper<Patient>().orderByAsc(Patient::getId)));
    }

    /** 按主键查询单条 */
    @GetMapping("/{id}")
    public Result<Patient> getById(@PathVariable Long id) {
        Patient patient = patientService.getById(id);
        if (patient == null) {
            return Result.fail(404, "患者不存在");
        }
        return Result.ok(patient);
    }

    /** 新增患者，字段格式由实体上的校验注解把关 */
    @PostMapping
    public Result<Patient> add(@Validated(ValidGroups.Create.class) @RequestBody Patient patient) {
        if (patient.getCreateTime() == null) {
            patient.setCreateTime(LocalDateTime.now());
        }
        if (patient.getStatus() == null) {
            patient.setStatus((byte) 1);
        }
        patient.setId(null);
        patientService.save(patient);
        return Result.ok("新增成功", patient);
    }

    /** 修改患者，未传的字段保持不变，仅校验已提交的字段格式 */
    @PutMapping("/{id}")
    public Result<Patient> update(@PathVariable Long id,
                                  @Validated(ValidGroups.Update.class) @RequestBody Patient patient) {
        if (patientService.getById(id) == null) {
            return Result.fail(404, "患者不存在");
        }
        patient.setId(id);
        patientService.updateById(patient);
        return Result.ok("修改成功", patientService.getById(id));
    }

    /** 删除患者 */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (!patientService.removeById(id)) {
            return Result.fail(404, "患者不存在或已删除");
        }
        return Result.ok("删除成功", null);
    }
}
