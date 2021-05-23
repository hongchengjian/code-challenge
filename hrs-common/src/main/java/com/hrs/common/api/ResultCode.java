package com.hrs.common.api;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(0),

    LOGIN_ERROR(1000),
    EXPIRED(1001),
    /**
     * 丢失参数
     */
    MISS_PARAM(1002),
    /**
     * 数据错误
     */
    DATA_ERROR(1100),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(500),
    UNAUTHORIZED(401),
    FORBIDDEN(403);

    private int code;

    ResultCode(int code) {
        this.code = code;
    }

}
