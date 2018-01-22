--liquibase formatted sql

--changeset sephy:1
CREATE TABLE `rule_coupon` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROMO_ID` bigint(20) NOT NULL,
  `COUPON_NAME` varchar(50) NOT NULL COMMENT '优惠券名称',
  `COUPON_CODE` varchar(50) DEFAULT NULL COMMENT '优惠券code',
  `COUPON_TYPE` varchar(50) NOT NULL COMMENT '优惠类型',
  `PRICE` decimal(10,2) NOT NULL COMMENT '优惠券金额',
  `IS_ADDED` bit(1) NOT NULL COMMENT '是否叠加',
  `VALID_DAYS` int(5) DEFAULT NULL COMMENT '有效日',
  `VALID_BEGIN_DATE` datetime DEFAULT NULL COMMENT '有效起始日期',
  `VALID_END_DATE` datetime DEFAULT NULL COMMENT '有效结束日期',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='优惠券';

--changeset sephy:2
CREATE TABLE `user_coupon` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(50) NOT NULL COMMENT '用户id',
  `PROMO_ID` bigint(20) NOT NULL,
  `COUPON_NAME` varchar(50) NOT NULL COMMENT '优惠券名称',
  `COUPON_TYPE` varchar(50) NOT NULL COMMENT '优惠类型',
  `PRICE` decimal(10,2) NOT NULL COMMENT '优惠券金额',
  `IS_ADDED` bit(1) NOT NULL COMMENT '是否叠加',
  `VALID_BEGIN_DATE` datetime DEFAULT NULL COMMENT '有效起始日期',
  `VALID_END_DATE` datetime DEFAULT NULL COMMENT '有效结束日期',
  `PROD_ID` varchar(20) NOT NULL COMMENT '产品ID',
  `MINIMUM` decimal(10,2) NOT NULL COMMENT '最小满足金额',
  `STATUS` varchar(20) NOT NULL COMMENT '状态',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='个人优惠卷';

--changeset sephy:3
ALTER TABLE `rule_coupon`
  MODIFY COLUMN `IS_ADDED`  bit(1) NOT NULL COMMENT '是否叠加(0为不可叠加，1为可叠加)' AFTER `PRICE`;
ALTER TABLE `user_coupon`
  MODIFY COLUMN `IS_ADDED`  bit(1) NOT NULL COMMENT '是否叠加(0为不可叠加，1为可叠加)' AFTER `PRICE`,
  MODIFY COLUMN `STATUS`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态(unused:未使用,used:已使用,overdue:已过期)' AFTER `MINIMUM`;

--changeset sephy:4
ALTER TABLE `user_coupon`
  ADD COLUMN `COUPON_CODE`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '优惠券编号' AFTER `COUPON_NAME`;

--changeset sephy:5
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
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset sephy:6
ALTER TABLE `user_coupon`
ADD COLUMN `PROD_TYPE_ID`  int(11) NOT NULL COMMENT '产品分类' AFTER `VALID_END_DATE`;

--changeset jll:1
CREATE TABLE IF NOT EXISTS `recommend` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(50) NOT NULL COMMENT '被推荐人用户id',
  `REFEREE_USER_ID` varchar(50) NOT NULL COMMENT '推荐人用户id',
  `IS_ORDER` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否办理业务',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_ID` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='推荐表';

--changeset jll:20170526
ALTER TABLE `user_coupon`
ADD COLUMN `TRADE_NO`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发券编号' AFTER `STATUS`,
ADD UNIQUE INDEX `TRADE_NO` (`TRADE_NO`);
