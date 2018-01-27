--liquibase formatted sql

--changeset will:1
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('60', 'BEFORE_APPLY_LIST', '申请列表', '申请列表', '17', ',1,17,60,', '1', 'system', '2016-12-08 09:53:55.000000', 'system', '2016-12-08 10:23:17.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('61', 'BEFORE_APPLY_ACQUIRE', '初审领单', '初审领单', '17', ',1,17,61,', '1', 'system', '2016-12-08 09:53:55.000000', 'system', '2016-12-08 10:23:17.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('62', 'BEFORE_TRIAL_LIST', '初审', '初审', '17', ',1,17,62,', '1', 'system', '2016-12-08 09:53:55.000000', 'system', '2016-12-08 10:23:17.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('63', 'BEFORE_REVIEW_LIST', '终审', '终审', '17', ',1,17,63', '1', 'system', '2016-12-08 09:53:55.000000', 'system', '2016-12-08 10:23:17.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('64', 'DURING_SIGN_LIST', '签约', '签约', '17', ',1,17,64,', '1', 'system', '2016-12-08 09:53:55.000000', 'system', '2016-12-08 10:23:17.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('65', 'DURING_TRANSFER_LIST', '放款', '放款', '17', ',1,17,65,', '1', 'system', '2016-12-08 09:53:55.000000', 'system', '2016-12-08 10:23:17.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('66', 'AFTER_LOAN_LIST', '贷后已贷业务列表', '贷后已贷业务列表', '17', ',1,17,66,', '1', 'system', '2016-12-08 09:53:55.000000', 'system', '2016-12-08 10:23:17.000000', '0');
update `mgt_permission` a set a.`PERM_PARENT` = '65', a.`PERM_TREE_PATH`= ',1,17,65,21,' where a.`PERMISSION_ID` = '21';
update `mgt_permission` a set a.`PERM_PARENT` = '66', a.`PERM_TREE_PATH`= ',1,17,66,22,' where a.`PERMISSION_ID` = '22';
insert into mgt_role_perm(role_id, permission_id) values(1, 60);
insert into mgt_role_perm(role_id, permission_id) values(1, 61);
insert into mgt_role_perm(role_id, permission_id) values(1, 62);
insert into mgt_role_perm(role_id, permission_id) values(1, 63);
insert into mgt_role_perm(role_id, permission_id) values(1, 64);
insert into mgt_role_perm(role_id, permission_id) values(1, 65);
insert into mgt_role_perm(role_id, permission_id) values(1, 66);

--changeset will:2
update product a set a.PROD_NAME = '云速贷' where a.PROD_ID = 'L01001';
update product a set a.PROD_NAME = '云随贷' where a.PROD_ID = 'L01002';

--changeset will:3
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('71', 'AUDIT_ASSIGN', '审核人员分配', '审核人员分配', '61', ',1,17,61,71,', '1', 'system', '2017-4-12 11:22:15.000000', 'system', '2017-4-12 11:22:15.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('72', 'LOAN_SEARCH_PERSON', '审核人员筛选', '审核人员筛选', '17', ',1,17,72,', '1', 'system', '2017-4-12 11:22:15.000000', 'system', '2017-4-12 11:22:15.000000', '0');

INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('73', 'GENERAL_LIST', '贷款综合查询列表', '贷款综合查询列表', '17', ',1,17,73,', '1', 'system', '2017-4-12 11:22:15.000000', 'system', '2017-4-12 11:22:15.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('74', 'GENERAL_EXPORT', '贷款综合列表导出', '贷款综合列表导出', '73', ',1,17,73,74', '1', 'system', '2017-4-12 11:22:15.000000', 'system', '2017-4-12 11:22:15.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('75', 'GENERAL_SMOKE', '订烟数据', '订烟数据', '73', ',1,17,73,75', '1', 'system', '2017-4-12 11:22:15.000000', 'system', '2017-4-12 11:22:15.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('76', 'GENERAL_CREDIT', '征信报告', '征信报告', '73', ',1,17,73,76', '1', 'system', '2017-4-12 11:22:15.000000', 'system', '2017-4-12 11:22:15.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('77', 'GENERAL_AUDIT_LOG', '审批信息', '审批信息', '73', ',1,17,73,77', '1', 'system', '2017-4-12 11:22:15.000000', 'system', '2017-4-12 11:22:15.000000', '0');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('78', 'GENERAL_FILE', '其他附件查看', '其他附件查看', '73', ',1,17,73,78', '1', 'system', '2017-4-12 11:22:15.000000', 'system', '2017-4-12 11:22:15.000000', '0');

-- 订烟数据，征信报告,审批信息，其他附件查看

-- changeset will:4
insert into mgt_role_perm(role_id, permission_id) values(1, 71);
insert into mgt_role_perm(role_id, permission_id) values(1, 72);
insert into mgt_role_perm(role_id, permission_id) values(1, 73);
insert into mgt_role_perm(role_id, permission_id) values(1, 74);
insert into mgt_role_perm(role_id, permission_id) values(1, 75);
insert into mgt_role_perm(role_id, permission_id) values(1, 76);
insert into mgt_role_perm(role_id, permission_id) values(1, 77);
insert into mgt_role_perm(role_id, permission_id) values(1, 78);

--changeset dongfangchao:1
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('67', 'GOODS_CODE', '商品码', '商品码', '1', ',1,67,', '1', 'system', '2017-03-28 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('68', 'GOODS_CODE_GEN_BATCH', '批量生码', '批量生码', '67', ',1,67,68,', '64', 'system', '2017-03-28 16:01:57');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('69', 'GOODS_MGT', '商品管理', '商品管理', '67', ',1,67,69,', '65', 'system', '2017-03-28 16:02:45');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('70', 'GOODS_CODE_MGT', '商品码管理', '商品码管理', '67', ',1,67,70,', '66', 'system', '2017-03-28 16:03:51');

--changeset jll:1
ALTER TABLE `cms_article`
ADD COLUMN `VIEWS_COUNT`  int(255) NULL DEFAULT 0 COMMENT '阅读数' AFTER `ORDERS`,
ADD COLUMN `UPS_COUNT`  int(255) NULL DEFAULT 0 COMMENT '点赞数' AFTER `VIEWS_COUNT`;


--changeset dongfangchao:2
ALTER TABLE `prod_app_detail`
ADD COLUMN `PROMO_TAG_LEFT`  varchar(255) NULL COMMENT '促销角标左' AFTER `CFG_HOT_ORDER`,
ADD COLUMN `PROMO_TAG_RIGHT`  varchar(255) NULL COMMENT '促销角标右' AFTER `PROMO_TAG_LEFT`,
ADD COLUMN `PROD_TITLE`  varchar(255) NULL COMMENT '产品标题' AFTER `PROMO_TAG_RIGHT`,
ADD COLUMN `PROD_SUB_TITLE_LEFT`  varchar(255) NULL COMMENT '副标题左' AFTER `PROD_TITLE`,
ADD COLUMN `PROD_SUB_TITLE_RIGHT`  varchar(255) NULL COMMENT '副标题右' AFTER `PROD_SUB_TITLE_LEFT`;

--changeset dongfangchao:3
ALTER TABLE `prod_app_detail`
DROP COLUMN `PROMO_TAG_LEFT`,
DROP COLUMN `PROMO_TAG_RIGHT`,
ADD COLUMN `PROMO_TAG`  varchar(255) NULL COMMENT '促销角标' AFTER `CFG_HOT_ORDER`,
ADD COLUMN `PROMO_DESC`  varchar(255) NULL COMMENT '促销文字' AFTER `PROMO_TAG`;

--changeset will:5
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('79', 'GENERAL_RISK', '评估报告', '评估报告', '73', ',1,17,73,79', '1', 'system', '2017-4-12 11:22:15.000000', 'system', '2017-4-12 11:22:15.000000', '0');
insert into mgt_role_perm(role_id, permission_id) values(1, 79);

--changeset dongfangchao:4
INSERT INTO `jinfu_mgt_pro`.`sys_area_inf` (`ID`, `NAME`, `FULL_NAME`, `TREE_PATH`, `PARENT`, `GB_CODE`, `ORDERS`) VALUES ('1373', '黄岛区', '山东省青岛市黄岛区', ',1357,1369,', '1369', '370211', NULL);

--changeset menglei:1
UPDATE mgt_permission SET PERM_TREE_PATH = CONCAT(PERM_TREE_PATH,',') WHERE PERMISSION_ID IN (74,75,76,77,78,79);

INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('80', 'EXAM_MGT', '考试管理', '考试管理', '2', ',1,2,80,', '1', 'system', '2017-05-08 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('81', 'SIGN_MGT', '签到管理', '签到管理', '2', ',1,2,81,', '1', 'system', '2017-05-08 16:00:52');

--changeset jll:2
CREATE TABLE IF NOT EXISTS `channel_user_area` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(50) NOT NULL,
  `PROVINCE_ID` bigint(20) NOT NULL COMMENT '省',
  `CITY_ID` bigint(20) DEFAULT NULL COMMENT '市',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '地区最子级id',
  `AREA_TREE_PATH` varchar(50) NOT NULL COMMENT '地区路径',
  `PROVINCE` varchar(64) NOT NULL COMMENT '省',
  `CITY` varchar(64) DEFAULT NULL COMMENT '市',
  `AREA` varchar(64) DEFAULT NULL COMMENT '区',
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道用户负责地区表';

CREATE TABLE IF NOT EXISTS `channel_user_relation` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PARENT_USER_ID` varchar(50) NOT NULL,
  `USER_ID` varchar(50) NOT NULL,
  `CREATE_OPID` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TS` datetime NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset menglei:2
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('82', 'CHAN_MEM_MGT', '渠道人员管理', '渠道人员管理', '2', ',1,2,82,', '3', 'system', '2017-05-08 16:00:52');

--changeset jll:3
INSERT INTO `mgt_permission` (`PERMISSION_ID`,`PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`)
VALUES ('83','MALL_USER_FEEDBACK', '用户反馈', '用户反馈', 10, ',1,10,16,', 18, 'system', '2016-12-08 09:49:34', 'system', '2016-12-08 10:23:17');

--changeset menglei:3
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('84', 'DT_AUDIT_MGT', '分销商准入审批', '分销商准入审批', '2', ',1,2,84,', '4', 'system', '2017-05-10 16:00:52');

--changeset menglei:4
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('85', 'DT_UPDATE', '分销商编辑', '分销商编辑', '3', ',1,2,3,85,', '1', 'system', '2017-05-11 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('86', 'DT_EXPORT', '分销商导出', '分销商导出', '3', ',1,2,3,86,', '2', 'system', '2017-05-11 16:00:52');

INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('87', 'DT_USER_IMPORT', '分销人员导入', '分销人员导入', '3', ',1,2,3,87,', '3', 'system', '2017-05-11 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('88', 'DT_USER_CREATE', '分销人员添加', '分销人员添加', '3', ',1,2,3,88,', '4', 'system', '2017-05-11 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('89', 'DT_USER_UPDATE', '分销人员编辑', '分销人员编辑', '3', ',1,2,3,89,', '5', 'system', '2017-05-11 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('90', 'DT_USER_DELETE', '分销人员删除', '分销人员删除', '3', ',1,2,3,90,', '6', 'system', '2017-05-11 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('91', 'DT_USER_RESET', '分销人员重置密码', '分销人员重置密码', '3', ',1,2,3,91,', '7', 'system', '2017-05-11 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('92', 'DT_USER', '分销人员查看', '分销人员查看', '3', ',1,2,3,92,', '8', 'system', '2017-05-11 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('93', 'DT_USER_EXPORT', '分销人员导出', '分销人员导出', '3', ',1,2,3,93,', '9', 'system', '2017-05-11 16:00:52');

INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('94', 'DT_OP_LOG', '分销商操作日志', '分销商操作日志', '3', ',1,2,3,94,', '10', 'system', '2017-05-11 16:00:52');

INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('95', 'CHAN_MEM_SAVE_AREA', '渠道人员配置区域', '渠道人员配置区域', '82', ',1,2,82,95,', '1', 'system', '2017-05-11 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('96', 'CHAN_MEM_SAVE_USER', '渠道人员配置城市经理', '渠道人员配置城市经理', '82', ',1,2,82,96,', '2', 'system', '2017-05-11 16:00:52');

--changeset menglei:5
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('97', 'DT_RESET', '分销商重新提交', '分销商重新提交', '3', ',1,2,3,97,', '11', 'system', '2017-05-11 16:00:52');

--changeset dongfangchao:201705181745
ALTER TABLE `notice_platform` ADD COLUMN `NOTICE_POSITION`  varchar(255) NULL COMMENT '公告位置' AFTER `NOTICE_PLATFORM`;
--changeset dongfangchao:5
CREATE TABLE `industry` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IND_NAME` varchar(255) DEFAULT NULL COMMENT '行业名称',
  `IND_TYPE` varchar(255) DEFAULT NULL COMMENT '行业大类',
  `IND_DESC` varchar(255) DEFAULT NULL COMMENT '适用范围',
  `BIZ_LICENSE` tinyint(1) DEFAULT '1' COMMENT '是否需要营业执照',
  `LICENCE_NAME` varchar(255) DEFAULT NULL COMMENT '许可证名称',
  `MCC` varchar(255) DEFAULT NULL COMMENT '商品类别码',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uindex` (`MCC`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='行业表';
INSERT INTO `industry` (`ID`, `IND_NAME`, `IND_TYPE`, `IND_DESC`, `BIZ_LICENSE`, `LICENCE_NAME`, `MCC`) VALUES ('1', '服装零售', '专门零售', '各类服饰及饰物店', '1', NULL, '5699');
INSERT INTO `industry` (`ID`, `IND_NAME`, `IND_TYPE`, `IND_DESC`, `BIZ_LICENSE`, `LICENCE_NAME`, `MCC`) VALUES ('2', '餐饮餐馆', '餐饮', '就餐场所和餐馆（包括快餐）', '1', '餐饮服务许可证', '5812');
INSERT INTO `industry` (`ID`, `IND_NAME`, `IND_TYPE`, `IND_DESC`, `BIZ_LICENSE`, `LICENCE_NAME`, `MCC`) VALUES ('3', 'KTV', '娱乐', '歌舞厅、KTV', '1', '文化经营许可证', '7911');
INSERT INTO `industry` (`ID`, `IND_NAME`, `IND_TYPE`, `IND_DESC`, `BIZ_LICENSE`, `LICENCE_NAME`, `MCC`) VALUES ('4', '网吧', '娱乐', '大型游戏机和游戏场所', '1', '网络文化经营许可证', '7994');
INSERT INTO `industry` (`ID`, `IND_NAME`, `IND_TYPE`, `IND_DESC`, `BIZ_LICENSE`, `LICENCE_NAME`, `MCC`) VALUES ('5', '理发', '商业服务', '理发店', '1', NULL, '7230');
INSERT INTO `industry` (`ID`, `IND_NAME`, `IND_TYPE`, `IND_DESC`, `BIZ_LICENSE`, `LICENCE_NAME`, `MCC`) VALUES ('6', '美容SPA', '娱乐', '美容、SPA', '1', '特种行业许可证', '7298');
INSERT INTO `industry` (`ID`, `IND_NAME`, `IND_TYPE`, `IND_DESC`, `BIZ_LICENSE`, `LICENCE_NAME`, `MCC`) VALUES ('7', '五金店', '专门零售', '五金商店', '1', NULL, '5251');
INSERT INTO `industry` (`ID`, `IND_NAME`, `IND_TYPE`, `IND_DESC`, `BIZ_LICENSE`, `LICENCE_NAME`, `MCC`) VALUES ('8', '食品零售', '专门零售', '各类食品店及专门食品零售店', '1', '食品流通许可证', '5499');
INSERT INTO `industry` (`ID`, `IND_NAME`, `IND_TYPE`, `IND_DESC`, `BIZ_LICENSE`, `LICENCE_NAME`, `MCC`) VALUES ('9', '杂货、便利店（非烟）', '专门零售', '各类杂货店、便利店', '1', NULL, '5331');

--changeset dongfangchao:201705231043
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('3', 'BUDDY', '小伙伴', '3');

--changeset dongfangchao:201705241032
ALTER TABLE `notice_inf` ADD COLUMN `LINK_URL`  varchar(255) NULL COMMENT '链接地址' AFTER `NOTICE_CONTENT`;
CREATE TABLE `prod_industry` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROD_ID` varchar(20) DEFAULT NULL COMMENT '产品id',
  `IND_MCC` varchar(255) DEFAULT NULL COMMENT '商户类别码',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uindex` (`PROD_ID`,`IND_MCC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品-行业关系表';

INSERT INTO `dictionary_type` (`ID`,`CODE`, `TEXT`, `STATUS`) VALUES (4, 'PROD_INDUSTRY', '产品适用行业', '1');
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('4', '5699', '服装零售', '1');
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('4', '5812', '餐饮餐馆', '2');
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('4', '7911', 'KTV', '3');
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('4', '7994', '网吧', '4');
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('4', '7230', '理发', '5');
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('4', '7298', '美容SPA', '6');
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('4', '5251', '五金店', '7');
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('4', '5499', '食品零售', '8');
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('4', '5331', '杂货、便利店（非烟）', '9');

--changeset dongfangchao:6
ALTER TABLE `industry` ADD COLUMN `STORE_LICENCE`  tinyint(1) NULL DEFAULT 1 COMMENT '是否需要许可证' AFTER `BIZ_LICENSE`;
UPDATE `industry` SET STORE_LICENCE = 0 WHERE MCC IN ('5699', '7230', '5251', '5331');

INSERT INTO `industry` (`IND_NAME`, `IND_TYPE`, `IND_DESC`, `LICENCE_NAME`, `MCC`) VALUES ('烟草制品零售', '专门零售', '食品、饮料及烟草制品专门零售', '烟草许可证', '5227');
INSERT INTO `dictionary_item` (`TYPE_ID`, `CODE`, `TEXT`, `SORT`) VALUES ('4', '5227', '烟草制品零售', '10');

--changeset will:6
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('98', 'LOAN_FINANCE', '财务管理', '财务管理', '17', ',1,17,98,', '1', 'system', '2016-12-08 09:53:55.000000', 'system', '2016-12-08 10:23:17.000000', '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('99', 'LOAN_BALANCE', '清算对账', '清算对账', '17', ',1,17,98,99,', '1', 'system', '2016-12-08 09:53:55.000000', 'system', '2016-12-08 10:23:17.000000', '0');
insert into mgt_role_perm(role_id, permission_id) values(1, 98);
insert into mgt_role_perm(role_id, permission_id) values(1, 99);

--changeset will:7
update jinfu_mgt_pro.mgt_permission a set a.PERM_PARENT = 98 where a.PERMISSION_ID = 99;

--changeset dongfangchao:201706201130
ALTER TABLE `app_op_log`
ADD COLUMN `VERSION_NAME`  varchar(255) NULL AFTER `APP_ID`,
ADD COLUMN `VERSION_CODE`  int(11) NULL AFTER `VERSION_NAME`,
ADD COLUMN `APP_FORCE_UPDATE`  tinyint(1) NULL AFTER `VERSION_CODE`;

--changeset dongfangchao:201706201758
ALTER TABLE `app_version_control`
ADD COLUMN `UPDATE_TIP`  varchar(255) NULL COMMENT '更新提示语' AFTER `OPERATING_SYSTEM`;

--changeset bright:1
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('100', 'REPORT_STS_CENTER', '报表统计中心', '报表统计中心', '1', ',1,100', '12', 'system', '2017-06-20 16:32:12.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('101', 'REPORT_STS_LOAN_HISTORY', '贷款记录', '贷款记录', '100', ',1,100,101', '12', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('102', 'REPORT_STS_REPAYMENT_HISTORY', '还款记录', '还款记录', '100', ',1,100,102', '12', 'system', '2017-06-20 16:37:35.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('103', 'REPORT_STS_REPAYMENT_PLAN', '还款计划', '还款计划', '100', ',1,100,123', '12', 'system', '2017-06-20 16:38:22.000000', NULL, NULL, '0');

--changeset menglei:6
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('104', 'DT_FROZEN', '分销商禁用启用', '分销商禁用启用', '3', ',1,2,3,104,', '12', 'system', '2017-06-16 16:00:52');
INSERT INTO `mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`)
VALUES ('105', 'DT_USER_FROZEN', '分销人员禁用启用', '分销人员禁用启用', '3', ',1,2,3,105,', '13', 'system', '2017-06-16 16:00:52');

--changeset bright:2
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('107', 'RISK_EYE', '云眼系统', '云眼系统', '1', ',1,107', '15', 'system', '2017-06-20 16:32:12.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('108', 'RISK_EYE_REPORT', '报告中心', '报告中心', '107', ',1,107,108', '15', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('109', 'RISK_EYE_SCORE_ITEM', '评分项目设置', '评分项目设置', '107', ',1,107,109', '15', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('110', 'RISK_EYE_SCORE_CARD', '评分卡配置', '评分卡配置', '107', ',1,107,110', '15', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('111', 'RISK_EYE_RULE_ITEM', '规则管理', '规则管理', '107', ',1,107,111', '15', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('112', 'RISK_EYE_RULE_TREE', '决策树配置', '决策树配置', '107', ',1,107,112', '15', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('113', 'RISK_EYE_PRODUCT', '产品管理', '产品管理', '107', ',1,107,113', '15', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('114', 'RISK_EYE_BLACKLIST', '黑名单管理', '黑名单管理', '107', ',1,107,114', '15', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('115', 'RISK_EYE_HOME', '首页', '首页', '107', ',1,107,115', '15', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');


--changeset dongfangchao:201706201130
ALTER TABLE `app_op_log`
ADD COLUMN `VERSION_NAME`  varchar(255) NULL AFTER `APP_ID`,
ADD COLUMN `VERSION_CODE`  int(11) NULL AFTER `VERSION_NAME`,
ADD COLUMN `APP_FORCE_UPDATE`  tinyint(1) NULL AFTER `VERSION_CODE`;

--changeset dongfangchao:201706201758
ALTER TABLE `app_version_control`
ADD COLUMN `UPDATE_TIP`  varchar(255) NULL COMMENT '更新提示语' AFTER `OPERATING_SYSTEM`;

--changeset dongfangchao:201707050934
ALTER TABLE `app_version_control`
ADD COLUMN `NORMAL_RELEASE`  tinyint(1) NOT NULL DEFAULT 1 COMMENT '是不是正常发布（撤回发布：0，正常发布：1）' AFTER `UPDATE_TIP`;

CREATE TABLE `app_version_control_log` (
  `APP_LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `VERSION_NAME` varchar(255) NOT NULL,
  `VERSION_CODE` int(11) NOT NULL,
  `APP_FORCE_UPDATE` bit(1) NOT NULL DEFAULT b'0',
  `APP_PATH` varchar(255) DEFAULT NULL,
  `APP_SOURCE` varchar(50) DEFAULT NULL COMMENT '应用类型（如：小伙伴等）',
  `OPERATING_SYSTEM` varchar(20) DEFAULT NULL COMMENT '操作系统（IOS，ANDROID）',
  `UPDATE_TIP` varchar(255) DEFAULT NULL COMMENT '更新提示语',
  `CREATE_OPID` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` datetime(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` decimal(17,0) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`APP_LOG_ID`),
  UNIQUE KEY `uindex` (`APP_SOURCE`,`OPERATING_SYSTEM`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史发布版本（包括线上版本）';

INSERT INTO app_version_control_log(VERSION_NAME, VERSION_CODE, APP_FORCE_UPDATE, APP_PATH, APP_SOURCE, OPERATING_SYSTEM, UPDATE_TIP, CREATE_OPID, CREATE_TS)
SELECT
	VERSION_NAME, VERSION_CODE, APP_FORCE_UPDATE, APP_PATH, APP_SOURCE, OPERATING_SYSTEM, UPDATE_TIP, LAST_MNT_OPID, LAST_MNT_TS
FROM
	`app_version_control`;

--changeset dongfangchao:201707061412
ALTER TABLE `app_version_control_log`
DROP INDEX `uindex` ,
ADD UNIQUE INDEX `uindex` (`APP_SOURCE`, `OPERATING_SYSTEM`, `VERSION_NAME`) USING BTREE;

--changeset dongfangchao:201707071406
ALTER TABLE `app_version_control_log`
ADD COLUMN `RELEASE_TIME`  datetime NULL COMMENT '发布时间' AFTER `UPDATE_TIP`;
ALTER TABLE `app_version_control`
ADD COLUMN `RELEASE_TIME`  datetime NULL COMMENT '发布时间' AFTER `NORMAL_RELEASE`;

--changeset dongfangchao:201707071439
TRUNCATE TABLE app_version_control_log;
INSERT INTO app_version_control_log(VERSION_NAME, VERSION_CODE, APP_FORCE_UPDATE, APP_PATH, APP_SOURCE, OPERATING_SYSTEM, UPDATE_TIP, RELEASE_TIME, CREATE_OPID, CREATE_TS)
SELECT
	VERSION_NAME, VERSION_CODE, APP_FORCE_UPDATE, APP_PATH, APP_SOURCE, OPERATING_SYSTEM, UPDATE_TIP, CREATE_TS, CREATE_OPID, CREATE_TS
FROM
	`app_version_control`;

--changeset dongfangchao:201707121407
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('116', 'PINGAN_INS_ORDER', '店铺保下单', '店铺保下单', '6', ',1,6,116', '14', 'system', NOW(), NULL, NULL, '0');


--changeset jl:20170724
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('117', 'XM_DEPOSITORY', '厦门存管页', '厦门存管页', '27', ',1,27,117,', '31', 'system', '2017-07-24 09:33:54.000000', NULL, NULL, '0');


--changeset bright:user-credit-permission
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('118', 'RISK_EYE_QUOTA_CARD', '额度模型设置', '额度模型设置', '107', ',1,107,118', '15', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`) VALUES ('119', 'RISK_EYE_USER_CREDIT', '客户及授信管理', '客户及授信管理', '107', ',1,107,119', '15', 'system', '2017-06-20 16:33:54.000000', NULL, NULL, '0');


--changeset dongfangchao:201707281102
ALTER TABLE `dictionary_item`
ADD COLUMN `NAME`  varchar(255) NULL AFTER `TYPE_ID`;

INSERT INTO `dictionary_type` (`CODE`, `TEXT`, `STATUS`) VALUES ('YLZG_MY_LOAN', '云联掌柜我的贷款', '1');

--changeset menglei:20170908
INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('127', 'CLOUD_CODE_DEPOT', '云码库管理', '云码库管理', '42', ',1,42,127,', '47', 'system', '2017-09-08 00:00:00', NULL, NULL, '0');

INSERT INTO `jinfu_mgt_pro`.`mgt_permission` (`PERMISSION_ID`, `PERMISSION_CODE`, `PERMISSION_NAME`, `PERMISSION_DESC`, `PERM_PARENT`, `PERM_TREE_PATH`, `PERM_ORDER`, `CREATE_OPID`, `CREATE_TS`, `LAST_MNT_OPID`, `LAST_MNT_TS`, `VERSION_CT`)
VALUES ('128', 'PAY_CODE_MGT', '支付码管理', '支付码管理', '1', ',1,128,', '1', 'system', '2017-09-08 00:00:00', NULL, NULL, '0');

