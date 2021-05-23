package com.hrs.common.exception;

import com.hrs.common.api.ResultJson;
import com.hrs.common.code.CodeInterface;
import com.hrs.common.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author CJ
 * @since 1.0
 */
@Getter
@Setter
public class CustomException extends RuntimeException {

    private ResultJson resultJson;

    public CustomException(ResultJson resultJson) {
        this.resultJson = resultJson;
    }

    public <T extends CodeInterface> CustomException(T t) {
        this.resultJson = ResultJson.failure(t);
    }

    public CustomException(int code, String message) {
        this.resultJson = ResultJson.failure(code, message);
    }

    public <T extends CodeInterface> CustomException(T t, Throwable cause) {
        super(cause);
        this.resultJson = ResultJson.failure(t);
    }

    public CustomException(int code, String message, Throwable cause) {
        super(cause);
        this.resultJson = ResultJson.failure(code, message);
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "resultJson=" + JsonUtil.toString(resultJson) +
                '}';
    }

}
