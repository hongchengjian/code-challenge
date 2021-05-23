package com.hrs.parcel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * 取记录
 *
 * @author CJ
 * @since 1.0
 */
@Getter
@Setter
@Builder
public class ParcelPickup extends BaseDomain {
    /**
     * 取件编号
     */
    @TableField("pickup_no")
    private String pickupNo;

    /**
     * 寄存编号
     */
    @TableField("deposit_no")
    private String depositNo;

    /**
     * 寄存柜编号
     */
    @TableField("cabinet_no")
    private String cabinetNo;

    /**
     * 管理员id
     */
    @TableField("admin_id")
    private Long adminId;

    /**
     * 管理员姓名
     */
    @TableField("admin_id")
    private String adminName;

    /**
     * 管理员电话
     */
    @TableField("admin_mobile")
    private String adminMobile;

    /**
     * 取件人姓名
     */
    @TableField("recipient_name")
    private String recipientName;

    /**
     * 取件人电话
     */
    @TableField("recipient_mobile")
    private String recipientMobile;

    /**
     * 取件类型
     */
    @TableField("pickup_type")
    private Integer pickupType;

    /**
     * 取件时间
     */
    @TableField("pickup_time")
    private Long pickupTime;


}
