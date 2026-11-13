package com.hospital.medical.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.medical.common.Result;
import com.hospital.medical.entity.Registration;
import com.hospital.medical.mapper.RegistrationMapper;
import com.hospital.medical.service.RegistrationService;
import com.hospital.medical.vo.RegistrationDetail;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
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
 * 挂号管理：基础增删改查，以及关联患者、医生、科室的详情分页查询。
 */
@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Resource
    private RegistrationService registrationService;

    @Resource
    private RegistrationMapper registrationMapper;

    /** 挂号详情分页，支持患者姓名模糊、科室、状态过滤 */
    @GetMapping("/page-detail")
    public Result<IPage<RegistrationDetail>> pageDetail(
            @RequestParam(defaultValue = "1") long pageNum,
            @RequestParam(defaultValue = "5") long pageSize,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) Byte status) {
        Page<RegistrationDetail> page = new Page<>(pageNum, pageSize);
        return Result.ok(registrationMapper.selectDetailPage(page, patientName, deptId, status));
    }

    @GetMapping("/page")
    public Result<Page<Registration>> page(@RequestParam(defaultValue = "1") long pageNum,
                                           @RequestParam(defaultValue = "5") long pageSize,
                                           @RequestParam(required = false) Long patientId,
                                           @RequestParam(required = false) Long doctorId) {
        Page<Registration> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Registration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(patientId != null, Registration::getPatientId, patientId)
               .eq(doctorId != null, Registration::getDoctorId, doctorId)
               .orderByDesc(Registration::getRegTime);
        return Result.ok(registrationService.page(page, wrapper));
    }

    @GetMapping("/list")
    public Result<List<Registration>> list() {
        return Result.ok(registrationService.list(
                new LambdaQueryWrapper<Registration>().orderByDesc(Registration::getRegTime)));
    }

    @PostMapping
    public Result<Registration> add(@RequestBody Registration registration) {
        if (registration.getCreatedTime() == null) {
            registration.setCreatedTime(LocalDateTime.now());
        }
        registration.setId(null);
        registrationService.save(registration);
        return Result.ok("挂号成功", registration);
    }

    @PutMapping("/{id}")
    public Result<Registration> update(@PathVariable Long id, @RequestBody Registration registration) {
        if (registrationService.getById(id) == null) {
            return Result.fail(404, "挂号记录不存在");
        }
        registration.setId(id);
        registrationService.updateById(registration);
        return Result.ok("修改成功", registrationService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (!registrationService.removeById(id)) {
            return Result.fail(404, "挂号记录不存在或已删除");
        }
        return Result.ok("删除成功", null);
    }
}
