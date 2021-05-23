package com.hrs.parcel.model.vo;

import lombok.*;

import java.util.Date;

/**
 * 包裹存记录信息
 *
 * @author CJ
 * @since 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParcelDepositListVO {
    /**
     * 寄存编号
     */
    private String depositNo;

    /**
     * 寄存柜编号
     */
    private String cabinetNo;

    /**
     * 管理员id
     */
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
    private String depositorName;

    /**
     * 寄存人电话
     */
    private String depositorMobile;

    /**
     * 备注
     */
    private String remark;

    /**
     * 酒店入住时间
     */
    private Date startTime;

    /**
     * 酒店离开时间
     */
    private Date endTime;

    /**
     * 寄存状态(0-寄存中，2-已取件，4-已逾期，8-逾期取件，16-行李已遗失，32-已作废)
     */
    private Integer depositStatus;
}
