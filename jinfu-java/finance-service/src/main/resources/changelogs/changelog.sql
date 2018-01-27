--liquibase formatted sql

--changeset dongfangchao:201706191100
ALTER TABLE `fin_tx_history` ADD COLUMN `EXT_TX_ACC_ID`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第三方理财账号' AFTER `USER_ID`;