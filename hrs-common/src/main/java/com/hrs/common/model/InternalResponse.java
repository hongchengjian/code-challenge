package com.hrs.common.model;

import com.hrs.common.code.CodeInterface;
import lombok.Data;

/**
 * feignClient调用内部服务捕捉的异常如何返回给上层服务
 *
 * @author CJ
 * @since 1.0
 */
@Data
public class InternalResponse<T> {

    private static final int SUCCESS_CODE = 0;

    private boolean success;
    private int code;
    private String message;
    private T result;

    public static <T> InternalResponse success(T result) {
        InternalResponse response = new InternalResponse();
        response.success = true;
        response.code = SUCCESS_CODE;
        response.message = "成功";
        response.result = result;
        return response;
    }

    public static InternalResponse success() {
        InternalResponse response = new InternalResponse();
        response.success = true;
        response.code = SUCCESS_CODE;
        response.message = "成功";
        return response;
    }

    public static InternalResponse fail(CodeInterface resultCode) {
        InternalResponse response = new InternalResponse();
        response.success = false;
        response.code = resultCode.getCode();
        response.message = resultCode.getDesc();
        return response;
    }

    public static InternalResponse fail(int code, String message) {
        InternalResponse response = new InternalResponse();
        response.success = false;
        response.code = code;
        response.message = message;
        return response;
    }
}
