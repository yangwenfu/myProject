--liquibase formatted sql

--changeset dongfangchao:201707030945

CREATE TABLE `gl_seq` (
  `SEQ_NAME` varchar(100) NOT NULL COMMENT '序列名称',
  `CURR_VAL` int(11) NOT NULL COMMENT '当前值',
  `STEP` int(11) NOT NULL COMMENT '递增值',
  `SEQ_TYPE` varchar(1) NOT NULL COMMENT '重置类型',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`SEQ_NAME`),
  KEY `CREATE_TS` (`CREATE_TS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_sign_in_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(50) DEFAULT NULL,
  `SIGN_IN_DATE` date DEFAULT NULL COMMENT '签到日期',
  PRIMARY KEY (`ID`),
  KEY `uindex` (`USER_ID`,`SIGN_IN_DATE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户签到表';

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
) ENGINE=InnoDB AUTO_INCREMENT=372 DEFAULT CHARSET=utf8 COMMENT='积分变化日志表';

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

--changeset dongfangchao:201707031045
CREATE TABLE `score_dict` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SCORE_CODE` varchar(255) DEFAULT NULL COMMENT '积分值编码',
  `SCORE_VALUE` int(11) DEFAULT NULL COMMENT '积分值',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uindex` (`SCORE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `activity_dict` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVITY_CODE` varchar(255) DEFAULT NULL COMMENT '活动编码',
  `ACTIVITY_NAME` varchar(255) DEFAULT NULL COMMENT '活动名称',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uindex` (`ACTIVITY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset dongfangchao:201707041124
ALTER TABLE `user_sign_in_log`
ADD COLUMN `CON_DAYS`  int(11) NULL COMMENT '连续签到天数' AFTER `SIGN_IN_DATE`;

--changeset dongfangchao:201707070931
INSERT INTO `jinfu_point_pro`.`activity_dict`
(`ID`, `ACTIVITY_CODE`, `ACTIVITY_NAME`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES
('1', 'CON_SEVEN_DAYS_SIGN_IN', '连续七日签到', 'system', '2017-07-03 14:45:17', NULL, NULL, '0'),
('2', 'DAILY_SIGN_IN', '日常签到', 'system', '2017-07-03 15:43:23', NULL, NULL, '0'),
('3', 'CHECK_IN_RAFFLE', '签到抽奖', 'system', '2017-07-06 09:25:45', NULL, NULL, '0');

INSERT INTO `jinfu_point_pro`.`score_dict`
(`ID`, `SCORE_CODE`, `SCORE_VALUE`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES
('1', 'P_ONE', '1', 'system', '2017-07-03 14:44:08', NULL, NULL, '0'),
('2', 'P_TEN', '10', 'system', '2017-07-06 09:27:13', NULL, NULL, '0'),
('3', 'P_TWENTY', '20', 'system', '2017-07-06 09:27:58', NULL, NULL, '0'),
('4', 'P_THIRTY', '30', 'system', '2017-07-06 09:28:41', NULL, NULL, '0'),
('5', 'P_FORTY', '40', 'system', '2017-07-06 09:29:07', NULL, NULL, '0'),
('6', 'P_FIFTY', '50', 'system', '2017-07-06 09:29:29', NULL, NULL, '0'),
('7', 'P_HUNDRED', '100', 'system', '2017-07-06 09:30:00', NULL, NULL, '0');

--changeset dongfangchao:20170724
ALTER TABLE `user_sign_in_log`
ADD COLUMN `CREATE_OPID`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人' AFTER `CON_DAYS`,
ADD COLUMN `CREATE_TS`  datetime NULL COMMENT '创建时间' AFTER `CREATE_OPID`,
ADD COLUMN `LAST_MNT_OPID`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后修改人' AFTER `CREATE_TS`,
ADD COLUMN `LAST_MNT_TS`  datetime NULL DEFAULT NULL COMMENT '最后修改时间' AFTER `LAST_MNT_OPID`,
ADD COLUMN `VERSION_CT`  decimal(17,0) NULL DEFAULT NULL COMMENT '版本号' AFTER `LAST_MNT_TS`;