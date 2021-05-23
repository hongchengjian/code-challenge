package com.hrs.parcel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 寄存记录
 *
 * @author CJ
 * @since 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("parcel_deposit")
public class ParcelDeposit extends BaseDomain {
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
     * 寄存人姓名
     */
    @TableField("depositor_name")
    private String depositorName;

    /**
     * 寄存人电话
     */
    @TableField("depositor_mobile")
    private String depositorMobile;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 酒店入住时间
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * 酒店离开时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 寄存状态(0-寄存中，2-已取件，4-已逾期，8-逾期取件，16-行李已遗失，32-已作废)
     */
    @TableField("deposit_status")
    private Integer depositStatus;
}
