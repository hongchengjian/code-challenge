CREATE DATABASE hrs DEFAULT CHARACTER SET UTF8MB4;
use hrs;
DROP TABLE IF EXISTS `parcel_cabinet`;
CREATE TABLE `parcel_cabinet` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `cabinet_no` VARCHAR(30) NOT NULL COMMENT '寄存柜编号',
    `cabinet_status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '寄存柜状态0-空闲、1-占用、2-逾期、3-不可用',
    `creator` BIGINT(21) NOT NULL DEFAULT '0' COMMENT '创建者',
    `updater` BIGINT(21) NOT NULL DEFAULT '0' COMMENT '更新者',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted` TINYINT(1) DEFAULT '0' NOT NULL COMMENT '0是true 存在，有效的',
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='寄存柜表';

INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0001');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0002');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0003');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0004');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0005');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0006');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0007');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0008');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0009');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0010');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0011');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0012');
INSERT INTO `hrs`.`parcel_cabinet` (`cabinet_no`) VALUES ('NO0013');

ALTER TABLE `hrs`.`parcel_cabinet`
ADD UNIQUE INDEX `uniq_cabinet_no` (`cabinet_no` ASC);

DROP TABLE IF EXISTS `parcel_deposit`;
CREATE TABLE `parcel_deposit` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `deposit_no` VARCHAR(50) NOT NULL COMMENT '寄存编号',
    `cabinet_no` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '寄存柜编号',
    `admin_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '管理员id',
    `admin_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '管理员姓名',
    `admin_mobile` VARCHAR(11) NOT NULL DEFAULT '' COMMENT '管理员电话',
    `depositor_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '寄存人姓名',
    `depositor_mobile` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '寄存人电话',
    `remark` VARCHAR(125) NOT NULL DEFAULT '' COMMENT '备注',
    `start_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '酒店入住时间',
    `end_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '酒店离开时间',
    `deposit_status` INT NOT NULL DEFAULT '0' COMMENT '寄存状态(0-寄存中，2-已取件，4-已逾期，8-逾期取件，16-行李已遗失，32-已作废)',
    `creator` BIGINT(21) NOT NULL DEFAULT '0' COMMENT '创建者',
    `updater` BIGINT(21) NOT NULL DEFAULT '0' COMMENT '更新者',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted` TINYINT(1) DEFAULT '0' NOT NULL COMMENT '0是true 存在，有效的',
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='包裹寄存表';

ALTER TABLE `hrs`.`parcel_deposit`
ADD UNIQUE INDEX `uniq_deposit_no` (`deposit_no` ASC),
ADD INDEX `idx_cabinet_no` (`cabinet_no` ASC),
ADD INDEX `idx_admin_id` (`admin_id` ASC),
ADD INDEX `idx_admin_name` (`admin_name` ASC),
ADD INDEX `idx_depositor_name` (`depositor_name` ASC),
ADD INDEX `idx_deposit_mobile` (`depositor_mobile` ASC),
ADD INDEX `idx_admin_mobile` (`admin_mobile` ASC);

DROP TABLE IF EXISTS `parcel_pickup`;
CREATE TABLE `parcel_pickup` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `pickup_no` VARCHAR(50) NOT NULL COMMENT '取件编号',
    `deposit_no` VARCHAR(50) NOT NULL COMMENT '寄存编号',
	`cabinet_no` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '寄存柜编号',
    `admin_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '管理员id',
    `admin_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '管理员姓名',
    `admin_mobile` VARCHAR(11) NOT NULL DEFAULT '' COMMENT '管理员电话',
    `recipient_name` VARCHAR(20) NOT NULL COMMENT '取件人姓名',
    `recipient_mobile` VARCHAR(20) NOT NULL COMMENT '取件人电话',
    `pickup_type` INT NOT NULL DEFAULT '0' COMMENT '取件类型(0-正常取件，2-行李有遗失，4-逾期取件)',
    `pickup_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '取件时间',
	`creator` BIGINT(21) NOT NULL DEFAULT '0' COMMENT '创建者',
    `updater` BIGINT(21) NOT NULL DEFAULT '0' COMMENT '更新者',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted` TINYINT(1) DEFAULT '0' NOT NULL COMMENT '0是true 存在，有效的',
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='取件表';

ALTER TABLE `hrs`.`parcel_pickup`
ADD UNIQUE INDEX `uniq_pickup_no` (`pickup_no` ASC),
ADD INDEX `idx_deposit_no` (`deposit_no` ASC),
ADD INDEX `idx_cabinet_no` (`cabinet_no` ASC),
ADD INDEX `idx_admin_id` (`admin_id` ASC),
ADD INDEX `idx_admin_name` (`admin_name` ASC),
ADD INDEX `idx_recipient_name` (`recipient_name` ASC),
ADD INDEX `idx_recipient_mobile` (`recipient_mobile` ASC),
ADD INDEX `idx_admin_mobile` (`admin_mobile` ASC);

DROP TABLE IF EXISTS `user_admin`;
CREATE TABLE `user_admin` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `admin_id` VARCHAR(30) NOT NULL COMMENT '编号',
    `admin_name` VARCHAR(20) NOT NULL COMMENT '姓名',
    `admin_mobile` VARCHAR(11) NOT NULL COMMENT '手机号码',
    `admin_type` INT NOT NULL DEFAULT '1' COMMENT '管理员类型(1-Receptionist，2-ADMIN,4-SYS)',
    `password` VARCHAR(20) NOT NULL COMMENT '登录密码',
    `creator` BIGINT(21) NOT NULL DEFAULT '0' COMMENT '创建者',
    `updater` BIGINT(21) NOT NULL DEFAULT '0' COMMENT '更新者',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted` TINYINT(1) DEFAULT '0' NOT NULL COMMENT '0是true 存在，有效的',
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='管理员表';

ALTER TABLE `hrs`.`user_admin`
ADD UNIQUE INDEX `uniq_admin_id` (`admin_id` ASC),
ADD INDEX `idx_admin_name` (`admin_name` ASC),
ADD INDEX `idx_admin_mobile` (`admin_mobile` ASC);