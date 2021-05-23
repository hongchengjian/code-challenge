package com.hrs.hotel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 通用Base domain
 *
 * @author CJ
 * @since 1.0
 */
@Getter
@Setter
public class BaseDomain {
    @TableId
    private Long id;

    @TableField("creator")
    private Long creator;

    @TableField("create_time")
    private Date createTime;

    @TableField("updater")
    private Long updater;

    @TableField(update = "NOW()")
    private Date updateTime;

    @TableField("deleted")
    @TableLogic
    private Boolean deleted;
}
