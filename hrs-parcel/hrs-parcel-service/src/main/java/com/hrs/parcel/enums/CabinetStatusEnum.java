package com.hrs.parcel.enums;

import lombok.Getter;
/**
 * 寄存柜状态
 *
 * @author CJ
 * @since 1.0
 */
@Getter
public enum CabinetStatusEnum {
    AVAILABLE(0, "空闲"),
    OCCUPIED(1, "占用"),
    OVERDUE(2, "逾期"),
    UNAVAILABLE(3, "不可用");
    private int code;

    private String desc;

    CabinetStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
