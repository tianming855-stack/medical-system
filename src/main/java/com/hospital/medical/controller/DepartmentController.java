package com.hospital.medical.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.medical.common.Result;
import com.hospital.medical.common.ValidGroups;
import com.hospital.medical.entity.Department;
import com.hospital.medical.service.DepartmentService;
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
 * 科室管理：按名称与状态组合分页查询，并支持新增、修改、删除。
 */
@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    /** 分页查询，支持名称模糊、编码模糊、状态过滤 */
    @GetMapping("/page")
    public Result<Page<Department>> page(@RequestParam(defaultValue = "1") long pageNum,
                                         @RequestParam(defaultValue = "5") long pageSize,
                                         @RequestParam(required = false) String deptName,
                                         @RequestParam(required = false) String deptCode,
                                         @RequestParam(required = false) Byte status) {
        Page<Department> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(deptName), Department::getDeptName, deptName)
               .like(StringUtils.hasText(deptCode), Department::getDeptCode, deptCode)
               .eq(status != null, Department::getStatus, status)
               .orderByAsc(Department::getId);
        return Result.ok(departmentService.page(page, wrapper));
    }

    @GetMapping("/list")
    public Result<List<Department>> list() {
        return Result.ok(departmentService.list(
                new LambdaQueryWrapper<Department>().orderByAsc(Department::getId)));
    }

    @GetMapping("/{id}")
    public Result<Department> getById(@PathVariable Long id) {
        Department department = departmentService.getById(id);
        if (department == null) {
            return Result.fail(404, "科室不存在");
        }
        return Result.ok(department);
    }

    @PostMapping
    public Result<Department> add(@Validated(ValidGroups.Create.class) @RequestBody Department department) {
        if (department.getCreatedTime() == null) {
            department.setCreatedTime(LocalDateTime.now());
        }
        if (department.getStatus() == null) {
            department.setStatus((byte) 1);
        }
        department.setId(null);
        departmentService.save(department);
        return Result.ok("新增成功", department);
    }

    @PutMapping("/{id}")
    public Result<Department> update(@PathVariable Long id,
                                     @Validated(ValidGroups.Update.class) @RequestBody Department department) {
        if (departmentService.getById(id) == null) {
            return Result.fail(404, "科室不存在");
        }
        department.setId(id);
        departmentService.updateById(department);
        return Result.ok("修改成功", departmentService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (!departmentService.removeById(id)) {
            return Result.fail(404, "科室不存在或已删除");
        }
        return Result.ok("删除成功", null);
    }
}
