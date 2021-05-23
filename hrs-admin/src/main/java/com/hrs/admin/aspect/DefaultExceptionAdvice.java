package com.hrs.admin.aspect;

import com.hrs.common.api.ResultJson;
import com.hrs.common.exception.CustomException;
import com.hrs.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 异常处理
 *
 * @author CJ
 * @since 1.0
 */
@RestControllerAdvice("com.hrs.admin.controller.BaseController")
@Slf4j
public class DefaultExceptionAdvice {

    /**
     * 自定义业务异常捕捉
     *
     * @param customException
     * @return ResultJson<?>
     */
    @ExceptionHandler(CustomException.class)
    public ResultJson<?> businessError(CustomException customException) {
        log.error("业务异常信息:{}", JsonUtil.toString(customException));
        return ResultJson.failure(customException.getResultJson().getCode(), customException.getResultJson().getMessage());
    }
}
