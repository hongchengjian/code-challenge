package com.hrs.parcel.advice;

import com.hrs.common.api.ResultCode;
import com.hrs.common.api.ResultJson;
import com.hrs.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.hrs.parcel.controller"})
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResultJson handlerCustomException(CustomException e) {
        log.error(e.getResultJson().getMessage(), e);
        return e.getResultJson();
    }


    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    public ResultJson handlerMissingServletRequestParameterException(Exception e) {
        if (e instanceof MissingServletRequestParameterException) {
            log.error("缺少参数{}", ((MissingServletRequestParameterException)e).getParameterName());
            return ResultJson.failure(ResultCode.MISS_PARAM.getCode(), "MISS_PARAM:" + ((MissingServletRequestParameterException)e).getParameterName());
        } else {
            log.error("缺少body数据{}", ((HttpMessageNotReadableException)e).getMessage());
            return ResultJson.failure(ResultCode.MISS_PARAM.getCode(), "MISS_PARAM:Required request body is missing");
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验不通过, {}", e.getBindingResult().getFieldError().getDefaultMessage());
        return ResultJson.failure(ResultCode.DATA_ERROR.getCode(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResultJson handlerBeanPropertyBindingResult(BindException e) {
        log.error("参数绑定失败", e);
        return ResultJson.failure(ResultCode.DATA_ERROR.getCode(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultJson handlerException(Exception e) {
        log.error("系统错误", e);
        return ResultJson.failure(ResultCode.SYSTEM_ERROR.getCode(), "UNKNOWERROR");
    }
}
