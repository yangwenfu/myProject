--liquibase formatted sql

--changeset sephy:1 runOnChange:true
-- ALTER TABLE `push_device` ADD COLUMN `APP_TYPE` int NOT NULL DEFAULT 1 COMMENT '1小伙伴 2云联掌柜';