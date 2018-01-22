INSERT INTO `mgt_permission`(
	`PERMISSION_ID` ,
	`PERMISSION_CODE` ,
  `PERMISSION_NAME` ,
	`PERMISSION_DESC` ,
	`PERM_PARENT` ,
	`PERM_TREE_PATH` ,
	`PERM_ORDER` ,
	`CREATE_TS` ,
	`CREATE_OPID` ,
	`LAST_MNT_TS` ,
	`LAST_MNT_OPID`
) VALUES
(50, 'CASHIER_MGT', '收银台管理', '收银台管理', 1, ',1,50,', 50, '2017-02-15 10:00:00', 'system', '2017-02-15 10:00:00', 'system'),
(51, 'CASHIER_PARTNER_MGT', '收单商户管理', '收单商户管理', 50, ',1,50,51,', 10, '2017-02-15 10:00:00', 'system', '2017-02-15 10:00:00', 'system'),
(52, 'CASHIER_USER_MGT', '会员管理', '会员管理', 50, ',1,50,52,', 20, '2017-02-15 10:00:00', 'system', '2017-02-15 10:00:00', 'system'),
(53, 'CASHIER_ORDER_MGT', '订单管理', '订单管理', 50, ',1,50,53,', 30, '2017-02-15 10:00:00', 'system', '2017-02-15 10:00:00', 'system'),
(54, 'CASHIER_CHANNEL_MGT', '通道管理', '通道管理', 50, ',1,50,54,', 40, '2017-02-15 10:00:00', 'system', '2017-02-15 10:00:00', 'system'),
(55, 'CASHIER_BILL_MGT', '账单管理', '账单管理', 50, ',1,50,55,', 50, '2017-02-15 10:00:00', 'system', '2017-02-15 10:00:00', 'system'),
(56, 'BU_BILL_MGT', 'BU账单', 'BU账单', 50, ',1,50,56,', 60, '2017-02-15 10:00:00', 'system', '2017-02-15 10:00:00', 'system');