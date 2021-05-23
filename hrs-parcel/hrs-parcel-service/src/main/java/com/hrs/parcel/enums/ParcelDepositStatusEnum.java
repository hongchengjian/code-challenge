package com.hrs.parcel.enums;

import lombok.Getter;

/**
 * 寄存状态
 *
 * @author CJ
 * @since 1.0
 */
@Getter
public enum ParcelDepositStatusEnum {
    OCCUPIED(0, "寄存中"),
    PICKED_UP(2, "已取件"),
    OVERDUE(4, "已逾期"),
    OVERDUE_PICKED_UP(8, "逾期取件"),
    LOST(16, "行李已遗失"),
    INVALID(32, "已作废");
    private int code;

    private String desc;

    ParcelDepositStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
