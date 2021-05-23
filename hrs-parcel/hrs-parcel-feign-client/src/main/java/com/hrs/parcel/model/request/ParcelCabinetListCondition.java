package com.hrs.parcel.model.request;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 寄存柜查询条件
 *
 * @author CJ
 * @since 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParcelCabinetListCondition implements Serializable {
    private static final long serialVersionUID = -1157642220860394187L;

    /**
     * 寄存柜编号（单个）
     */
    private String cabinetNo;

    /**
     * 寄存柜状态0-空闲、1-占用、2-逾期、3-不可用（多状态勾选）
     */
    private List<Integer> cabinetStatus;
}
