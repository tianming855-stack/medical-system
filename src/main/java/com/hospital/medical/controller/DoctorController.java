package com.hospital.medical.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.medical.common.Result;
import com.hospital.medical.common.ValidGroups;
import com.hospital.medical.entity.Doctor;
import com.hospital.medical.service.DoctorService;
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
 * 医生管理：按姓名、科室、职称组合分页查询，并支持新增、修改、删除。
 */
@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    @Resource
    private DoctorService doctorService;

    /** 分页查询，支持姓名模糊、科室精确、职称模糊 */
    @GetMapping("/page")
    public Result<Page<Doctor>> page(@RequestParam(defaultValue = "1") long pageNum,
                                     @RequestParam(defaultValue = "5") long pageSize,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) Long departmentId,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(required = false) Byte status) {
        Page<Doctor> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Doctor> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name), Doctor::getName, name)
               .eq(departmentId != null, Doctor::getDepartmentId, departmentId)
               .like(StringUtils.hasText(title), Doctor::getTitle, title)
               .eq(status != null, Doctor::getStatus, status)
               .orderByAsc(Doctor::getId);
        return Result.ok(doctorService.page(page, wrapper));
    }

    @GetMapping("/list")
    public Result<List<Doctor>> list() {
        return Result.ok(doctorService.list(
                new LambdaQueryWrapper<Doctor>().orderByAsc(Doctor::getId)));
    }

    @GetMapping("/{id}")
    public Result<Doctor> getById(@PathVariable Long id) {
        Doctor doctor = doctorService.getById(id);
        if (doctor == null) {
            return Result.fail(404, "医生不存在");
        }
        return Result.ok(doctor);
    }

    @PostMapping
    public Result<Doctor> add(@Validated(ValidGroups.Create.class) @RequestBody Doctor doctor) {
        if (doctor.getCreatedTime() == null) {
            doctor.setCreatedTime(LocalDateTime.now());
        }
        if (doctor.getStatus() == null) {
            doctor.setStatus((byte) 1);
        }
        doctor.setId(null);
        doctorService.save(doctor);
        return Result.ok("新增成功", doctor);
    }

    @PutMapping("/{id}")
    public Result<Doctor> update(@PathVariable Long id,
                                 @Validated(ValidGroups.Update.class) @RequestBody Doctor doctor) {
        if (doctorService.getById(id) == null) {
            return Result.fail(404, "医生不存在");
        }
        doctor.setId(id);
        doctorService.updateById(doctor);
        return Result.ok("修改成功", doctorService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (!doctorService.removeById(id)) {
            return Result.fail(404, "医生不存在或已删除");
        }
        return Result.ok("删除成功", null);
    }
}
