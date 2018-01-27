--liquibase formatted sql

--changeset sephy:3
ALTER TABLE `user_operation_log` ADD COLUMN `TRACE_ID` varchar(32) DEFAULT '' COMMENT '追踪 ID' AFTER `CONTENT`;

--changeset sephy:2
ALTER TABLE `user_ext`
MODIFY COLUMN `STREET`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '街道' AFTER `AREA`,
MODIFY COLUMN `ADDRESS`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址' AFTER `STREET_ID`;
ALTER TABLE `user_linkman`
MODIFY COLUMN `NAME`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名' AFTER `USER_ID`;

--changeset sephy:4
CREATE TABLE `user_label` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` varchar(50) NOT NULL COMMENT '用户id',
  `LABEL_TYPE` varchar(64) DEFAULT NULL COMMENT '标签类别',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime NOT NULL COMMENT '创建时间',
  `VERSION_CT` decimal(17,0) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COMMENT='用户标签信息';

--changeset jll:3
ALTER TABLE `store_inf`
ADD COLUMN `INDUSTRY_MCC`  varchar(20) NOT NULL DEFAULT 5227 COMMENT '行业类别码' AFTER `DEL_REASON`,
ADD COLUMN `LICENCE`  varchar(50) NULL COMMENT '许可证' AFTER `INDUSTRY_MCC`;

--changeset dongfangchao:1
CREATE TABLE `corp_bank` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(50) NOT NULL,
  `OPENING_BANK` varchar(255) DEFAULT NULL COMMENT '开户行',
  `BANK_BRANCH` varchar(255) DEFAULT NULL COMMENT '网点',
  `ACCT_NAME` varchar(255) DEFAULT NULL COMMENT '账户名',
  `ACCOUNT` varchar(255) DEFAULT NULL COMMENT '账号',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uindex` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对公账号信息表';

--changeset jll:5
ALTER TABLE `user_linkman`
  ADD COLUMN `DATE_TYPE`  varchar(20) NULL DEFAULT 0 COMMENT '数据类型：0用户添加1为风控添加' AFTER `ADDRESS`;

--changeset dongfangchao:201705191007
CREATE TABLE `user_sign_in_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(50) DEFAULT NULL,
  `SIGN_IN_DATE` date DEFAULT NULL COMMENT '签到日期',
  PRIMARY KEY (`ID`),
  KEY `uindex` (`USER_ID`,`SIGN_IN_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户签到表';

CREATE TABLE `user_scoreboard` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(50) NOT NULL COMMENT '用户id',
  `TOTAL_SCORE` int(11) DEFAULT NULL COMMENT '总积分',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uindex` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户积分表';

CREATE TABLE `user_score_changelog` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(50) NOT NULL,
  `CHANGED_SCORE` int(11) NOT NULL COMMENT '变化积分（增或减）',
  `SOURCE` varchar(255) NOT NULL COMMENT '积分来源',
  `TRAN_SEQ` varchar(255) NOT NULL COMMENT '积分流水号',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uindex` (`SOURCE`,`TRAN_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分变化日志表';


--changeset dongfangchao:201705221023
ALTER TABLE `corp_bank` ADD COLUMN `BANK_SHORT_NAME`  varchar(255) NULL COMMENT '银行简称（如ICBC）' AFTER `USER_ID`;


--changeset menglei:1
ALTER TABLE `store_inf` ADD `SCORE` INT(11)  NOT NULL  DEFAULT '0'  COMMENT '地址信息获得的分数'  AFTER `UUID`;
CREATE TABLE `sys_config` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(20) DEFAULT NULL COMMENT '类型',
  `SCORE` int(11) DEFAULT NULL COMMENT '分数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置';
INSERT INTO `sys_config` (`ID`, `TYPE`, `SCORE`) VALUES ('1', 'STORE_ADDRESS', '2500');

--changeset will:1
ALTER TABLE `user_contract`
ADD COLUMN `signed_mark` int(11) NULL DEFAULT 0 comment '当前签署状态, 0:合同初始化,1:爱投资盖章已完成,2:手动签署已完成' AFTER `BS_VATECODE`;

--changeset menglei:2
ALTER TABLE `store_inf` ADD `LOCATION_SOURCE` VARCHAR(20)  NULL  DEFAULT 'CONVERT'  COMMENT '坐标来源 CONVERT=地址转换 LOCATION=坐标获取'  AFTER `LAT`;

--changeset menglei:2
ALTER TABLE `store_inf` ADD `LOCATION_SOURCE` VARCHAR(20)  NULL  DEFAULT 'CONVERT'  COMMENT '坐标来源 CONVERT=地址转换 LOCATION=坐标获取'  AFTER `LAT`;

--changeset will:1
ALTER TABLE `user_contract`
ADD COLUMN `signed_mark` int(11) NULL DEFAULT 0 comment '当前签署状态, 0:合同初始化,1:爱投资盖章已完成,2:手动签署已完成' AFTER `BS_VATECODE`;

--changeset will:2
INSERT INTO `jinfu_user_pro`.`contract_best_sign_cfg` (`ID`, `CNTRCT_TMPLT_TYPE`, `SIGN_TYPE`, `PAGENUM`, `SIGNX`, `SIGNY`)
VALUES ('7', 'ATZ_LOAN', 'ENTERPRISE', '1', '0.5', '0.5');
INSERT INTO `jinfu_user_pro`.`contract_best_sign_cfg` (`ID`, `CNTRCT_TMPLT_TYPE`, `SIGN_TYPE`, `PAGENUM`, `SIGNX`, `SIGNY`)
VALUES ('8', 'ATZ_WITHHOLD', 'ENTERPRISE', '1', '0.5', '0.5');

--changeset will:3
INSERT INTO `jinfu_user_pro`.`contract_best_sign_cfg`
(`ID`, `CNTRCT_TMPLT_TYPE`, `SIGN_TYPE`, `PAGENUM`, `SIGNX`, `SIGNY`) VALUES ('9', 'ATZ_LOAN', 'PERSONAL', '1', '0.3', '0.3');
INSERT INTO `jinfu_user_pro`.`contract_best_sign_cfg`
(`ID`, `CNTRCT_TMPLT_TYPE`, `SIGN_TYPE`, `PAGENUM`, `SIGNX`, `SIGNY`) VALUES ('10', 'ATZ_WITHHOLD', 'PERSONAL', '1', '0.3', '0.3');

--changeset jll:20170701101
ALTER TABLE `store_inf`
	ALTER `BIZ_LICENCE` DROP DEFAULT;
ALTER TABLE `store_inf`
	CHANGE COLUMN `BIZ_LICENCE` `BIZ_LICENCE` VARCHAR(50) NULL COMMENT '营业执照' AFTER `TOBACCO_END_DATE`;

--changeset jll:2017071801
ALTER TABLE `user_inf`
	ADD COLUMN `AB_TEST` VARCHAR(20) NULL AFTER `SOURCE`;

--changeset jll:2017072801
ALTER TABLE `user_ext`
	ADD COLUMN `HOUSE_NUM` INT(5) NULL DEFAULT NULL COMMENT '房产数量' AFTER `LOAN_NUM`,
	ADD COLUMN `CAR_NUM` INT(5) NULL DEFAULT NULL COMMENT '车辆数量' AFTER `HOUSE_NUM`,
	ADD COLUMN `CREDIT_CARD_NUM` INT(5) NULL DEFAULT NULL COMMENT '信用卡张数' AFTER `CAR_NUM`;

--changeset jll:2017080401
ALTER TABLE `user_inf`
	ADD COLUMN `DEALER_USER_ID` VARCHAR(20) NULL  COMMENT '分销员id'  AFTER `AB_TEST`;

--changeset jll:2017080901
ALTER TABLE `user_inf`
	ADD COLUMN `SOURCE_NAME` VARCHAR(20) NULL COMMENT '数据来源名称' AFTER `SOURCE`;

--changeset jll:2017090401
ALTER TABLE `contract_best_sign_cfg`
	ADD COLUMN `SEAL_NAME` VARCHAR(50) NULL DEFAULT NULL COMMENT '公章名称' AFTER `SIGN_TYPE`;

--changeset jll:2017090501
CREATE TABLE IF NOT EXISTS `contract_best_company` (
  `ID` int(21) NOT NULL AUTO_INCREMENT,
  `CNTRCT_TMPLT_TYPE` varchar(50) DEFAULT NULL COMMENT '合同类型',
  `NAME` varchar(100) DEFAULT NULL COMMENT '姓名',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `MOBILE` varchar(20) DEFAULT NULL COMMENT '手机号',
  `SIGN_TYPE` varchar(50) DEFAULT NULL COMMENT '签名类型，公司或个人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='合同签署用户';

INSERT INTO `contract_best_company` (`ID`, `CNTRCT_TMPLT_TYPE`, `NAME`, `EMAIL`, `MOBILE`, `SIGN_TYPE`) VALUES
	(18, 'ZXL01006', '宁波华青商务信息咨询有限公司', '91330203MA2934G32Q', '13738855770', 'ENTERPRISE');

