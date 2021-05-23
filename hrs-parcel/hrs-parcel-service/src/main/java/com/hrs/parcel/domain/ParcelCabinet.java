package com.hrs.parcel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 寄存柜
 *
 * @author CJ
 * @since 1.0
 */
@Getter
@Setter
@TableName("parcel_cabinet")
public class ParcelCabinet extends BaseDomain {
    /**
     * 寄存柜编号
     */
    @TableField("cabinet_no")
    private String cabinetNo;

    /**
     * 寄存柜状态0-空闲、1-占用、2-逾期、3-不可用
     */
    @TableField("cabinet_status")
    private Integer cabinetStatus;
}
