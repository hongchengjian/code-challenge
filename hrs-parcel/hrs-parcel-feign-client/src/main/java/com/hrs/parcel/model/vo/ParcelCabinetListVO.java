package com.hrs.parcel.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 寄存柜信息
 *
 * @author CJ
 * @since 1.0
 */
@ApiModel(description = "寄存柜信息查询返回")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParcelCabinetListVO {
    /**
     * 寄存柜编号
     */
    @ApiModelProperty(value = "寄存柜编号")
    private String cabinetNo;

    /**
     * 寄存柜状态0-空闲、1-占用、2-逾期、3-不可用
     */
    @ApiModelProperty(value = "寄存柜状态0-空闲、1-占用、2-逾期、3-不可用")
    private Integer cabinetStatus;
}
