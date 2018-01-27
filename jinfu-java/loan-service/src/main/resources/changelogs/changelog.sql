--liquibase formatted sql

--changeset will:1
ALTER TABLE fp_loan_appl ADD DEALER_USER_ID varchar(20) default null comment '分销员ID' after USER_EXTRA;
ALTER TABLE fp_loan_appl ADD DEALER_ID varchar(20) default null comment '分销商ID' after DEALER_USER_ID;
ALTER TABLE fp_loan_appl ADD TRIAL_USER_ID varchar(50) default null comment '初审人员编号' after DEALER_ID;
ALTER TABLE fp_loan_appl ADD REVIEW_USER_ID varchar(50) default null comment '复审人员编号' after TRIAL_USER_ID;
ALTER TABLE fp_loan_appl ADD HANGUP bit(1) default b'0' comment '挂起状态,1为挂起，0为非挂起' after REVIEW_USER_ID;
ALTER TABLE fp_loan_appl ADD SIGN_TS datetime(6) default null comment '签约时间' after HANGUP;
ALTER TABLE fp_loan_audit ADD `TEMP` bit(1) DEFAULT b'1' COMMENT '是否暂存,1是  0不是' after REASON;
ALTER TABLE fp_loan_audit ADD `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人';
ALTER TABLE fp_loan_audit ADD `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间';
ALTER TABLE fp_loan_audit ADD `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号';
ALTER TABLE fp_loan_audit modify column `TERM_LEN` int(11) DEFAULT '0' COMMENT '授信期限';
drop table if exists fp_loan_appl_user;
CREATE TABLE `fp_loan_appl_user` (
  `APPL_ID` varchar(40) NOT NULL COMMENT '贷款编号',
  `USER_BASE` text COMMENT '用户基础信息',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`APPL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset will:2
update fp_loan_appl a set a.appl_status = '02' where a.appl_status = '03';

--changeset will:3
CREATE TABLE `fp_loan_att` (
  `ID` int(21) NOT NULL AUTO_INCREMENT,
  `APPLY_ID` varchar(40) DEFAULT NULL,
  `UPLOAD_DATE` datetime DEFAULT NULL COMMENT '上传日期',
  `UPLOADER` varchar(255) DEFAULT NULL COMMENT '上传人',
  `FILE_NAME` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `FILE_PATH` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='贷款申请上传附件表';

--changeset will:4
CREATE TABLE `fp_loan_audit_log` (
  `LOG_ID` varchar(40) NOT NULL COMMENT '贷款操作日志编号',
  `APPL_ID` varchar(40) DEFAULT NULL COMMENT '贷款申请编号',
  `USER_NAME` varchar(40) DEFAULT NULL,
  `OPERATE_DT` datetime DEFAULT NULL COMMENT '操作时间',
  `LOG_TYPE` char(2) DEFAULT NULL COMMENT '日志类型',
  `CONTENT` varchar(255) DEFAULT NULL COMMENT '日志内容',
  `CREATE_OPID` varchar(20) DEFAULT NULL,
  `CREATE_TS` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset will:5
update fp_loan_appl a set a.TRIAL_USER_ID = 'UM0000000030' where a.APPL_STATUS in ('11','12','13') and (a.TRIAL_USER_ID = '' or a.TRIAL_USER_ID is null);
update fp_loan_appl a set a.REVIEW_USER_ID = 'UM0000000030' where a.APPL_STATUS in ('11','12','13') and (a.REVIEW_USER_ID = '' or a.REVIEW_USER_ID is null);

--changeset will:6
ALTER TABLE fp_loan_audit modify column `TEMP` bit(1) DEFAULT b'0' COMMENT '是否暂存,1是  0不是';

--changeset will:7
ALTER TABLE fp_loan_audit ADD `AUDIT_USER_ID` varchar(50) DEFAULT null COMMENT '审核人员' after TEMP;

--changeset will:8
ALTER TABLE FP_LOAN_APPL_USER ADD USER_LINKMAN text DEFAULT null COMMENT '联系人' after USER_BASE;
ALTER TABLE FP_LOAN_APPL_USER ADD STORE_BASE text DEFAULT null COMMENT '店铺' after USER_LINKMAN;
ALTER TABLE FP_LOAN_APPL_USER ADD HOUSE_BASE text DEFAULT null COMMENT '家庭' after STORE_BASE;
ALTER TABLE FP_LOAN_APPL_USER ADD CAR_BASE text DEFAULT null COMMENT '车辆' after HOUSE_BASE;
ALTER TABLE FP_LOAN_APPL_USER ADD USER_TOBACCO text DEFAULT null COMMENT '烟草数据' after CAR_BASE;
ALTER TABLE FP_LOAN_APPL_USER ADD CREDIT_PHONE text DEFAULT null COMMENT '信用-通讯' after USER_TOBACCO;
ALTER TABLE FP_LOAN_APPL_USER ADD CREDIT_OVERDUE text DEFAULT null COMMENT '信用-逾期' after CREDIT_PHONE;
ALTER TABLE FP_LOAN_APPL_USER ADD CREDIT_LOAN text DEFAULT null COMMENT '信用-贷款' after CREDIT_OVERDUE;
ALTER TABLE FP_LOAN_APPL_USER ADD CREDIT_BLACKLIST text DEFAULT null COMMENT '信用-黑名单' after CREDIT_LOAN;
ALTER TABLE FP_LOAN_APPL_USER ADD CREDIT_STATISTICS text DEFAULT null COMMENT '信用-统计' after CREDIT_BLACKLIST;

--changeset will:9
ALTER TABLE FP_LOAN_APPL_USER ADD BANK_BASE text DEFAULT null COMMENT '银行信息' after STORE_BASE;

--changeset will:10
ALTER TABLE fp_loan_audit modify column REMARK varchar(1800) DEFAULT null COMMENT '审批备注信息';
ALTER TABLE fp_loan_audit modify column REASON varchar(1800) DEFAULT null COMMENT '审批原因、原因代码';
ALTER TABLE fp_audit_note modify column CONTENT varchar(1800) DEFAULT null COMMENT '核实信息';

--changeset will:11
ALTER TABLE fp_loan_audit_log modify column CONTENT text DEFAULT null COMMENT '日志内容';

--changeset will:12
update fp_loan_appl_user a set a.CAR_BASE = '[]' where a.CAR_BASE is null;
update fp_loan_appl_user a set a.HOUSE_BASE = '[]' where a.HOUSE_BASE is null;
update fp_loan_appl_user a set a.BANK_BASE = '{}' where a.BANK_BASE is null;
update fp_loan_appl_user a set a.BANK_BASE = '{}' where a.BANK_BASE is null;
update fp_loan_appl_user a set a.USER_TOBACCO = '{}' where a.USER_TOBACCO is null;
update fp_loan_appl_user a set a.CREDIT_PHONE = '{}' where a.CREDIT_PHONE is null;
update fp_loan_appl_user a set a.CREDIT_OVERDUE = '{}' where a.CREDIT_OVERDUE is null;
update fp_loan_appl_user a set a.CREDIT_LOAN = '{}' where a.CREDIT_LOAN is null;
update fp_loan_appl_user a set a.CREDIT_STATISTICS = '{}' where a.CREDIT_STATISTICS is null;
update fp_loan_appl_user a set a.CREDIT_BLACKLIST = '{}' where a.CREDIT_BLACKLIST is null;

--changeset will:13
ALTER TABLE fp_loan_audit ADD `AUDIT_DATE` datetime DEFAULT null COMMENT '审核时间' after AUDIT_USER_ID;

--changeset will:14
update fp_loan_audit a set a.AUDIT_USER_ID = a.CREATE_OPID where a.AUDIT_USER_ID is null;
update fp_loan_audit a set a.AUDIT_DATE = a.CREATE_TS where a.AUDIT_DATE is null;

--changeset will:15
ALTER TABLE fp_loan_appl ADD CHANNEL varchar(10) default null comment '渠道,SELF:掌柜,JUHE:聚合' after HANGUP;

--changeset will:16
update prod_inf a set a.PROD_NAME = '云速贷' where a.PROD_ID = 'L01001';
update prod_inf a set a.PROD_NAME = '云随贷' where a.PROD_ID = 'L01002';
update fp_loan_dtl a set a.LOAN_NAME = '云速贷' where a.PRODUCT_ID = 'L01001';
update fp_loan_dtl a set a.LOAN_NAME = '云随贷' where a.PRODUCT_ID = 'L01002';

--changeset sephy:1
ALTER TABLE `fp_loan_appl` ADD COLUMN `TRACE_ID` varchar(32) DEFAULT '' COMMENT '追踪 ID' AFTER `CREATE_TS`;

--changeset will:17
CREATE TABLE `fp_loan_coupon` (
  `REPAY_ID` varchar(50) NOT NULL COMMENT '还款编号',
  `LOAN_ID` varchar(50) DEFAULT NULL,
  `USER_ID` varchar(50) DEFAULT NULL,
  `COUPON_ID` bigint(20) NOT NULL COMMENT '券编号',
  `PRICE` decimal(10,2) DEFAULT NULL COMMENT '券金额',
  `COUPON_NAME` varchar(50) DEFAULT NULL COMMENT '券名称',
  `COUPON_TYPE` varchar(50) DEFAULT NULL COMMENT '券类型 ',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`COUPON_ID`),
  KEY `idx_repayId` (`REPAY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset will:18
ALTER TABLE `fp_loan_coupon`
MODIFY COLUMN `COUPON_ID`  bigint(20) NOT NULL COMMENT '券ID' AFTER `USER_ID`,
ADD COLUMN `COUPON_CODE`  varchar(30) NULL COMMENT '券码' AFTER `COUPON_ID`;

--changeset will:19
ALTER TABLE `ac_pay_recv_ord`
ADD COLUMN `RET_CHANNEL_CODE`  varchar(50) NULL COMMENT '收银台收付渠道标识' AFTER `RET_MSG`;
ALTER TABLE `ac_pay_recv_ord`
ADD COLUMN `RET_CHANNEL_NAME`  varchar(50) NULL COMMENT '收银台收付渠道名称' AFTER `RET_CHANNEL_CODE`;

--changeset jll:1
ALTER TABLE `feedback`
ADD COLUMN `READ`  bit(1) NULL DEFAULT 0 AFTER `CONTENT`;

--changeset will:22
CREATE TABLE `BALANCE_OUTLINE` (
`ID`  int(12) NOT NULL AUTO_INCREMENT COMMENT '编号' ,
`GENERATE_DATE`  datetime(6) NULL COMMENT '日期' ,
`REPAY_COUNT`  int(12) NULL COMMENT '还款笔数' ,
`REPAY_AMT`  decimal(17,2) NULL COMMENT '还款总金额' ,
`BALANCE_STATUS`  char(2) NULL default '0' COMMENT '对账状态,0未对账,1已对账',
`BALANCE_DATE`  datetime(6) NULL COMMENT '对账日期',
`BALANCE_USER_ID`  varchar(40) NULL COMMENT '操作人',
`CREATE_OPID`  varchar(20) NULL ,
`CREATE_TS`  datetime(6) NULL ,
`LAST_MNT_OPID`  varchar(40) NULL ,
`LAST_MNT_TS`  datetime(6) NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
;
CREATE TABLE `BALANCE_DETAIL` (
`ID`  int(12) NOT NULL AUTO_INCREMENT COMMENT '编号' ,
`REPAY_DATE`  datetime(6) NULL COMMENT '还款日期' ,
`REPAY_ID`  varchar(50) NULL COMMENT '还款编号' ,
`REPAY_AMT`  decimal(17,2) NULL COMMENT '还款金额',
`REPAY_STATUS`  char(1) NULL COMMENT '还款状态',
`CHANNEL_NAME`  varchar(50) NULL COMMENT '通道',
`BALANCE_DATE`  datetime(6) NULL COMMENT '对账日期',
`USER_ID`  varchar(40) NULL COMMENT '客户编号',
`USER_NAME`  varchar(255) NULL COMMENT '客户名称',
`LOAN_ID`  varchar(40) NULL COMMENT '贷款编号',
`PROD_NAME`  varchar(40) NULL COMMENT '产品名称',
`BALANCE_STATUS`  char(1) NULL default '0' COMMENT '勾对状态,0未勾对,1已勾对',
`CREATE_OPID`  varchar(20) NULL ,
`CREATE_TS`  datetime(6) NULL ,
`LAST_MNT_OPID`  varchar(40) NULL ,
`LAST_MNT_TS`  datetime(6) NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
;
CREATE TABLE `BALANCE_CASHIER` (
`ID`  int(12) NOT NULL AUTO_INCREMENT COMMENT '编号' ,
`PAY_DATE`  datetime(6) NULL COMMENT '支付日期' ,
`BIZ_ID`  varchar(40) NULL COMMENT '商户订单号' ,
`PAY_AMT`  decimal(17,2) NULL COMMENT '支付金额' ,
`PAY_STATUS`  char(1) NULL COMMENT '1表示成功，代收对账文件中有且只有支付成功的交易记录' ,
`BALANCE_STATUS`  char(1) NULL default '0' COMMENT '勾对状态,0未勾对,1已勾对',
`CHANNEL_NAME`  varchar(50) NULL COMMENT '通道' ,
`BALANCE_DATE`  datetime(6) NULL COMMENT '对账日期' ,
`CREATE_OPID`  varchar(20) NULL ,
`CREATE_TS`  datetime(6) NULL ,
`LAST_MNT_OPID`  varchar(40) NULL ,
`LAST_MNT_TS`  datetime(6) NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
;

--changeset will:23
ALTER TABLE `balance_detail`
ADD COLUMN `OUTLINE_ID`  int(12) NULL COMMENT '对账概要编号' AFTER `ID`;

--changeset will:24
ALTER TABLE `balance_detail`
DROP COLUMN `USER_NAME`;

--changeset will:25
ALTER TABLE `fp_repay_dtl`
ADD COLUMN `SURPLUS_CAPTIAL`  decimal(17,2) NULL COMMENT '剩余本金' AFTER `REPAY_FINE`;
ALTER TABLE `balance_cashier`
ADD COLUMN `OUTLINE_ID`  int(12) NULL COMMENT '对账概要编号' AFTER `ID`;
ALTER TABLE `balance_outline`
ADD COLUMN `IS_AUTOED`  bit(1) NULL DEFAULT 0 COMMENT '是否已经自动勾对过' AFTER `BALANCE_DATE`;

--changeset will:26
ALTER TABLE `ac_acct`
COMMENT='小贷账户表';
ALTER TABLE `ac_credit_line_rsrv_dtl`
COMMENT='小贷额度占用';
ALTER TABLE `ac_pay_recv_ord`
COMMENT='小贷收付指令';
ALTER TABLE `balance_cashier`
COMMENT='小贷收银台账单';
ALTER TABLE `balance_detail`
COMMENT='小贷对账详情';
ALTER TABLE `balance_outline`
COMMENT='小贷对账概要';
ALTER TABLE `fp_audit_note`
COMMENT='审核核实信息';
ALTER TABLE `fp_loan_appl`
COMMENT='贷款申请表';
ALTER TABLE `fp_loan_appl_ext`
COMMENT='贷款申请时的用户信息拷贝';
ALTER TABLE `fp_loan_appl_user`
COMMENT='贷款申请用户信息拷贝(会在一些特定行为时进行更新)';
ALTER TABLE `fp_loan_att`
COMMENT='贷款申请上传附件表';
ALTER TABLE `fp_loan_audit`
COMMENT='贷款申请审核';
ALTER TABLE `fp_loan_audit_log`
COMMENT='审核日志';
ALTER TABLE `fp_loan_coupon`
COMMENT='还款用券表';
ALTER TABLE `fp_loan_dtl`
COMMENT='贷款表';
ALTER TABLE `fp_loan_promo`
COMMENT='小贷促销信息';
ALTER TABLE `fp_repay_dtl`
COMMENT='还款详情';
ALTER TABLE `fp_repay_schd`
COMMENT='还款计划表';
ALTER TABLE `link_repay_schd`
COMMENT='还款计划与还款的关联表';
ALTER TABLE `prod_inf`
COMMENT='小贷产品信息表';
ALTER TABLE `prod_credit_type`
COMMENT='信用类产品详情';

--changeset will:27
ALTER TABLE `balance_outline`
ADD COLUMN `VERSION_CT`  decimal(17,0) NULL AFTER `LAST_MNT_TS`;
ALTER TABLE `balance_detail`
ADD COLUMN `VERSION_CT`  decimal(17,0) NULL AFTER `LAST_MNT_TS`;
ALTER TABLE `balance_cashier`
ADD COLUMN `VERSION_CT`  decimal(17,0) NULL AFTER `LAST_MNT_TS`;


--changeset will:20
ALTER TABLE `fp_repay_dtl`
ADD COLUMN `REPAY_TYPE`  char(2) NULL COMMENT '还款类型,01提前还全部,02提前还本期,03随借随还,04逾期还款,05等额本息还单期' AFTER `STATUS`;

--changeset will:21
ALTER TABLE `fp_repay_dtl`
ADD COLUMN `REPAY_DATE`  datetime(6) NULL COMMENT '实际还款时间' AFTER `TRANS_MODE`;
update fp_repay_dtl a set a.REPAY_DATE = a.CREATE_TS;

--changeset will:28
ALTER TABLE `balance_cashier`
MODIFY COLUMN `PAY_STATUS`  char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1表示成功，代收对账文件中有且只有支付成功的交易记录' AFTER `PAY_AMT`;
ALTER TABLE `balance_detail`
MODIFY COLUMN `BALANCE_STATUS`  char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '勾对状态,0未勾对,1已勾对' AFTER `PROD_NAME`;

--changeset will:29
ALTER TABLE `balance_cashier`
MODIFY COLUMN `PAY_STATUS`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1表示成功，代收对账文件中有且只有支付成功的交易记录' AFTER `PAY_AMT`;

--changeset bright:1
CREATE TABLE `EXTERNAL_NBCB_LOAN` (
	`ORDER_NO` VARCHAR (20) NOT NULL COMMENT '订单号',
	`USER_ID` VARCHAR (40) NOT NULL COMMENT '金服USER ID',
	`RECEIVE_STATUS` VARCHAR (20) NULL COMMENT '订单状态：接受成功、接受失败',
	`APPR_STATUS` VARCHAR (40) NULL COMMENT '审批状态：审批通过额度生效、审批不通过',
	`CREDIT_DEAD_LINE` datetime NULL COMMENT '授信结束时间（额度到期的时间）',
	`CREDIT_STATUS` VARCHAR (40) NULL COMMENT '额度状态（正常、失效）',
	`CREDIT` DECIMAL (17, 2) NULL DEFAULT 0.0 COMMENT '授信金额',
	`LOAN_REMAINING` DECIMAL (17, 2) NULL DEFAULT 0.0 COMMENT '贷款余额',
	`LOAN_REMAINING_AVG` DECIMAL (17, 2) NULL DEFAULT 0.0 COMMENT '贷款余额年日均',
	`LOAN_STATUS` VARCHAR (40) NULL COMMENT '结清标志（结清、未结清正常、未结清逾期）',
	`MODIFY_TS` datetime NULL COMMENT '最后修改日期',
	PRIMARY KEY (`ORDER_NO`)
);

--changeset will:21
ALTER TABLE `fp_repay_dtl`
ADD COLUMN `REPAY_DATE`  datetime(6) NULL COMMENT '实际还款时间' AFTER `TRANS_MODE`;
update fp_repay_dtl a set a.REPAY_DATE = a.CREATE_TS;

--changeset will:30
CREATE TABLE `finance_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资金方编号',
  `name` varchar(100) DEFAULT NULL COMMENT '资金方名称',
  `stype` char(2) DEFAULT NULL COMMENT '资金方类型',
  `open` tinyint(1) DEFAULT '0' COMMENT '资金开关',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
INSERT INTO `jinfu_loan_pro`.`finance_source` (`id`, `name`, `stype`, `open`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `CREATE_OPID`, `CREATE_TS`) VALUES ('1', '自有资金', '1', '0', '', NULL, NULL, NULL);
INSERT INTO `jinfu_loan_pro`.`finance_source` (`id`, `name`, `stype`, `open`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `CREATE_OPID`, `CREATE_TS`) VALUES ('2', '爱投资', '2', '1', NULL, NULL, NULL, NULL);

--changeset will:31
ALTER TABLE `fp_loan_appl`
ADD COLUMN `FINANCE_SOURCE_ID`  int(11) NULL COMMENT '资金来源编号' AFTER `SIGN_TS`;
ALTER TABLE `fp_loan_dtl`
ADD COLUMN `FINANCE_SOURCE_ID`  int(11) NULL COMMENT '资金来源编号' AFTER `LOAN_STAT`;

--changeset will:32
CREATE TABLE `fp_loan_appl_out` (
  `ID` int(11) NOT NULL COMMENT '主键',
  `APPL_ID` varchar(40) NOT NULL COMMENT '申请编号',
  OUT_TRADE_NO varchar(40) not null comment '外部关联订单号',
  stype char(2) not null comment '外部订单类型',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) AUTO_INCREMENT = 1 ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='贷款申请外部编号表';

--changeset will:33
ALTER TABLE `fp_loan_appl_out`
MODIFY COLUMN `ID`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键' FIRST ;
CREATE TABLE `fp_loan_appl_out_bankcard` (
  `ID` int(11) NOT NULL COMMENT '主键' auto_increment,
  `APPL_ID` varchar(40) NOT NULL COMMENT '申请编号',
  `BANK_CARD_NO` varchar(40) NOT NULL COMMENT '银行卡号',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='贷款申请外部银行卡信息表';

--changeset will:34
CREATE TABLE `fp_loan_appl_out_user` (
  `ID` int(11) NOT NULL COMMENT '主键' auto_increment,
  `USER_ID` varchar(40) NOT NULL COMMENT '金服用户编号',
  `OUT_USER_ID` varchar(40) NOT NULL COMMENT '外部用户编号',
  `STYPE` char(2) not null comment '外部用户类型',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小贷外部用户关联表';

--changeset will:35
ALTER TABLE `finance_source`
ADD COLUMN `VERSION_CT`  decimal(17,0) NULL AFTER `CREATE_TS`;

--changeset will:36
ALTER TABLE `finance_source`
MODIFY COLUMN `stype`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资金方类型' AFTER `name`;

--changeset will:37
update jinfu_loan_pro.finance_source a set a.stype = 'OWN' where a.id = 1;
update jinfu_loan_pro.finance_source a set a.stype = 'AITOUZI' where a.id = 2;

--changeset will:38
CREATE TABLE `fp_loan_appl_out_audit` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `APPL_ID` varchar(40) NOT NULL COMMENT '贷款申请编号',
  `STYPE` varchar(10) NOT NULL COMMENT '类型',
	`TERM_LEN` varchar(10) NOT NULL COMMENT '期限长度',
	`LOAN_AMT` decimal(12,2) NOT NULL COMMENT '审核金额',
  `RESULT` varchar(10) NOT NULL COMMENT '审核结果',
	`REASON` varchar(100) NOT NULL COMMENT '审核原因说明',
	`commissions` DECIMAL(12,2) default 0 COMMENT '手续费',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小贷外部系统审核表';

--changeset will:39
ALTER TABLE `fp_loan_appl_out_audit`
ADD COLUMN `paymentOption`  int(11) NULL COMMENT '同意还款方式' AFTER `commissions`;

--changeset will:40
ALTER TABLE `fp_loan_appl_out_audit`
MODIFY COLUMN `REASON`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '审核原因说明' AFTER `RESULT`;

--changeset will:41
ALTER TABLE `fp_loan_appl_out_bankcard`
ADD COLUMN `USER_ID`  varchar(40) NULL COMMENT '用户编号' AFTER `APPL_ID`;

--changeset will:42
ALTER TABLE `fp_loan_appl_out_audit`
MODIFY COLUMN `TERM_LEN`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '期限长度' AFTER `STYPE`,
MODIFY COLUMN `LOAN_AMT`  decimal(12,2) NULL COMMENT '审核金额' AFTER `TERM_LEN`,
MODIFY COLUMN `RESULT`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '审核结果' AFTER `LOAN_AMT`,
MODIFY COLUMN `CREATE_OPID`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人' AFTER `paymentOption`,
MODIFY COLUMN `CREATE_TS`  datetime(6) NULL COMMENT '创建时间' AFTER `CREATE_OPID`;

--changeset songfengl:43
CREATE TABLE `fp_loan_out_risk` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(40) DEFAULT '' COMMENT '金服用户id',
  `APPLY_ID` varchar(40) DEFAULT NULL COMMENT '内部贷款申请编号',
  `MERCHANT_LEVEL` int(255) DEFAULT NULL COMMENT '店铺档位',
  `MERCHANT_TYPE` int(255) DEFAULT NULL COMMENT 'MERCHANT_TYPE',
  `TARGET_MARKET_TYPE` int(255) DEFAULT NULL COMMENT '市场类型',
  `CUSTOMER_TYPE` int(255) DEFAULT NULL COMMENT '用户类型',
  `OWNER_COMPANY_NAME` varchar(255) DEFAULT NULL COMMENT '所属公司',
  `OPERATION_YEARS` int(255) DEFAULT NULL COMMENT '经营年限',
  `ACTIVE_MONTHS_IN_LAST_YEAR` int(255) DEFAULT NULL COMMENT '近１２个月内有订烟的月份数',
  `ACTIVE_MONTHS_IN_LAST_QUARTER` int(255) DEFAULT NULL COMMENT '近3个月内有订烟的月份数',
  `AVERAGE_MONTHLY_AMOUNT` decimal(12,2) DEFAULT NULL COMMENT '月均订烟额',
  `VARIANCE` decimal(12,2) DEFAULT NULL COMMENT '订烟额波动率',
  `ACTIVE_DEGREE` decimal(12,2) DEFAULT NULL COMMENT '最近月份的订烟活跃度',
  `SAME_OWNER_FLAG` int(2) DEFAULT NULL COMMENT '申请人是否与烟草系统中的负责人一致',
  `SAME_CONTACT_FLAG` int(2) DEFAULT NULL COMMENT '申请人是否与烟草系统中的负责人联系电话一致',
  `SAME_ARRESS_FLAG` int(2) DEFAULT NULL COMMENT '经营地址是否与烟草系统中的店铺地址一致',
  `STYPE` varchar(10) DEFAULT NULL COMMENT '外部系统类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset bright:2
CREATE TABLE `NBCB_REGION` (
`id`  int NOT NULL AUTO_INCREMENT ,
`city_id`  varchar(255) NULL ,
`city_name`  varchar(255) NULL ,
`enabled`  bit NULL ,
PRIMARY KEY (`id`)
);
INSERT INTO `NBCB_REGION`(city_id, city_name, enabled) VALUES ('853','苏州市', 1), ('811','南京市', 1);

--changeset will:43
update jinfu_loan_pro.fp_loan_appl a set a.FINANCE_SOURCE_ID = 1 where a.FINANCE_SOURCE_ID is null;
update jinfu_loan_pro.fp_loan_dtl a set a.FINANCE_SOURCE_ID = 1 where a.FINANCE_SOURCE_ID is null;


--changeset songfengl:44
CREATE TABLE `fp_loan_appl_out_refundsadvance` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `APPLY_ID` varchar(40) DEFAULT NULL COMMENT '内部贷款申请编号',
  `OUT_REFUNDS_ADVANCE_ID` varchar(40) DEFAULT NULL COMMENT '提前还款编号',
  `BALANCE` decimal(10,0) DEFAULT NULL COMMENT '还款计划未还金额',
  `AMOUNT` decimal(10,0) DEFAULT NULL COMMENT '提前还款总额',
  `CAPITAL` decimal(10,0) DEFAULT NULL  COMMENT '剩余本金',
  `INTEREST` decimal(10,0) DEFAULT NULL COMMENT '新的利息',
   `DISABLED` tinyint(4) DEFAULT '0',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='小贷外部系统提前还款申请记录';


--changeset bright:1
CREATE TABLE `EXTERNAL_NBCB_LOAN` (
	`ORDER_NO` VARCHAR (20) NOT NULL COMMENT '订单号',
	`USER_ID` VARCHAR (40) NOT NULL COMMENT '金服USER ID',
	`RECEIVE_STATUS` VARCHAR (20) NULL COMMENT '订单状态：接受成功、接受失败',
	`APPR_STATUS` VARCHAR (40) NULL COMMENT '审批状态：审批通过额度生效、审批不通过',
	`CREDIT_DEAD_LINE` datetime NULL COMMENT '授信结束时间（额度到期的时间）',
	`CREDIT_STATUS` VARCHAR (40) NULL COMMENT '额度状态（正常、失效）',
	`CREDIT` DECIMAL (17, 2) NULL DEFAULT 0.0 COMMENT '授信金额',
	`LOAN_REMAINING` DECIMAL (17, 2) NULL DEFAULT 0.0 COMMENT '贷款余额',
	`LOAN_REMAINING_AVG` DECIMAL (17, 2) NULL DEFAULT 0.0 COMMENT '贷款余额年日均',
	`LOAN_STATUS` VARCHAR (40) NULL COMMENT '结清标志（结清、未结清正常、未结清逾期）',
	`MODIFY_TS` datetime NULL COMMENT '最后修改日期',
	PRIMARY KEY (`ORDER_NO`)
);

--changeset bright:2
CREATE TABLE `NBCB_REGION` (
`id`  int NOT NULL AUTO_INCREMENT ,
`city_id`  varchar(255) NULL ,
`city_name`  varchar(255) NULL ,
`enabled`  bit NULL ,
PRIMARY KEY (`id`)
);
INSERT INTO `NBCB_REGION`(city_id, city_name, enabled) VALUES ('853','苏州市', 1), ('811','南京市', 1);

--changeset will:44
ALTER TABLE `fp_loan_dtl`
ADD COLUMN `TEST_SOURCE`  char(2) NULL DEFAULT 'A' COMMENT 'ABTEST路径' AFTER `FINANCE_SOURCE_ID`;

--changeset will:46
ALTER TABLE `fp_loan_appl`
ADD COLUMN `TEST_SOURCE`  char(2) NULL DEFAULT 'A' COMMENT 'ABTEST路径' AFTER `FINANCE_SOURCE_ID`;

--changeset will:47
ALTER TABLE `fp_user_credit_line`
MODIFY COLUMN `CREDIT_LINE_TOTAL`  decimal(17,2) NULL DEFAULT 0 COMMENT '授信额度' AFTER `USER_ID`,
MODIFY COLUMN `CREDIT_LINE_AVALIABLE`  decimal(17,2) NULL DEFAULT 0 COMMENT '可用额度' AFTER `CREDIT_LINE_TOTAL`;

--changeset songfenglai:45
ALTER TABLE `fp_loan_appl_out_refundsadvance`
MODIFY COLUMN `BALANCE` decimal(10,2) DEFAULT NULL COMMENT '还款计划未还金额',
MODIFY COLUMN `AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '提前还款总额',
MODIFY COLUMN `CAPITAL` decimal(10,2) DEFAULT NULL  COMMENT '剩余本金',
MODIFY COLUMN `INTEREST` decimal(10,2) DEFAULT NULL COMMENT '新的利息';
--changeset dongfangchao:201707181732
CREATE TABLE `loan_carbank_order` (
  `CB_ORDER_NO` varchar(255) NOT NULL COMMENT '订单编号',
  `USER_ID` varchar(255) DEFAULT NULL COMMENT '金服用户ID',
  `USER_NAME` varchar(255) DEFAULT NULL COMMENT '金服用户名',
  `WISH_ORDER_MOBILE` varchar(255) DEFAULT NULL COMMENT '意向单中的手机号',
  `CITY_ID` int(11) DEFAULT NULL COMMENT '城市ID',
  `CITY_NAME` varchar(255) DEFAULT NULL COMMENT '城市名',
  `VEHICLE_BRAND_ID` int(11) DEFAULT NULL COMMENT '汽车品牌ID',
  `VEHICLE_BRAND_NAME` varchar(255) DEFAULT NULL COMMENT '汽车品牌名',
  `VEHICLE_SERIES_ID` int(11) DEFAULT NULL COMMENT '汽车车系ID',
  `VEHICLE_SERIES_NAME` varchar(255) DEFAULT NULL COMMENT '汽车车系名称',
  `VEHICLE_MODEL_ID` int(11) DEFAULT NULL COMMENT '车型ID',
  `VEHICLE_MODEL_NAME` varchar(255) DEFAULT NULL COMMENT '车型名称',
  `VEHICLE_REGISTER_DATE` date DEFAULT NULL COMMENT '车辆登记日期',
  `LOAN_AMT` decimal(17,2) DEFAULT NULL COMMENT '贷款金额',
  `TERM_LEN` int(6) DEFAULT NULL COMMENT '贷款期限',
  `OUT_TRADE_NO` varchar(255) DEFAULT NULL COMMENT '车闪贷订单号',
  `ORDER_STATUS` varchar(255) DEFAULT NULL COMMENT '订单状态',
  `REASON` varchar(255) DEFAULT NULL COMMENT '取消或违约原因',
  `OVERDUE` tinyint(1) DEFAULT NULL COMMENT '是否已经逾期',
  `ISSUE_LOAN_DATE` date DEFAULT NULL COMMENT '放款日期',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`CB_ORDER_NO`),
  UNIQUE KEY `uindex` (`OUT_TRADE_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车闪贷订单表';

--changeset dongfangchao:201707191032
ALTER TABLE `loan_carbank_order`
MODIFY COLUMN `OVERDUE`  tinyint(1) NULL DEFAULT 0 COMMENT '是否已经逾期' AFTER `REASON`;

--changeset songfenglai:45
ALTER TABLE `fp_loan_appl_out_refundsadvance`
MODIFY COLUMN `BALANCE` decimal(10,2) DEFAULT NULL COMMENT '还款计划未还金额',
MODIFY COLUMN `AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '提前还款总额',
MODIFY COLUMN `CAPITAL` decimal(10,2) DEFAULT NULL  COMMENT '剩余本金',
MODIFY COLUMN `INTEREST` decimal(10,2) DEFAULT NULL COMMENT '新的利息';

--changeset will:45
CREATE TABLE `FP_USER_CREDIT_LINE` (
  `USER_ID` varchar(50) NOT NULL COMMENT '用户编号',
	`CREDIT_LINE_TOTAL` DECIMAL(17,2) NOT NULL COMMENT '授信额度',
	`CREDIT_LINE_AVALIABLE` DECIMAL(17,2) NOT NULL COMMENT '可用额度',
	`CREDIT_LINE_DYNAMIC` DECIMAL(17,2) NULL DEFAULT 0.00 COMMENT '动态额度',
	`STATUS` CHAR(2) NULL DEFAULT '0' COMMENT '0 计算中 1未使用 2已使用 3已失效',
	`CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='用户贷款额度表';

--changeset songfengl:44
CREATE TABLE `fp_loan_appl_out_refundsadvance` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `APPLY_ID` varchar(40) DEFAULT NULL COMMENT '内部贷款申请编号',
  `OUT_REFUNDS_ADVANCE_ID` varchar(40) DEFAULT NULL COMMENT '提前还款编号',
  `BALANCE` decimal(10,0) DEFAULT NULL COMMENT '还款计划未还金额',
  `AMOUNT` decimal(10,0) DEFAULT NULL COMMENT '提前还款总额',
  `CAPITAL` decimal(10,0) DEFAULT NULL  COMMENT '剩余本金',
  `INTEREST` decimal(10,0) DEFAULT NULL COMMENT '新的利息',
   `DISABLED` tinyint(4) DEFAULT '0',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小贷外部系统提前还款申请记录';

--changeset will:44
ALTER TABLE `fp_loan_dtl`
ADD COLUMN `TEST_SOURCE`  char(2) NULL DEFAULT 'A' COMMENT 'ABTEST路径' AFTER `FINANCE_SOURCE_ID`;

--changeset will:46
ALTER TABLE `fp_loan_appl`
ADD COLUMN `TEST_SOURCE`  char(2) NULL DEFAULT 'A' COMMENT 'ABTEST路径' AFTER `FINANCE_SOURCE_ID`;

--changeset will:47
ALTER TABLE `fp_user_credit_line`
MODIFY COLUMN `CREDIT_LINE_TOTAL`  decimal(17,2) NULL DEFAULT 0 COMMENT '授信额度' AFTER `USER_ID`,
MODIFY COLUMN `CREDIT_LINE_AVALIABLE`  decimal(17,2) NULL DEFAULT 0 COMMENT '可用额度' AFTER `CREDIT_LINE_TOTAL`;

--changeset songfenglai:45
ALTER TABLE `fp_loan_appl_out_refundsadvance`
MODIFY COLUMN `BALANCE` decimal(10,2) DEFAULT NULL COMMENT '还款计划未还金额',
MODIFY COLUMN `AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '提前还款总额',
MODIFY COLUMN `CAPITAL` decimal(10,2) DEFAULT NULL  COMMENT '剩余本金',
MODIFY COLUMN `INTEREST` decimal(10,2) DEFAULT NULL COMMENT '新的利息';

--changeset will:44
ALTER TABLE `fp_loan_dtl`
ADD COLUMN `TEST_SOURCE`  char(2) NULL DEFAULT 'A' COMMENT 'ABTEST路径' AFTER `FINANCE_SOURCE_ID`;

--changeset will:46
ALTER TABLE `fp_loan_appl`
ADD COLUMN `TEST_SOURCE`  char(2) NULL DEFAULT 'A' COMMENT 'ABTEST路径' AFTER `FINANCE_SOURCE_ID`;

--changeset will:47
ALTER TABLE `fp_user_credit_line`
MODIFY COLUMN `CREDIT_LINE_TOTAL`  decimal(17,2) NULL DEFAULT 0 COMMENT '授信额度' AFTER `USER_ID`,
MODIFY COLUMN `CREDIT_LINE_AVALIABLE`  decimal(17,2) NULL DEFAULT 0 COMMENT '可用额度' AFTER `CREDIT_LINE_TOTAL`;

--changeset will:48
ALTER TABLE `fp_loan_dtl`
ADD COLUMN `IS_DEPOSITORY`  tinyint(1) NULL DEFAULT 0 COMMENT '是否走存管' AFTER `FINANCE_SOURCE_ID`;

--changeset will:49
CREATE TABLE `fp_loan_depository_acct` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACCT_NO` varchar(20) NOT NULL COMMENT '存管账户编号',
  `BANK_CARD_ID` bigint(20) NOT NULL COMMENT '银行卡编号',
  `BANK_CARD_NO` varchar(40) DEFAULT NULL COMMENT '银行卡编号',
  `ID_CARD_NO` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `name` varchar(20) DEFAULT NULL  COMMENT '姓名',
  `mobile` varchar(20) DEFAULT NULL  COMMENT '手机号',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小贷存管账户表';

--changeset will:50
ALTER TABLE `fp_loan_depository_acct`
ADD UNIQUE INDEX `fx_depository_bank_id` (`BANK_CARD_ID`) ,
ADD UNIQUE INDEX `fx_depository_bank_no` (`BANK_CARD_NO`);

--changeset will:51
ALTER TABLE `fp_loan_depository_acct`
DROP INDEX `fx_depository_bank_no`;

--changeset jll:2017083001
ALTER TABLE `prod_credit_type`
	ADD COLUMN `SERVICE_FEE` VARCHAR(10) NULL COMMENT '期初服务费' AFTER `VIOLATE_VALUE`,
	ADD COLUMN `SERVICE_FEE_MONTH` VARCHAR(50) NULL COMMENT '月综合浮动费率' AFTER `SERVICE_FEE`;
INSERT INTO `jinfu_loan_pro`.`prod_inf` (`PROD_ID`, `PROD_NAME`, `PROD_TYPE`, `TERM_LEN`, `TERM_TYPE`, `REPAY_MODE`, `INTR_RT`, `INTR_RT_TYPE`, `PREPAY`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`) VALUES ('L01003', '小烟贷', '01', '6,12', '3', '08', 0.01000000, 'MONTH', b'1', 'system', '2016-09-06 10:58:26.000000', '', '2016-09-19 11:00:07.000000');
INSERT INTO `jinfu_loan_pro`.`prod_credit_type` (`PROD_ID`, `MIN_INTR_DAYS`, `LOAN_AMT_MAX`, `LOAN_AMT_MIN`, `REPAY_AMT_MIN`, `VIOLATE_TYPE`, `VIOLATE_VALUE`, `SERVICE_FEE`, `SERVICE_FEE_MONTH`, `FINE_TYPE`, `FINE_VALUE`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`) VALUES ('L01003', 5, 500000.00, 15000.00, 1000.00, '01', 0.00000000, '0.03', '0.02,0.05,0.08', '1', '0.00050', 'SYSTEM', '2016-09-06 11:03:28.000000', '', '2016-09-19 11:00:41.000000');

--changeset JL:2017083001
ALTER TABLE `jinfu_loan_pro`.`fp_loan_appl`
  ADD COLUMN `APPR_SERVICE_FEE_MONTH_RT` DECIMAL(10,8) DEFAULT 0 NULL COMMENT '批准月综合服务费率' AFTER `APPR_TERM_LEN`,
  ADD COLUMN `SERVICE_FEE_RT` DECIMAL(10,8) DEFAULT 0 NULL COMMENT '咨询服务费率' AFTER `LOAN_RT_TYPE`,
  ADD COLUMN `SERVICE_FEE_MONTH_RT` DECIMAL(10,8) DEFAULT 0 NULL COMMENT '月综合服务费率' AFTER `SERVICE_FEE_RT`;

ALTER TABLE `jinfu_loan_pro`.`fp_loan_dtl`
  ADD COLUMN `SERVICE_FEE_RT` DECIMAL(10,8) DEFAULT 0 NULL COMMENT '咨询服务费率' AFTER `LOAN_RT_TYPE`,
  ADD COLUMN `SERVICE_FEE_MONTH_RT` DECIMAL(10,8) DEFAULT 0 NULL COMMENT '月综合服务费率' AFTER `SERVICE_FEE_RT`;

--changeset jll:2017083101
  ALTER TABLE `prod_credit_type`
	CHANGE COLUMN `SERVICE_FEE` `SERVICE_FEE_RT` VARCHAR(10) NULL DEFAULT NULL COMMENT '期初服务费' AFTER `VIOLATE_VALUE`,
	CHANGE COLUMN `SERVICE_FEE_MONTH` `SERVICE_FEE_MONTH_RT` VARCHAR(50) NULL DEFAULT NULL COMMENT '月综合浮动费率' AFTER `SERVICE_FEE_RT`;

--changeset jl:2017090401
CREATE TABLE `jinfu_loan_pro`.`DEALER_SERVICE_FEE`(
  `ID` VARCHAR(20) NOT NULL COMMENT 'ID',
  `LOAN_ID` VARCHAR(20) NOT NULL COMMENT '贷款单号',
  `BANK_CARD_ID` VARCHAR(20) NOT NULL COMMENT '银行卡号',
  `LOAN_AMT` DECIMAL(17,2) NOT NULL COMMENT '贷款金额',
  `SERVICE_FEE_RT` DECIMAL(10,8) NOT NULL COMMENT '服务费比率',
  `SERVICE_FEE` DECIMAL(17,2) NOT NULL COMMENT '服务费',
  `STATUS` VARCHAR(10) NOT NULL COMMENT '状态',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
);

--changeset jll:2017090401
UPDATE `jinfu_loan_pro`.`prod_inf` SET `PROD_ID`='L01006' WHERE  `PROD_ID`='L01003';
UPDATE `jinfu_loan_pro`.`prod_credit_type` SET `PROD_ID`='L01006', `SERVICE_FEE_MONTH_RT`='0.00' WHERE  `PROD_ID`='L01003';

--changeset jll:2017090601
UPDATE `jinfu_loan_pro`.`prod_inf` SET `TERM_LEN`='12,6' WHERE  `PROD_ID`='L01006';
ALTER TABLE `fp_loan_appl`
	ADD COLUMN `RISK_SCORE` DOUBLE NULL COMMENT '评分' AFTER `TEST_SOURCE`,
	ADD COLUMN `RISK_RESULT` VARCHAR(50) NULL COMMENT '决策建议' AFTER `RISK_SCORE`;

--changeset jll:2017090602
ALTER TABLE `prod_inf`
	ADD COLUMN `DEFAULT_AMT` INT NULL COMMENT '默认金额' AFTER `PREPAY`;
UPDATE `jinfu_loan_pro`.`prod_inf` SET `DEFAULT_AMT`=100000 WHERE  `PROD_ID`='L01006';


--changeset JL:2017090601
ALTER TABLE `jinfu_loan_pro`.`dealer_service_fee`
  ADD  UNIQUE INDEX `UNIQUE_LOAN_ID` (`LOAN_ID`);

--changeset JL:2017090801
UPDATE `jinfu_loan_pro`.`prod_credit_type` SET `LOAN_AMT_MIN`=10000.00 WHERE  `PROD_ID`='L01006';
UPDATE `jinfu_loan_pro`.`prod_inf` SET `TERM_LEN`='6,12' WHERE  `PROD_ID`='L01006';
