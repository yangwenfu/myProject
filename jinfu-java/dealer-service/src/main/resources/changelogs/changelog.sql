--liquibase formatted sql

--changeset jll:1
ALTER TABLE `dealer_user`
  ADD COLUMN `ID_CARD_NO`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号' AFTER `NAME`,
	ADD COLUMN `IDENTITY_AUTH` BIT(1) NULL DEFAULT b'0' COMMENT '实名认证，默认0，1为已认证' AFTER `STATUS`,
	ADD COLUMN `IDENTITY_AUTH_DATE` DATETIME NULL DEFAULT NULL COMMENT '实名时间' AFTER `IDENTITY_AUTH`,
	ADD COLUMN `LIVED` bit(1) NULL DEFAULT b'0' COMMENT '活体检测' AFTER `IDENTITY_AUTH_DATE`,
	ADD COLUMN `YITU_SIMILARITY` DECIMAL(5,2) NULL DEFAULT NULL COMMENT '依图-相似度比对结果' AFTER `LIVED`,
	ADD COLUMN `YITU_IS_PASS` BIT(1) NULL DEFAULT b'0' COMMENT '依图-大礼包比对是否通过' AFTER `YITU_SIMILARITY`,
	ADD COLUMN `ID_FRONT_PIC` VARCHAR(255) NULL DEFAULT '' COMMENT '身份证正面' AFTER `YITU_IS_PASS`,
	ADD COLUMN `ID_BACK_PIC` VARCHAR(255) NULL DEFAULT '' COMMENT '身份证反面' AFTER `ID_FRONT_PIC`,
	ADD COLUMN `LINE_PIC` VARCHAR(255) NULL DEFAULT '' COMMENT '网纹照' AFTER `ID_BACK_PIC`,
	ADD COLUMN `LIVE_PIC` VARCHAR(255) NULL DEFAULT '' COMMENT '活体照片' AFTER `LINE_PIC`,
	ADD COLUMN `IS_PASS` BIT(1) NULL DEFAULT b'0' COMMENT '活体是否通过' AFTER `LIVE_PIC`,
	ADD COLUMN `QR_CODE` VARCHAR(20) NULL DEFAULT '' COMMENT '二维码code' AFTER `IS_PASS`,
	ADD COLUMN `QR_CODE_KEY` VARCHAR(20) NULL DEFAULT '' COMMENT '二维码加密码' AFTER `QR_CODE`;

--changeset menglei:2
ALTER TABLE `dealer_user_order` ADD INDEX `ORDER_NO` (`ORDER_NO`);

--changeset menglei:3
ALTER TABLE `dealer_user` ADD `EXAM_IS_PASS` BIT(1)  NOT NULL  DEFAULT b'0'  COMMENT '首次考试是否通过 默认0，1通过'  AFTER `QR_CODE_KEY`;
ALTER TABLE `dealer` ADD `AUDIT_STATUS` VARCHAR(20)  NOT NULL  DEFAULT 'UN_FIRST_AUDIT'  COMMENT '审核状态 UN_FIRST_AUDIT未初审 UN_LAST_AUDIT未终审 PASS审核通过 REFUSE审核被拒'  AFTER `END_TIME`;
ALTER TABLE `dealer` ADD `REMARK` VARCHAR(255)  NULL  DEFAULT NULL  COMMENT '审核备注'  AFTER `AUDIT_STATUS`;
CREATE TABLE `exam` (
  `EXAM_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) DEFAULT NULL COMMENT '名称',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '类型',
  `URL` varchar(255) DEFAULT NULL COMMENT '地址',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `PASS_LINE` int(11) DEFAULT NULL COMMENT '及格线',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`EXAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考试';
CREATE TABLE `exam_user` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `DEALER_ID` varchar(20) DEFAULT '' COMMENT '分销商id',
  `USER_ID` varchar(20) DEFAULT '' COMMENT '分销员id',
  `EXAM_ID` bigint(20) DEFAULT NULL COMMENT '考试id',
  `SCORE` int(11) DEFAULT NULL COMMENT '成绩',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '状态 PASS及格 FAIL不及格',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考试成绩';
CREATE TABLE `sign_info` (
  `ID` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `DEALER_ID` varchar(20) DEFAULT '' COMMENT '分销商id',
  `USER_ID` varchar(20) DEFAULT NULL COMMENT '分销员id',
  `STORE_ID` bigint(20) DEFAULT NULL COMMENT '店铺id',
  `SIGN_IN_TIME` datetime DEFAULT NULL COMMENT '签到时间',
  `SIGN_IN_STORE_HEADER` varchar(255) DEFAULT NULL COMMENT '签到门头照',
  `SIGN_IN_STORE_INNER` varchar(255) DEFAULT NULL COMMENT '签到店内照',
  `SIGN_IN_LNG` varchar(50) DEFAULT NULL COMMENT '签到经度(横坐标)',
  `SIGN_IN_LAT` varchar(50) DEFAULT NULL COMMENT '签到纬度(纵坐标)',
  `SIGN_IN_ADDRESS` varchar(255) DEFAULT NULL COMMENT '签到地址',
  `SIGN_OUT_TIME` datetime DEFAULT NULL COMMENT '签退时间',
  `SIGN_OUT_HEADER` varchar(255) DEFAULT NULL COMMENT '签退门头照',
  `SIGN_OUT_LNG` varchar(50) DEFAULT NULL COMMENT '签退经度(横坐标)',
  `SIGN_OUT_LAT` varchar(50) DEFAULT NULL COMMENT '签退纬度(纵坐标)',
  `SIGN_OUT_ADDRESS` varchar(255) DEFAULT NULL COMMENT '签退地址',
  `DISTANCE_TIME` varchar(50) DEFAULT NULL COMMENT '时长',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签到签退';

--changeset menglei:4
ALTER TABLE `dealer_extra` ADD `ACCOUNT_LICENCE_PIC` varchar(255)  NULL  DEFAULT NULL  COMMENT '开户许可证'  AFTER `BANK_CARD_NO`;

--changeset menglei:5
ALTER TABLE `exam` ADD `STATUS` VARCHAR(20)  NULL  DEFAULT NULL  COMMENT '状态 DELETE删除 NORMAL正常'  AFTER `PASS_LINE`;

--changeset menglei:6
CREATE TABLE `dealer_op_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OPERATOR` varchar(50) DEFAULT NULL COMMENT '操作人',
  `OP_LOG` varchar(255) DEFAULT NULL COMMENT '操作信息',
  `DEALER_ID` varchar(20) DEFAULT NULL COMMENT '操作分销商id',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '操作类型',
  `FRONT_BASE` longtext COMMENT '修改前快照',
  `AFTER_BASE` longtext COMMENT '修改后快照',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分销商操作日志';

--changeset menglei:7
ALTER TABLE `dealer` ADD `BELONG_ID` VARCHAR(20)  NULL  DEFAULT NULL  COMMENT '责任人'  AFTER `STATUS`;

--changeset menglei:9
DROP VIEW `exam_user_view`;
CREATE VIEW `exam_user_view` AS
select eu.*,e.NAME,e.TYPE,du.NAME as USER_NAME,du.MOBILE as MOBILE,d.DEALER_NAME,d.PROVINCE_ID,d.CITY_ID,d.AREA_ID,d.STREET_ID,d.PROVINCE,d.CITY,d.AREA,d.STREET,d.CREATE_OPID as CREATE_DEALER_OPID,mu.NAME as CREATE_DEALER_OPNAME,d.BELONG_ID as BELONG_ID,if(d.BELONG_ID='0',"总部",mu2.NAME) as BELONG_NAME
from jinfu_dealer_pro.exam_user eu
left join jinfu_dealer_pro.exam e on e.EXAM_ID=eu.EXAM_ID
left join jinfu_dealer_pro.dealer_user du on du.USER_ID=eu.USER_ID
left join jinfu_dealer_pro.dealer d on d.DEALER_ID=eu.DEALER_ID
left join jinfu_mgt_pro.mgt_user mu on mu.USER_ID=d.CREATE_OPID
left join jinfu_mgt_pro.mgt_user mu2 on mu2.USER_ID=d.BELONG_ID;

--changeset menglei:10
ALTER TABLE `store_white_list` ADD `REMARK` VARCHAR(20)  NULL  DEFAULT ''  COMMENT '原因备注'  AFTER `STATUS`;
ALTER TABLE `store_white_list` ADD `USER_ID` VARCHAR(20)  NULL  DEFAULT ''  COMMENT '关联分销员id'  AFTER `DEALER_ID`;
ALTER TABLE `store_white_list` ADD `STREET_ID` VARCHAR(20)  NULL  DEFAULT NULL  COMMENT '街道ID'  AFTER `AREA_ID`;
ALTER TABLE `store_white_list` ADD `STREET` VARCHAR(255)  NULL  DEFAULT NULL  COMMENT '街道'  AFTER `AREA`;
ALTER TABLE `store_white_list` CHANGE `STATUS` `STATUS` VARCHAR(20)  CHARACTER SET utf8  COLLATE utf8_general_ci  NULL  DEFAULT 'NOSIGNIN'  COMMENT '状态';
ALTER TABLE `store_white_list` ADD INDEX `REMARK` (`REMARK`);
ALTER TABLE `store_white_list` ADD INDEX `USER_ID` (`USER_ID`);
ALTER TABLE `store_white_list` DROP INDEX `idx_area`;
ALTER TABLE `store_white_list` ADD INDEX `idx_area` (`PROVINCE_ID`, `CITY_ID`, `AREA_ID`, `STREET_ID`);
update store_white_list set STATUS='NOSIGNIN';

--changeset menglei:11
CREATE TABLE `store_white_sign` (
  `ID` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `DEALER_ID` varchar(20) DEFAULT '' COMMENT '分销商id',
  `USER_ID` varchar(20) DEFAULT NULL COMMENT '分销员id',
  `STORE_ID` bigint(20) DEFAULT NULL COMMENT '白名单店铺id',
  `SIGN_IN_TIME` datetime DEFAULT NULL COMMENT '签到时间',
  `SIGN_IN_STORE_HEADER` varchar(255) DEFAULT NULL COMMENT '签到门头照',
  `SIGN_IN_LNG` varchar(50) DEFAULT NULL COMMENT '签到经度(横坐标)',
  `SIGN_IN_LAT` varchar(50) DEFAULT NULL COMMENT '签到纬度(纵坐标)',
  `SIGN_IN_ADDRESS` varchar(255) DEFAULT NULL COMMENT '签到地址',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='白名单店铺签到';

--changeset menglei:12
update dealer_user set IS_ADMIN=0 where IS_ADMIN is null

--changeset menglei:13
CREATE TABLE `dealer_user_subscribe` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `OPEN_ID` varchar(50) DEFAULT NULL COMMENT 'openid',
  `WECHAT_TYPE` varchar(20) DEFAULT NULL COMMENT '微信类型 云联金服',
  `DEALER_ID` varchar(20) DEFAULT NULL COMMENT '关联分销商ID',
  `USER_ID` varchar(20) DEFAULT NULL COMMENT '关联分销员ID',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信推广公众号关注';

--changeset menglei:14
CREATE VIEW `dealer_user_subscribe_view` AS
select dus.*,d.DEALER_NAME as DEALER_NAME,du.MOBILE as MOBILE,du.NAME as USER_NAME,mu.NAME as BELONG_NAME
from jinfu_dealer_pro.dealer_user_subscribe dus
left join jinfu_dealer_pro.dealer d on dus.DEALER_ID=d.DEALER_ID
left join jinfu_dealer_pro.dealer_user du on dus.USER_ID=du.USER_ID
left join jinfu_mgt_pro.mgt_user mu on mu.USER_ID=d.BELONG_ID;

--changeset menglei:15
DROP VIEW `exam_user_view`;
CREATE VIEW `exam_user_view` AS
select eu.*,e.NAME,e.TYPE,du.NAME as USER_NAME,du.MOBILE as MOBILE,d.DEALER_NAME,d.PROVINCE_ID,d.CITY_ID,d.AREA_ID,d.STREET_ID,d.PROVINCE,d.CITY,d.AREA,d.STREET,d.CREATE_OPID as CREATE_DEALER_OPID,mu.NAME as CREATE_DEALER_OPNAME,d.BELONG_ID as BELONG_ID,if(d.BELONG_ID='0',"总部",mu2.NAME) as BELONG_NAME,
e.START_TIME,e.END_TIME,e.STATUS as EXAM_STATUS,e.CREATE_TS as EXAM_CREATE_TS
from jinfu_dealer_pro.exam_user eu
left join jinfu_dealer_pro.exam e on e.EXAM_ID=eu.EXAM_ID
left join jinfu_dealer_pro.dealer_user du on du.USER_ID=eu.USER_ID
left join jinfu_dealer_pro.dealer d on d.DEALER_ID=eu.DEALER_ID
left join jinfu_mgt_pro.mgt_user mu on mu.USER_ID=d.CREATE_OPID
left join jinfu_mgt_pro.mgt_user mu2 on mu2.USER_ID=d.BELONG_ID;

--changeset jl:2017090201
ALTER TABLE `jinfu_dealer_pro`.`dealer`
  ADD COLUMN `SERVICE_FEE_RT` DECIMAL(10,7) NULL COMMENT '服务费率' AFTER `STATUS`;


