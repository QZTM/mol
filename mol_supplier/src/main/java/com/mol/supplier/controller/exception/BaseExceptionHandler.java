package com.mol.supplier.controller.exception;

import entity.ServiceResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理控制器
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ServiceResult exception(Exception e){
        e.printStackTrace();
        return new ServiceResult(false,"30000",e.getMessage());
    }
}
