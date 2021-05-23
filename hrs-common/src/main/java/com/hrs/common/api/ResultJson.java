package com.hrs.common.api;

import com.hrs.common.code.CodeInterface;
import com.hrs.common.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultJson<T> {

    private static final String SUCCESS_MESSAGE = "success";

    private int code;

    private String message;

    private T data;

    public ResultJson(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultJson(int code, String message) {
        this(code, message, null);
    }

    public ResultJson(T data) {
        this(ResultCode.SUCCESS.getCode(), SUCCESS_MESSAGE, data);
    }

    public static ResultJson failure(int code, String message) {
        return new ResultJson(code, message);
    }

    public static <T extends CodeInterface> ResultJson failure(T t) {
        return failure(t.getCode(), t.getDesc());
    }

    public static <T> ResultJson<T> success(T data) {
        return new ResultJson<T>(data);
    }

    @Override
    public String toString() {
        return JsonUtil.toString(this);
    }

}
