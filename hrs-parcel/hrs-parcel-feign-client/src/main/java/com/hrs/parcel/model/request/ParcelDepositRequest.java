package com.hrs.parcel.model.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 寄存请求参数
 *
 * @author CJ
 * @since 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParcelDepositRequest {

    /**
     * 寄存柜编号
     */
    @NotNull(message = "寄存柜编号不能为空")
    private String cabinetNo;

    /**
     * 管理员id
     */
    @NotNull(message = "管理员id不能为空")
    private Long adminId;

    /**
     * 管理员姓名
     */
    private String adminName;

    /**
     * 管理员电话
     */
    private String adminMobile;

    /**
     * 寄存人姓名
     */
    @NotNull(message = "寄存人姓名不能为空")
    @Size(max = 50, min = 0, message = "寄存人姓名长度不能超过50字")
    private String depositorName;

    /**
     * 寄存人电话
     */
    @NotNull(message = "寄存人电话：不能为空")
    @Pattern(regexp = "^1(3|4|5|6|7|8|9)\\d{9}$", message = "寄存人电话：格式不正确,11位以1开头的数字")
    private String depositorMobile;

    /**
     * 备注
     */
    @Size(max = 100, min = 0, message = "备注长度不能超过100字")
    private String remark;

    /**
     * 酒店入住时间（没预定房间也可以存）
     */
    //@NotNull(message = "酒店入住时间不能为空")
    private Date startTime;

    /**
     * 酒店离开时间（没预定房间也可以存）
     */
    //@NotNull(message = "酒店离开时间不能为空")
    private Date endTime;
}
