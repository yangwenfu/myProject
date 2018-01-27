--liquibase formatted sql

--changeset sephy:1
ALTER TABLE `ym_member` ADD `ACTIVATE` BIT(1)  NOT NULL  DEFAULT b'1'  COMMENT '云码激活状态 0未激活 1已激活' AFTER `AUDIT_STATUS`;

--changeset menglei:1
ALTER TABLE `ym_member` ADD `CORP_BANK_ID` BIGINT(20)  NULL  DEFAULT NULL  COMMENT '对公账号id'  AFTER `BANK_CARD_ID`;

--changeset menglei:2
CREATE TABLE `ym_third_user` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `THIRD_CONFIG_ID` bigint(20) DEFAULT NULL COMMENT '关联ym_third_config id',
  `OUT_ID` varchar(50) DEFAULT NULL COMMENT '关联外部id',
  `MOBILE` varchar(20) DEFAULT NULL COMMENT '外部手机号',
  `USER_ID` varchar(50) DEFAULT NULL COMMENT '商户id',
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '云码店铺id',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方用户';

--changeset menglei:3
ALTER TABLE `ym_trade` ADD `TYPE` VARCHAR(50)  NOT NULL  DEFAULT 'USER_SCAN'  COMMENT '类型 USER_SCAN用户扫码 MERCHANT_SCAN商家扫码'  AFTER `STATUS`;
ALTER TABLE `ym_third_config` CHANGE `NOTIFY_URL` `NOTIFY_URL` VARCHAR(255)  CHARACTER SET utf8  COLLATE utf8_general_ci  NULL  DEFAULT NULL  COMMENT '支付回调地址';
ALTER TABLE `ym_third_config` ADD `BIND_NOTIFY_URL` VARCHAR(255)  NULL  DEFAULT NULL  COMMENT '云码绑定通知地址'  AFTER `KEY`;
ALTER TABLE `ym_third_config` ADD `ORDER_NOTIFY_URL` VARCHAR(255)  NULL  DEFAULT NULL  COMMENT '下单通知地址'  AFTER `BIND_NOTIFY_URL`;

--changeset menglei:4
INSERT INTO `ym_biz` (`ID`, `NAME`, `CODE`, `RATE`, `SETTLEMENT`, `STATUS`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES
	(3, '京东支付', 'JDPAY', 0.35, '1', 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `ym_biz` (`ID`, `NAME`, `CODE`, `RATE`, `SETTLEMENT`, `STATUS`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES
	(4, '翼支付', 'BESTPAY', 0.30, '1', 0, NULL, NULL, NULL, NULL, 0);

--changeset menglei:5
CREATE TABLE `ym_pay_channel` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CHANNEL` varchar(20) DEFAULT NULL COMMENT '通道商',
  `AREA_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '通道地区 0为默认',
  `AREA_TREE_PATH` varchar(50) DEFAULT NULL,
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付通道配置';
INSERT INTO `ym_pay_channel` (`ID`, `CHANNEL`, `AREA_ID`, `AREA_TREE_PATH`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('1', 'ZHONGMA', '0', ',0,', 'system', '2017-07-31 00:00:00', NULL, NULL, '0');

ALTER TABLE `ym_member` ADD `INTO_STATUS` VARCHAR(20)  NULL  DEFAULT NULL  COMMENT '进件状态'  AFTER `AUDIT_STATUS`;
update ym_member set INTO_STATUS='INTO_SUCCESS';

CREATE TABLE `ym_into_info` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `MEMBER_NO` varchar(50) DEFAULT NULL COMMENT '商户号',
  `OUT_MEMBER_NO` varchar(50) DEFAULT NULL COMMENT '外部商户号',
  `CHANNEL` varchar(50) DEFAULT NULL COMMENT '通道商',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '状态',
  `REMARK` varchar(200) DEFAULT NULL COMMENT '备注',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='云码进件信息(收银台返回)';

--changeset menglei:6
INSERT INTO `ym_biz` (`ID`, `NAME`, `CODE`, `RATE`, `SETTLEMENT`, `STATUS`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES
	(5, 'QQ支付', 'QQPAY', 0.30, '1', 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `ym_biz` (`ID`, `NAME`, `CODE`, `RATE`, `SETTLEMENT`, `STATUS`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES
	(6, '百度支付', 'BAIDUPAY', 0.30, '1', 0, NULL, NULL, NULL, NULL, 0);

--changeset menglei:20170828001
CREATE TABLE `ym_depot` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `QRCODE_NO` varchar(50) DEFAULT NULL COMMENT '绑定二维码编号',
  `QRCODE_URL` varchar(50) DEFAULT NULL COMMENT '二维码地址',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '状态',
  `RECEIVE_STATUS` varchar(20) DEFAULT NULL COMMENT '领取状态',
  `BATCH_NO` varchar(20) DEFAULT NULL COMMENT '批次号',
  `MAIL_NAME` varchar(20) DEFAULT NULL COMMENT '云码邮寄姓名',
  `MAIL_MOBILE` varchar(20) DEFAULT NULL COMMENT '云码邮寄手机号',
  `MAIL_ADDRESS` varchar(20) DEFAULT NULL COMMENT '邮寄地址',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ORCODE_NO` (`QRCODE_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='云码库';

CREATE VIEW `ym_depot_view` AS
select yd.ID,yd.QRCODE_NO,yd.QRCODE_URL,yd.STATUS,yd.RECEIVE_STATUS,yd.REMARK,yd.BATCH_NO,yd.MAIL_NAME,yd.MAIL_MOBILE,yd.MAIL_ADDRESS,
ui.MOBILE,ym.BIND_TIME
from jinfu_cloudcode_pro.ym_depot yd
left join jinfu_cloudcode_pro.ym_member ym on yd.QRCODE_NO=ym.QRCODE_NO
left join jinfu_user_pro.user_inf ui on ym.USER_ID=ui.USER_ID;

--changeset huchao:20170828-payCode/1
CREATE TABLE `pay_code` (
  `PAY_CODE_NO` varchar(32) NOT NULL COMMENT '支付码编号',
  `MOBILE` varchar(24) NOT NULL COMMENT '手机号',
  `BALANCE` decimal(17,0) NOT NULL COMMENT '余额',
  `STATUS` varchar(16) NOT NULL COMMENT '支付码状态',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(16) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`PAY_CODE_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付码';

CREATE TABLE `pay_code_balance_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `PAY_CODE_NO` varchar(32) NOT NULL COMMENT '支付码编号',
  `MOBILE` varchar(24) NOT NULL COMMENT '手机号',
  `TYPE` varchar(16) NOT NULL COMMENT '操作类型',
  `AMOUNT` decimal(17,0) NOT NULL COMMENT '操作金额',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(16) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付码金额变更记录';

--changeset menglei:20170829001
DROP VIEW `ym_depot_view`;
CREATE VIEW `ym_depot_view` AS
select yd.ID,yd.QRCODE_NO,yd.QRCODE_URL,yd.STATUS,yd.RECEIVE_STATUS,yd.REMARK,yd.BATCH_NO,yd.MAIL_NAME,yd.MAIL_MOBILE,yd.MAIL_ADDRESS,
ui.MOBILE,ym.BIND_TIME,ym.ID as YM_ID
from jinfu_cloudcode_pro.ym_depot yd
left join jinfu_cloudcode_pro.ym_member ym on yd.QRCODE_NO=ym.QRCODE_NO
left join jinfu_user_pro.user_inf ui on ym.USER_ID=ui.USER_ID;

--changeset huchao:20170828-payCode/2
ALTER TABLE `pay_code` ADD COLUMN PAY_CODE_URL VARCHAR (128);
ALTER TABLE `pay_code_balance_log` ADD COLUMN SERIAL_NUMBER VARCHAR (64);

--changeset menglei:20170831001
ALTER TABLE `ym_member` ADD `DEALER_USER_ID` VARCHAR(20)  NULL  DEFAULT NULL  COMMENT '推荐人（对应分销员id）'  AFTER `ACTIVATE`;

--changeset menglei:20170831002
DROP VIEW `ym_member_view`;
CREATE VIEW `ym_member_view` AS
SELECT
   `a`.`ID` AS `ID`,
   `a`.`STORE_ID` AS `STORE_ID`,
   `a`.`USER_ID` AS `USER_ID`,
   `a`.`MEMBER_NO` AS `MEMBER_NO`,
   `a`.`QRCODE_NO` AS `QRCODE_NO`,
   `a`.`QRCODE_URL` AS `QRCODE_URL`,
   `a`.`OPEN_ID` AS `OPEN_ID`,
   `a`.`SETTLEMENT` AS `SETTLEMENT`,
   `a`.`BIND_TIME` AS `BIND_TIME`,
   `a`.`BANK_CARD_ID` AS `BANK_CARD_ID`,
   `a`.`STATUS` AS `STATUS`,
   `a`.`DEALER_USER_ID` AS `DEALER_USER_ID`,
   `a`.`CREATE_OPID` AS `CREATE_OPID`,
   `a`.`CREATE_TS` AS `CREATE_TS`,
   `a`.`LAST_MNT_OPID` AS `LAST_MNT_OPID`,
   `a`.`LAST_MNT_TS` AS `LAST_MNT_TS`,
   `a`.`VERSION_CT` AS `VERSION_CT`,
   `b`.`STORE_NAME` AS `STORE_NAME`,
   `b`.`PROVINCE_ID` AS `PROVINCE_ID`,
   `b`.`CITY_ID` AS `CITY_ID`,
   `b`.`AREA_ID` AS `AREA_ID`,
   `b`.`PROVINCE` AS `PROVINCE`,
   `b`.`CITY` AS `CITY`,
   `b`.`AREA` AS `AREA`,
   `c`.`USER_NAME` AS `USER_NAME`,
   `c`.`MOBILE` AS `MOBILE`,
   `d`.`NAME` AS `DEALER_USER_NAME`,
   `d`.`DEALER_ID` AS `DEALER_ID`
FROM `jinfu_cloudcode_pro`.`ym_member` `a`
left join `jinfu_user_pro`.`store_inf` `b` on `a`.`STORE_ID` = `b`.`STORE_ID`
left join `jinfu_user_pro`.`user_inf` `c` on `b`.`USER_ID` = `c`.`USER_ID`
left join `jinfu_dealer_pro`.`dealer_user` `d` on `a`.`DEALER_USER_ID` = `d`.`USER_ID`;

--changeset menglei:20170905001
ALTER TABLE `ym_depot` CHANGE `QRCODE_URL` `QRCODE_URL` VARCHAR(255)  CHARACTER SET utf8  COLLATE utf8_general_ci  NULL  DEFAULT NULL  COMMENT '二维码地址';

--changeset huchao:20170828-payCode/3
ALTER TABLE `pay_code` MODIFY COLUMN `BALANCE` decimal(17,2);
ALTER TABLE `pay_code_balance_log` MODIFY COLUMN `AMOUNT` decimal(17,2);



