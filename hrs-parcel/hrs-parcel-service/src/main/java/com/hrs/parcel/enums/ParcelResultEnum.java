package com.hrs.parcel.enums;

import com.hrs.common.code.CodeInterface;
import lombok.Getter;
/**
 * 自定义Code
 *
 * @author CJ
 * @since 1.0
 */
@Getter
public enum ParcelResultEnum implements CodeInterface {
    CABINET_IS_EMPTY(100001,"寄存柜不存在"),
    CABINET_UNAVAILABLE(100002,"寄存柜不可用"),
    PARCEL_DEPOSIT_IS_EMPTY(100003,"寄存包裹不存在"),
    PARCEL_DEPOSIT_PICKED_UP(100004,"不可重复取件"),
    PARCEL_LOST(100005,"包裹可能遗失，请联系前台")
    ;

    private int code;

    private String desc;

    ParcelResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
