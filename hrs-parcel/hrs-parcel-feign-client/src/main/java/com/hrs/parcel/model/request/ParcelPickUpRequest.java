package com.hrs.parcel.model.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 取件请求参数
 *
 * @author CJ
 * @since 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParcelPickUpRequest {

    /**
     * 寄存柜编号
     */
    @NotNull(message = "寄存柜编号不能为空")
    private String cabinetNo;

    /**
     * 寄存编号
     */
    @NotNull(message = "寄存编号不能为空")
    private String depositNo;

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
     * 取件人姓名
     */
    @NotNull(message = "取件人姓名不能为空")
    @Size(max = 50, min = 0, message = "取件人姓名长度不能超过50字")
    private String recipientName;

    /**
     * 取件人电话
     */
    @NotNull(message = "取件人电话：不能为空")
    @Pattern(regexp = "^1(3|4|5|6|7|8|9)\\d{9}$", message = "取件人电话：格式不正确,11位以1开头的数字")
    private String recipientMobile;

    /**
     * 取件类型
     */
    @NotNull(message = "取件类型：不能为空")
    private Integer pickupType;

}
