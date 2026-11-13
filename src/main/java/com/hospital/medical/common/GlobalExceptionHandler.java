package com.hospital.medical.common;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理：把参数校验失败与数据库约束冲突转成可读的提示。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 实体校验失败，返回 400 与具体字段提示 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        return Result.fail(400, msg);
    }

    /** 唯一键冲突，例如病历号、身份证号重复 */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result<Void> handleDuplicate(DuplicateKeyException e) {
        return Result.fail(409, "数据已存在：业务编号或身份证号不能重复");
    }

    /** 外键约束冲突，例如删除仍有挂号记录的医生 */
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result<Void> handleIntegrity(org.springframework.dao.DataIntegrityViolationException e) {
        return Result.fail(409, "数据存在关联记录，无法完成当前操作");
    }
}
