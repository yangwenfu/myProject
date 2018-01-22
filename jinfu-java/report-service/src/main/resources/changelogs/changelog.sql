--liquibase formatted sql

--changeset bright:3
DROP VIEW IF EXISTS fp_loan_dtl_import;

DROP VIEW IF EXISTS fp_repay_dtl_import;

DROP VIEW IF EXISTS fp_repay_schd_import;

DROP VIEW IF EXISTS fp_loan_dtl_all;

DROP VIEW IF EXISTS fp_repay_dtl_all;

DROP VIEW IF EXISTS fp_repay_schd_all;

CREATE VIEW fp_loan_dtl_import AS
SELECT
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_ID` AS `LOAN_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`USER_ID` AS `USER_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`APPL_ID` AS `APPL_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`PRODUCT_ID` AS `PRODUCT_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_NAME` AS `LOAN_NAME`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`ACCT_NO` AS `ACCT_NO`,
	`jinfu_user_pro`.`user_inf`.`USER_NAME` AS `USER_NAME`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_DT` AS `LOAN_DT`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_AMT` AS `LOAN_AMT`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`REPAYED_AMT` AS `REPAYED_AMT`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`DUE_DT` AS `DUE_DT`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`TRANSFER_TS` AS `TRANSFER_TS`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`TRANSFER_STAT` AS `TRANSFER_STAT`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`TERM_LEN` AS `TERM_LEN`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`TERM_TYPE` AS `TERM_TYPE`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`REPAY_MODE` AS `REPAY_MODE`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_RT` AS `LOAN_RT`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_RT_TYPE` AS `LOAN_RT_TYPE`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_STAT` AS `LOAN_STAT`,
	`jinfu_user_pro`.`store_inf`.`PROVINCE` AS `PROVINCE`,
	`jinfu_user_pro`.`store_inf`.`CITY` AS `CITY`,
	`jinfu_user_pro`.`store_inf`.`AREA` AS `AREA`,
	`jinfu_promo_pro`.`promo_inf`.`PROMO_ID` AS `PROMO_ID`,
	`jinfu_promo_pro`.`promo_inf`.`NAME` AS `PROMO_NAME`
FROM
	(
		(
			(
				(
					`jinfu_loan_pro`.`fp_loan_dtl_import`
					LEFT JOIN `jinfu_user_pro`.`user_inf` ON (
						(
							`jinfu_loan_pro`.`fp_loan_dtl_import`.`USER_ID` = `jinfu_user_pro`.`user_inf`.`USER_ID`
						)
					)
				)
				LEFT JOIN `jinfu_loan_pro`.`fp_loan_promo` ON (
					(
						`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_ID` = `jinfu_loan_pro`.`fp_loan_promo`.`LOAN_ID`
					)
				)
			)
			LEFT JOIN `jinfu_promo_pro`.`promo_inf` ON (
				(
					`jinfu_loan_pro`.`fp_loan_promo`.`PROMO_ID` = `jinfu_promo_pro`.`promo_inf`.`PROMO_ID`
				)
			)
		)
		LEFT JOIN `jinfu_user_pro`.`store_inf` ON (
			(
				`jinfu_user_pro`.`store_inf`.`STORE_ID` = (
					SELECT
						min(
							`jinfu_user_pro`.`store_inf`.`STORE_ID`
						)
					FROM
						`jinfu_user_pro`.`store_inf`
					WHERE
						(
							`jinfu_user_pro`.`store_inf`.`USER_ID` = `jinfu_user_pro`.`user_inf`.`USER_ID`
						)
				)
			)
		)
	)
WHERE
	(
		`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_ID` NOT IN (
			'L302011650002246078',
			'L302011038271687036',
			'L302010849472296044',
			'L302010843653670041',
			'L301142448361533096',
			'L301132558002248074',
			'L301130876884454018',
			'L301130797031976097',
			'L301130349049995045',
			'D16100900000002',
			'D16092800000004',
			'D16092300000012'
		)
	);

CREATE VIEW fp_repay_dtl_import AS
SELECT
	`jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_ID` AS `REPAY_ID`,
	`jinfu_loan_pro`.`fp_repay_dtl_import`.`LOAN_ID` AS `LOAN_ID`,
	`jinfu_loan_pro`.`fp_repay_dtl_import`.`ACCT_NO` AS `ACCT_NO`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_NAME` AS `LOAN_NAME`,
	`jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_DT` AS `REPAY_DT`,
	`jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_PRIN_AMT` AS `REPAY_PRIN_AMT`,
	`jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_FEE` AS `REPAY_FEE`,
	`jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_INTR` AS `REPAY_INTR`,
	`jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_FINE` AS `REPAY_FINE`,
	`jinfu_loan_pro`.`fp_repay_dtl_import`.`TRANS_MODE` AS `TRANS_MODE`,
	`jinfu_user_pro`.`store_inf`.`PROVINCE` AS `PROVINCE`,
	`jinfu_user_pro`.`store_inf`.`CITY` AS `CITY`,
	`jinfu_user_pro`.`store_inf`.`AREA` AS `AREA`,
	concat(
		'券类型:',
		`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_TYPE`,
		',券名称:',
		`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_NAME`,
		',券码:',
		`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_CODE`
	) AS `COUPON_DESC`,
	(
		CASE
		WHEN (
			`jinfu_loan_pro`.`fp_loan_coupon`.`PRICE` > `jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_INTR`
		) THEN
			`jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_INTR`
		ELSE
			`jinfu_loan_pro`.`fp_loan_coupon`.`PRICE`
		END
	) AS `COUPON_PRICE`,
	`c`.`USER_NAME` AS `USER_NAME`,
	`ord`.`RET_CHANNEL_NAME` AS `RET_CHANNEL_NAME`
FROM
	(
		(
			(
				(
					(
						`jinfu_loan_pro`.`fp_repay_dtl_import`
						LEFT JOIN `jinfu_loan_pro`.`fp_loan_dtl_import` ON (
							(
								`jinfu_loan_pro`.`fp_repay_dtl_import`.`LOAN_ID` = `jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_ID`
							)
						)
					)
					LEFT JOIN `jinfu_user_pro`.`store_inf` ON (
						(
							`jinfu_user_pro`.`store_inf`.`STORE_ID` = (
								SELECT
									min(
										`jinfu_user_pro`.`store_inf`.`STORE_ID`
									)
								FROM
									`jinfu_user_pro`.`store_inf`
								WHERE
									(
										`jinfu_user_pro`.`store_inf`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl_import`.`USER_ID`
									)
							)
						)
					)
				)
				LEFT JOIN `jinfu_loan_pro`.`fp_loan_coupon` ON (
					(
						`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_ID` = (
							SELECT
								min(
									`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_ID`
								)
							FROM
								`jinfu_loan_pro`.`fp_loan_coupon`
							WHERE
								(
									`jinfu_loan_pro`.`fp_loan_coupon`.`REPAY_ID` = `jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_ID`
								)
						)
					)
				)
			)
			LEFT JOIN `jinfu_user_pro`.`user_inf` `c` ON (
				(
					`c`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl_import`.`USER_ID`
				)
			)
		)
		LEFT JOIN `jinfu_loan_pro`.`ac_pay_recv_ord` `ord` ON (
			(
				`ord`.`ORD_ID` = (
					SELECT
						min(
							`jinfu_loan_pro`.`ac_pay_recv_ord`.`ORD_ID`
						)
					FROM
						`jinfu_loan_pro`.`ac_pay_recv_ord`
					WHERE
						(
							`jinfu_loan_pro`.`ac_pay_recv_ord`.`BIZ_ID` = `jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_ID`
						)
				)
			)
		)
	)
WHERE
	(
		(
			(
				`jinfu_loan_pro`.`fp_repay_dtl_import`.`STATUS` = '1'
			)
			OR isnull(
				`jinfu_loan_pro`.`fp_repay_dtl_import`.`STATUS`
			)
		)
		AND (
			`jinfu_loan_pro`.`fp_repay_dtl_import`.`LOAN_ID` NOT IN (
				'L302011650002246078',
				'L302011038271687036',
				'L302010849472296044',
				'L302010843653670041',
				'L301142448361533096',
				'L301132558002248074',
				'L301130876884454018',
				'L301130797031976097',
				'L301130349049995045',
				'D16100900000002',
				'D16092800000004',
				'D16092300000012'
			)
		)
	)
ORDER BY
	`jinfu_loan_pro`.`fp_repay_dtl_import`.`REPAY_ID`;

CREATE VIEW fp_repay_schd_import AS
SELECT
	`jinfu_loan_pro`.`fp_repay_schd_import`.`SCHD_ID` AS `SCHD_ID`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`LOAN_ID` AS `LOAN_ID`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`ACCT_NO` AS `ACCT_NO`,
	`jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_NAME` AS `LOAN_NAME`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`DUE_DT` AS `DUE_DT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`PAY_DT` AS `PAY_DT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`SEQ_NO` AS `SEQ_NO`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`PRIN_PY_AMT` AS `PRIN_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`PRIN_PD_AMT` AS `PRIN_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`INTR_PY_AMT` AS `INTR_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`INTR_PD_AMT` AS `INTR_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`PRIN_FINE_PY_AMT` AS `PRIN_FINE_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`PRIN_FINE_PD_AMT` AS `PRIN_FINE_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`INTR_FINE_PY_AMT` AS `INTR_FINE_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`INTR_FINE_PD_AMT` AS `INTR_FINE_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`FEE_PY_AMT` AS `FEE_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`FEE_PD_AMT` AS `FEE_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd_import`.`STATUS` AS `STATUS`,
	`jinfu_user_pro`.`store_inf`.`PROVINCE` AS `PROVINCE`,
	`jinfu_user_pro`.`store_inf`.`CITY` AS `CITY`,
	`jinfu_user_pro`.`store_inf`.`AREA` AS `AREA`,
	`c`.`USER_NAME` AS `USER_NAME`
FROM
	(
		(
			(
				`jinfu_loan_pro`.`fp_repay_schd_import`
				LEFT JOIN `jinfu_loan_pro`.`fp_loan_dtl_import` ON (
					(
						`jinfu_loan_pro`.`fp_repay_schd_import`.`LOAN_ID` = `jinfu_loan_pro`.`fp_loan_dtl_import`.`LOAN_ID`
					)
				)
			)
			LEFT JOIN `jinfu_user_pro`.`store_inf` ON (
				(
					`jinfu_user_pro`.`store_inf`.`STORE_ID` = (
						SELECT
							min(
								`jinfu_user_pro`.`store_inf`.`STORE_ID`
							)
						FROM
							`jinfu_user_pro`.`store_inf`
						WHERE
							(
								`jinfu_user_pro`.`store_inf`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl_import`.`USER_ID`
							)
					)
				)
			)
		)
		LEFT JOIN `jinfu_user_pro`.`user_inf` `c` ON (
			(
				`c`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl_import`.`USER_ID`
			)
		)
	)
WHERE
	(
		`jinfu_loan_pro`.`fp_repay_schd_import`.`LOAN_ID` NOT IN (
			'L302011650002246078',
			'L302011038271687036',
			'L302010849472296044',
			'L302010843653670041',
			'L301142448361533096',
			'L301132558002248074',
			'L301130876884454018',
			'L301130797031976097',
			'L301130349049995045',
			'D16100900000002',
			'D16092800000004',
			'D16092300000012'
		)
	);

CREATE VIEW fp_loan_dtl_all AS
SELECT * FROM fp_loan_dtl
union ALL
SELECT * FROM fp_loan_dtl_import;

CREATE VIEW fp_repay_dtl_all AS
SELECT * FROM fp_repay_dtl
union ALL
SELECT * FROM fp_repay_dtl_import;

CREATE VIEW fp_repay_schd_all AS
SELECT * FROM fp_repay_schd
union ALL
SELECT * FROM fp_repay_schd_import;

--changeset menglei:1
DROP VIEW `dealer_order`;
CREATE VIEW `dealer_order` AS
SELECT `ord`.`USER_ID` AS `USER_ID`,
         `ord`.`APPL_ID` AS `ORDER_NO`,
         `ord`.`PRODUCT_ID` AS `PRODUCT_ID`,
         `jinfu_loan_pro`.`prod_inf`.`PROD_NAME` AS `product_name`,
        '--' AS `store_name`,
        `usr_inf`.`USER_NAME` AS `user_name`,
        `ord`.`APPL_AMT` AS `order_amt`,
        if(isnull(`uo`.`ID`),
        'SELFSERVICE','MANAGERSERVICE') AS `deal_type`,
        `ord`.`CREATE_TS` AS `order_date`, `du`.`NAME` AS `operator_name`,
        `jinfu_dealer_pro`.`dealer`.`DEALER_NAME` AS `DEALER_NAME`,
        `store`.`PROVINCE_ID` AS `PROVINCE_ID`,
        `store`.`PROVINCE` AS `PROVINCE`,
        `store`.`CITY_ID` AS `CITY_ID`,
        `store`.`CITY` AS `CITY`,
        `store`.`AREA_ID` AS `AREA_ID`,
        `store`.`AREA` AS `AREA`,
        `store`.`STREET_ID` AS `STREET_ID`,
        `store`.`STREET` AS `STREET`,
        `jinfu_dealer_pro`.`dealer`.`PROVINCE_ID` AS `dealer_province_id`,
        `jinfu_dealer_pro`.`dealer`.`CITY_ID` AS `dealer_city_id`,
        `jinfu_dealer_pro`.`dealer`.`AREA_ID` AS `dealer_area_id`,
        `jinfu_dealer_pro`.`dealer`.`STREET_ID` AS `dealer_street_id`,
        `jinfu_dealer_pro`.`dealer`.`BELONG_ID` AS `BELONG_ID`,
        if(`jinfu_dealer_pro`.`dealer`.`BELONG_ID`='0',"总部",`jinfu_mgt_pro`.`mgt_user`.`NAME`) AS `BELONG_NAME`
FROM (((((((`jinfu_loan_pro`.`fp_loan_appl` `ord`
LEFT JOIN `jinfu_loan_pro`.`prod_inf` on((`ord`.`PRODUCT_ID` = `jinfu_loan_pro`.`prod_inf`.`PROD_ID`)))
LEFT JOIN `jinfu_user_pro`.`user_inf` `usr_inf` on((`ord`.`USER_ID` = `usr_inf`.`USER_ID`)))
LEFT JOIN `jinfu_dealer_pro`.`dealer_user_order` `uo` on((`ord`.`APPL_ID` = `uo`.`ORDER_NO`)))
LEFT JOIN `jinfu_dealer_pro`.`dealer` on((`uo`.`DEALER_ID` = `jinfu_dealer_pro`.`dealer`.`DEALER_ID`)))
LEFT JOIN `jinfu_dealer_pro`.`dealer_user` `du` on((`uo`.`USER_ID` = `du`.`USER_ID`)))
LEFT JOIN `jinfu_user_pro`.`store_inf` `store` on((`usr_inf`.`USER_ID` = `store`.`USER_ID`)))
LEFT JOIN `jinfu_mgt_pro`.`mgt_user` on((`jinfu_dealer_pro`.`dealer`.`BELONG_ID` = `jinfu_mgt_pro`.`mgt_user`.`USER_ID`)))
UNION
all
    (SELECT `usr_inf`.`USER_ID` AS `USER_ID`,
        `ord`.`PER_INSURANCE_ORDER_NO` AS `ORDER_NO`,
        `ord`.`PRODUCT_ID` AS `PRODUCT_ID`,
        `ord`.`PRODUCT_NAME` AS `product_name`,
        `ord`.`STORE_NAME` AS `store_name`,
        `usr_inf`.`USER_NAME` AS `user_name`,
        (`ord`.`INSURANCE_FEE` / 1000) AS `order_amt`,
        if(isnull(`jinfu_dealer_pro`.`dealer`.`DEALER_NAME`),
        'SELFSERVICE','MANAGERSERVICE') AS `deal_type`,
        `ord`.`ORDER_DATE` AS `order_date`,
        `du`.`NAME` AS `operator_name`,
        `jinfu_dealer_pro`.`dealer`.`DEALER_NAME` AS `DEALER_NAME`,
        `province_inf`.`ID` AS `PROVINCE_ID`,
        `province_inf`.`NAME` AS `PROVINCE`,
        `city_inf`.`ID` AS `CITY_ID`,
        `city_inf`.`NAME` AS `CITY`,
        `area_inf`.`ID` AS `AREA_ID`,
        `area_inf`.`NAME` AS `AREA`,
        `street_inf`.`ID` AS `STREET_ID`,
        `street_inf`.`NAME` AS `STREET`,
        `jinfu_dealer_pro`.`dealer`.`PROVINCE_ID` AS `dealer_province_id`,
        `jinfu_dealer_pro`.`dealer`.`CITY_ID` AS `dealer_city_id`,
        `jinfu_dealer_pro`.`dealer`.`AREA_ID` AS `dealer_area_id`,
        `jinfu_dealer_pro`.`dealer`.`STREET_ID` AS `dealer_street_id`,
        `jinfu_dealer_pro`.`dealer`.`BELONG_ID` AS `BELONG_ID`,
        if(`jinfu_dealer_pro`.`dealer`.`BELONG_ID`='0',"总部",`jinfu_mgt_pro`.`mgt_user`.`NAME`) AS `BELONG_NAME`
    FROM ((((((((((`jinfu_ins_pro`.`per_insurance_info` `ord`
    LEFT JOIN `jinfu_user_pro`.`store_inf` `store` on((`ord`.`STORE_ID` = `store`.`STORE_ID`)))
    LEFT JOIN `jinfu_user_pro`.`user_inf` `usr_inf` on((`store`.`USER_ID` = `usr_inf`.`USER_ID`)))
    LEFT JOIN `jinfu_dealer_pro`.`dealer_user_order` `uo` on((`ord`.`PER_INSURANCE_ORDER_NO` = `uo`.`ORDER_NO`)))
    LEFT JOIN `jinfu_dealer_pro`.`dealer` on((`uo`.`DEALER_ID` = `jinfu_dealer_pro`.`dealer`.`DEALER_ID`)))
    LEFT JOIN `jinfu_dealer_pro`.`dealer_user` `du` on((`uo`.`USER_ID` = `du`.`USER_ID`)))
    LEFT JOIN `jinfu_mgt_pro`.`mgt_user` on((`jinfu_dealer_pro`.`dealer`.`BELONG_ID` = `jinfu_mgt_pro`.`mgt_user`.`USER_ID`)))
    LEFT JOIN `jinfu_mgt_pro`.`sys_area_inf` `province_inf` on((`province_inf`.`ID` = substring_index(substring_index(`ord`.`STORE_AREA_TREE_PATH`,',',2),',',-(1)))))
    LEFT JOIN `jinfu_mgt_pro`.`sys_area_inf` `city_inf` on((`city_inf`.`ID` = substring_index(substring_index(`ord`.`STORE_AREA_TREE_PATH`,',',3),',',-(1)))))
    LEFT JOIN `jinfu_mgt_pro`.`sys_area_inf` `area_inf` on((`area_inf`.`ID` = substring_index(substring_index(`ord`.`STORE_AREA_TREE_PATH`,',',4),',',-(1)))))
    LEFT JOIN `jinfu_mgt_pro`.`sys_area_inf` `street_inf` on((`street_inf`.`ID` = substring_index(substring_index(`ord`.`STORE_AREA_TREE_PATH`,',',5),',',-(1))))));

--changeset will:1
ALTER
VIEW jinfu_report_pro.`fp_loan_dtl` AS
SELECT
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_ID` AS `LOAN_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID` AS `USER_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`APPL_ID` AS `APPL_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`PRODUCT_ID` AS `PRODUCT_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_NAME` AS `LOAN_NAME`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`ACCT_NO` AS `ACCT_NO`,
	`jinfu_user_pro`.`user_inf`.`USER_NAME` AS `USER_NAME`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_DT` AS `LOAN_DT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_AMT` AS `LOAN_AMT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`REPAYED_AMT` AS `REPAYED_AMT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`DUE_DT` AS `DUE_DT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`TRANSFER_TS` AS `TRANSFER_TS`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`TRANSFER_STAT` AS `TRANSFER_STAT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`TERM_LEN` AS `TERM_LEN`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`TERM_TYPE` AS `TERM_TYPE`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`REPAY_MODE` AS `REPAY_MODE`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_RT` AS `LOAN_RT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_RT_TYPE` AS `LOAN_RT_TYPE`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_STAT` AS `LOAN_STAT`,
	`jinfu_user_pro`.`store_inf`.`PROVINCE` AS `PROVINCE`,
	`jinfu_user_pro`.`store_inf`.`CITY` AS `CITY`,
	`jinfu_user_pro`.`store_inf`.`AREA` AS `AREA`,
	`jinfu_promo_pro`.`promo_inf`.`PROMO_ID` AS `PROMO_ID`,
	`jinfu_promo_pro`.`promo_inf`.`NAME` AS `PROMO_NAME`
FROM
	(
		(
			(
				(
					`jinfu_loan_pro`.`fp_loan_dtl`
					LEFT JOIN `jinfu_user_pro`.`user_inf` ON (
						(
							`jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID` = `jinfu_user_pro`.`user_inf`.`USER_ID`
						)
					)
				)
				LEFT JOIN `jinfu_loan_pro`.`fp_loan_promo` ON (
					(
						`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_ID` = `jinfu_loan_pro`.`fp_loan_promo`.`LOAN_ID`
					)
				)
			)
			LEFT JOIN `jinfu_promo_pro`.`promo_inf` ON (
				(
					`jinfu_loan_pro`.`fp_loan_promo`.`PROMO_ID` = `jinfu_promo_pro`.`promo_inf`.`PROMO_ID`
				)
			)
		)
		LEFT JOIN `jinfu_user_pro`.`store_inf` ON (
			(
				`jinfu_user_pro`.`store_inf`.`STORE_ID` = (
					SELECT
						min(
							`jinfu_user_pro`.`store_inf`.`STORE_ID`
						)
					FROM
						`jinfu_user_pro`.`store_inf`
					WHERE
						(
							`jinfu_user_pro`.`store_inf`.`USER_ID` = `jinfu_user_pro`.`user_inf`.`USER_ID`
						)
				)
			)
		)
	)
WHERE
	(
		`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_ID` NOT IN (
			'L302011650002246078',
			'L302011038271687036',
			'L302010849472296044',
			'L302010843653670041',
			'L301142448361533096',
			'L301132558002248074',
			'L301130876884454018',
			'L301130797031976097',
			'L301130349049995045',
			'D16100900000002',
			'D16092800000004',
			'D16092300000012'
		)
	)
AND (`jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID = 1 or `jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID is null) ;

ALTER
VIEW jinfu_report_pro.`fp_repay_dtl` AS
SELECT
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_ID` AS `REPAY_ID`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`LOAN_ID` AS `LOAN_ID`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`ACCT_NO` AS `ACCT_NO`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_NAME` AS `LOAN_NAME`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_DT` AS `REPAY_DT`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_PRIN_AMT` AS `REPAY_PRIN_AMT`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_FEE` AS `REPAY_FEE`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_INTR` AS `REPAY_INTR`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_FINE` AS `REPAY_FINE`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`TRANS_MODE` AS `TRANS_MODE`,
	`jinfu_user_pro`.`store_inf`.`PROVINCE` AS `PROVINCE`,
	`jinfu_user_pro`.`store_inf`.`CITY` AS `CITY`,
	`jinfu_user_pro`.`store_inf`.`AREA` AS `AREA`,
	concat(
		'券类型:',
		`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_TYPE`,
		',券名称:',
		`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_NAME`,
		',券码:',
		`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_CODE`
	) AS `COUPON_DESC`,
	(
		CASE
		WHEN (
			`jinfu_loan_pro`.`fp_loan_coupon`.`PRICE` > `jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_INTR`
		) THEN
			`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_INTR`
		ELSE
			`jinfu_loan_pro`.`fp_loan_coupon`.`PRICE`
		END
	) AS `COUPON_PRICE`,
	`c`.`USER_NAME` AS `USER_NAME`,
	`ord`.`RET_CHANNEL_NAME` AS `RET_CHANNEL_NAME`
FROM
	(
		(
			(
				(
					(
						`jinfu_loan_pro`.`fp_repay_dtl`
						LEFT JOIN `jinfu_loan_pro`.`fp_loan_dtl` ON (
							(
								`jinfu_loan_pro`.`fp_repay_dtl`.`LOAN_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_ID`
							)
						)
					)
					LEFT JOIN `jinfu_user_pro`.`store_inf` ON (
						(
							`jinfu_user_pro`.`store_inf`.`STORE_ID` = (
								SELECT
									min(
										`jinfu_user_pro`.`store_inf`.`STORE_ID`
									)
								FROM
									`jinfu_user_pro`.`store_inf`
								WHERE
									(
										`jinfu_user_pro`.`store_inf`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID`
									)
							)
						)
					)
				)
				LEFT JOIN `jinfu_loan_pro`.`fp_loan_coupon` ON (
					(
						`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_ID` = (
							SELECT
								min(
									`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_ID`
								)
							FROM
								`jinfu_loan_pro`.`fp_loan_coupon`
							WHERE
								(
									`jinfu_loan_pro`.`fp_loan_coupon`.`REPAY_ID` = `jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_ID`
								)
						)
					)
				)
			)
			LEFT JOIN `jinfu_user_pro`.`user_inf` `c` ON (
				(
					`c`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID`
				)
			)
		)
		LEFT JOIN `jinfu_loan_pro`.`ac_pay_recv_ord` `ord` ON (
			(
				`ord`.`ORD_ID` = (
					SELECT
						min(
							`jinfu_loan_pro`.`ac_pay_recv_ord`.`ORD_ID`
						)
					FROM
						`jinfu_loan_pro`.`ac_pay_recv_ord`
					WHERE
						(
							`jinfu_loan_pro`.`ac_pay_recv_ord`.`BIZ_ID` = `jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_ID`
						)
				)
			)
		)
	)
WHERE
		(
			(
				`jinfu_loan_pro`.`fp_repay_dtl`.`STATUS` = '1'
			)
			OR isnull(
				`jinfu_loan_pro`.`fp_repay_dtl`.`STATUS`
			)
		)
		AND (
			`jinfu_loan_pro`.`fp_repay_dtl`.`LOAN_ID` NOT IN (
				'L302011650002246078',
				'L302011038271687036',
				'L302010849472296044',
				'L302010843653670041',
				'L301142448361533096',
				'L301132558002248074',
				'L301130876884454018',
				'L301130797031976097',
				'L301130349049995045',
				'D16100900000002',
				'D16092800000004',
				'D16092300000012'
			)
		)
		AND (`jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID = 1 or `jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID is null)
ORDER BY
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_ID` ;

ALTER
VIEW jinfu_report_pro.`fp_repay_schd` AS
SELECT
	`jinfu_loan_pro`.`fp_repay_schd`.`SCHD_ID` AS `SCHD_ID`,
	`jinfu_loan_pro`.`fp_repay_schd`.`LOAN_ID` AS `LOAN_ID`,
	`jinfu_loan_pro`.`fp_repay_schd`.`ACCT_NO` AS `ACCT_NO`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_NAME` AS `LOAN_NAME`,
	`jinfu_loan_pro`.`fp_repay_schd`.`DUE_DT` AS `DUE_DT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`PAY_DT` AS `PAY_DT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`SEQ_NO` AS `SEQ_NO`,
	`jinfu_loan_pro`.`fp_repay_schd`.`PRIN_PY_AMT` AS `PRIN_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`PRIN_PD_AMT` AS `PRIN_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`INTR_PY_AMT` AS `INTR_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`INTR_PD_AMT` AS `INTR_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`PRIN_FINE_PY_AMT` AS `PRIN_FINE_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`PRIN_FINE_PD_AMT` AS `PRIN_FINE_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`INTR_FINE_PY_AMT` AS `INTR_FINE_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`INTR_FINE_PD_AMT` AS `INTR_FINE_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`FEE_PY_AMT` AS `FEE_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`FEE_PD_AMT` AS `FEE_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`STATUS` AS `STATUS`,
	`jinfu_user_pro`.`store_inf`.`PROVINCE` AS `PROVINCE`,
	`jinfu_user_pro`.`store_inf`.`CITY` AS `CITY`,
	`jinfu_user_pro`.`store_inf`.`AREA` AS `AREA`,
	`c`.`USER_NAME` AS `USER_NAME`
FROM
	(
		(
			(
				`jinfu_loan_pro`.`fp_repay_schd`
				LEFT JOIN `jinfu_loan_pro`.`fp_loan_dtl` ON (
					(
						`jinfu_loan_pro`.`fp_repay_schd`.`LOAN_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_ID`
					)
				)
			)
			LEFT JOIN `jinfu_user_pro`.`store_inf` ON (
				(
					`jinfu_user_pro`.`store_inf`.`STORE_ID` = (
						SELECT
							min(
								`jinfu_user_pro`.`store_inf`.`STORE_ID`
							)
						FROM
							`jinfu_user_pro`.`store_inf`
						WHERE
							(
								`jinfu_user_pro`.`store_inf`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID`
							)
					)
				)
			)
		)
		LEFT JOIN `jinfu_user_pro`.`user_inf` `c` ON (
			(
				`c`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID`
			)
		)
	)
WHERE
	(
		`jinfu_loan_pro`.`fp_repay_schd`.`LOAN_ID` NOT IN (
			'L302011650002246078',
			'L302011038271687036',
			'L302010849472296044',
			'L302010843653670041',
			'L301142448361533096',
			'L301132558002248074',
			'L301130876884454018',
			'L301130797031976097',
			'L301130349049995045',
			'D16100900000002',
			'D16092800000004',
			'D16092300000012'
		)
	) AND (`jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID = 1 or `jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID is null) ;

--changeset bright:seperate-user-report
DROP VIEW IF EXISTS `user`;

CREATE VIEW `user` AS SELECT
	concat(
		ifnull(`usr_inf`.`USER_ID`, ''),
		'-',
		CONVERT (
			ifnull(`store`.`STORE_ID`, '') USING utf8
		)
	) AS `id`,
	`usr_inf`.`USER_ID` AS `user_id`,
	`usr_inf`.`MOBILE` AS `mobile`,
	`usr_inf`.`USER_NAME` AS `user_name`,
	`usr_inf`.`ID_CARD_NO` AS `ID_CARD_NO`,
	`usr_inf`.`IDENTITY_AUTH` AS `IDENTITY_AUTH`,
	`usr_inf`.`STORE_AUTH` AS `STORE_AUTH`,
	`usr_inf`.`CREATE_TS` AS `CREATE_TS`,
	`usr_inf`.`IDENTITY_AUTH_DATE` AS `IDENTITY_AUTH_DATE`,
	`store`.`STORE_NAME` AS `store_name`,
	`store`.`TOBACCO_CERTIFICATE_NO` AS `TOBACCO_CERTIFICATE_NO`,
	`store`.`BIZ_LICENCE` AS `BIZ_LICENCE`,
	`store`.`PROVINCE_ID` AS `PROVINCE_ID`,
	`store`.`PROVINCE` AS `PROVINCE`,
	`store`.`CITY_ID` AS `CITY_ID`,
	`store`.`CITY` AS `CITY`,
	`store`.`AREA_ID` AS `AREA_ID`,
	`store`.`AREA` AS `AREA`,
	`store`.`STREET_ID` AS `STREET_ID`,
	`store`.`STREET` AS `STREET`,
	`store`.`FULL_ADDRESS` AS `FULL_ADDRESS`,
	`usr_inf`.`SOURCE` AS `user_Source`,
	`usr`.`NAME` AS `dealer_user_name`,
	`usr`.`MOBILE` AS `dealer_user_mobile`,
	`jinfu_dealer_pro`.`dealer`.`DEALER_NAME` AS `dealer_name`,
	'Active' AS `user_status`
FROM
	(
		(
			(
				(
					`jinfu_user_pro`.`user_inf` `usr_inf`
					LEFT JOIN `jinfu_user_pro`.`store_inf` `store` ON (
						(
							`usr_inf`.`USER_ID` = `store`.`USER_ID`
						)
					)
				)
				LEFT JOIN `jinfu_dealer_pro`.`dealer_user_store` `us` ON (
					(
						`store`.`STORE_ID` = `us`.`STORE_ID`
					)
				)
			)
			LEFT JOIN `jinfu_dealer_pro`.`dealer_user` `usr` ON (
				(
					`us`.`USER_ID` = `usr`.`USER_ID`
				)
			)
		)
		LEFT JOIN `jinfu_dealer_pro`.`dealer` ON (
			(
				`us`.`DEALER_ID` = `jinfu_dealer_pro`.`dealer`.`DEALER_ID`
			)
		)
	);


DROP VIEW IF EXISTS `old_user`;

CREATE VIEW `old_user` AS SELECT
	`jinfu_user_pro`.`old_user`.`USER_ID` AS `id`,
	`jinfu_user_pro`.`old_user`.`USER_ID` AS `user_id`,
	`jinfu_user_pro`.`old_user`.`MOBILE` AS `mobile`,
	`jinfu_user_pro`.`old_user`.`USER_NAME` AS `user_name`,
	`jinfu_user_pro`.`old_user`.`ID_CARD_NO` AS `ID_CARD_NO`,
	`jinfu_user_pro`.`old_user`.`IDENTITY_AUTH` AS `IDENTITY_AUTH`,
	`jinfu_user_pro`.`old_user`.`STORE_AUTH` AS `STORE_AUTH`,
	NULL AS `create_ts`,
	`jinfu_user_pro`.`old_user`.`IDENTITY_AUTH_DATE` AS `IDENTITY_AUTH_DATE`,
	NULL AS `store_name`,
	`jinfu_user_pro`.`old_user`.`TOBACCO_CERTIFICATE_NO` AS `TOBACCO_CERTIFICATE_NO`,
	NULL AS `biz_licence`,
	NULL AS `province_id`,
	NULL AS `province`,
	NULL AS `city_id`,
	NULL AS `city`,
	NULL AS `area_id`,
	NULL AS `area`,
	NULL AS `street_id`,
	NULL AS `street`,
	NULL AS `full_address`,
	NULL AS `user_source`,
	NULL AS `dealer_user_name`,
	NULL AS `dealer_user_mobile`,
	NULL AS `dealer_name`,
	'Inactive' AS `user_status`
FROM
	`jinfu_user_pro`.`old_user`;


--changeset menglei:2
DROP VIEW `dealer_user`;
CREATE VIEW `dealer_user` AS
select
   `usr`.`USER_ID` AS `USER_ID`,
   `usr`.`NAME` AS `user_name`,
   `usr`.`MOBILE` AS `mobile`,
   `usr`.`IS_ADMIN` AS `is_admin`,
   `jinfu_dealer_pro`.`dealer`.`DEALER_NAME` AS `dealer_name`,
   `jinfu_dealer_pro`.`dealer`.`LEVEL_ID` AS `level_id`,
   `jinfu_dealer_pro`.`dealer`.`PROVINCE_ID` AS `province_id`,
   `jinfu_dealer_pro`.`dealer`.`CITY_ID` AS `CITY_ID`,
   `jinfu_dealer_pro`.`dealer`.`AREA_ID` AS `AREA_ID`,
   `jinfu_dealer_pro`.`dealer`.`STREET_ID` AS `STREET_ID`,
   `dp`.`PROD_ID` AS `product_id`,
   `jinfu_dealer_pro`.`dealer`.`CREATE_TS` AS `dealer_create_ts`,
   `dp`.`PROVINCE_ID` AS `prod_province_id`,
   `dp`.`CITY_ID` AS `prod_city_id`,
   `dp`.`AREA_ID` AS `prod_area_id`,
   `dp`.`STREET_ID` AS `prod_street_id`,
   `usr`.`CREATE_TS` AS `create_ts`,
   `jinfu_dealer_pro`.`dealer`.`TYPE` AS `type`
FROM ((`jinfu_dealer_pro`.`dealer_user` `usr` left join `jinfu_dealer_pro`.`dealer` on((`usr`.`DEALER_ID` = `jinfu_dealer_pro`.`dealer`.`DEALER_ID`))) left join `jinfu_dealer_pro`.`dealer_prod` `dp` on((`jinfu_dealer_pro`.`dealer`.`DEALER_ID` = `dp`.`DEALER_ID`))) where (`usr`.`STATUS` = 'normal') order by `usr`.`USER_ID` desc;


--changeset bright:internal-loan-report
DROP VIEW IF EXISTS fp_loan_dtl_real;

CREATE
VIEW `fp_loan_dtl_real` AS
SELECT
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_ID` AS `LOAN_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID` AS `USER_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`APPL_ID` AS `APPL_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`PRODUCT_ID` AS `PRODUCT_ID`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_NAME` AS `LOAN_NAME`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`ACCT_NO` AS `ACCT_NO`,
	`jinfu_user_pro`.`user_inf`.`USER_NAME` AS `USER_NAME`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_DT` AS `LOAN_DT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_AMT` AS `LOAN_AMT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`REPAYED_AMT` AS `REPAYED_AMT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`DUE_DT` AS `DUE_DT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`TRANSFER_TS` AS `TRANSFER_TS`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`TRANSFER_STAT` AS `TRANSFER_STAT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`TERM_LEN` AS `TERM_LEN`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`TERM_TYPE` AS `TERM_TYPE`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`REPAY_MODE` AS `REPAY_MODE`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_RT` AS `LOAN_RT`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_RT_TYPE` AS `LOAN_RT_TYPE`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_STAT` AS `LOAN_STAT`,
	`jinfu_user_pro`.`store_inf`.`PROVINCE` AS `PROVINCE`,
	`jinfu_user_pro`.`store_inf`.`CITY` AS `CITY`,
	`jinfu_user_pro`.`store_inf`.`AREA` AS `AREA`,
	`jinfu_promo_pro`.`promo_inf`.`PROMO_ID` AS `PROMO_ID`,
	`jinfu_promo_pro`.`promo_inf`.`NAME` AS `PROMO_NAME`
FROM
	(
		(
			(
				(
					`jinfu_loan_pro`.`fp_loan_dtl`
					LEFT JOIN `jinfu_user_pro`.`user_inf` ON (
						(
							`jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID` = `jinfu_user_pro`.`user_inf`.`USER_ID`
						)
					)
				)
				LEFT JOIN `jinfu_loan_pro`.`fp_loan_promo` ON (
					(
						`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_ID` = `jinfu_loan_pro`.`fp_loan_promo`.`LOAN_ID`
					)
				)
			)
			LEFT JOIN `jinfu_promo_pro`.`promo_inf` ON (
				(
					`jinfu_loan_pro`.`fp_loan_promo`.`PROMO_ID` = `jinfu_promo_pro`.`promo_inf`.`PROMO_ID`
				)
			)
		)
		LEFT JOIN `jinfu_user_pro`.`store_inf` ON (
			(
				`jinfu_user_pro`.`store_inf`.`STORE_ID` = (
					SELECT
						min(
							`jinfu_user_pro`.`store_inf`.`STORE_ID`
						)
					FROM
						`jinfu_user_pro`.`store_inf`
					WHERE
						(
							`jinfu_user_pro`.`store_inf`.`USER_ID` = `jinfu_user_pro`.`user_inf`.`USER_ID`
						)
				)
			)
		)
	)
WHERE
(`jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID = 1 or `jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID is null) ;

DROP VIEW IF EXISTS fp_repay_dtl_real;

CREATE
VIEW `fp_repay_dtl_real` AS
SELECT
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_ID` AS `REPAY_ID`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`LOAN_ID` AS `LOAN_ID`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`ACCT_NO` AS `ACCT_NO`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_NAME` AS `LOAN_NAME`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_DT` AS `REPAY_DT`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_PRIN_AMT` AS `REPAY_PRIN_AMT`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_FEE` AS `REPAY_FEE`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_INTR` AS `REPAY_INTR`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_FINE` AS `REPAY_FINE`,
	`jinfu_loan_pro`.`fp_repay_dtl`.`TRANS_MODE` AS `TRANS_MODE`,
	`jinfu_user_pro`.`store_inf`.`PROVINCE` AS `PROVINCE`,
	`jinfu_user_pro`.`store_inf`.`CITY` AS `CITY`,
	`jinfu_user_pro`.`store_inf`.`AREA` AS `AREA`,
	concat(
		'券类型:',
		`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_TYPE`,
		',券名称:',
		`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_NAME`,
		',券码:',
		`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_CODE`
	) AS `COUPON_DESC`,
	(
		CASE
		WHEN (
			`jinfu_loan_pro`.`fp_loan_coupon`.`PRICE` > `jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_INTR`
		) THEN
			`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_INTR`
		ELSE
			`jinfu_loan_pro`.`fp_loan_coupon`.`PRICE`
		END
	) AS `COUPON_PRICE`,
	`c`.`USER_NAME` AS `USER_NAME`,
	`ord`.`RET_CHANNEL_NAME` AS `RET_CHANNEL_NAME`
FROM
	(
		(
			(
				(
					(
						`jinfu_loan_pro`.`fp_repay_dtl`
						LEFT JOIN `jinfu_loan_pro`.`fp_loan_dtl` ON (
							(
								`jinfu_loan_pro`.`fp_repay_dtl`.`LOAN_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_ID`
							)
						)
					)
					LEFT JOIN `jinfu_user_pro`.`store_inf` ON (
						(
							`jinfu_user_pro`.`store_inf`.`STORE_ID` = (
								SELECT
									min(
										`jinfu_user_pro`.`store_inf`.`STORE_ID`
									)
								FROM
									`jinfu_user_pro`.`store_inf`
								WHERE
									(
										`jinfu_user_pro`.`store_inf`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID`
									)
							)
						)
					)
				)
				LEFT JOIN `jinfu_loan_pro`.`fp_loan_coupon` ON (
					(
						`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_ID` = (
							SELECT
								min(
									`jinfu_loan_pro`.`fp_loan_coupon`.`COUPON_ID`
								)
							FROM
								`jinfu_loan_pro`.`fp_loan_coupon`
							WHERE
								(
									`jinfu_loan_pro`.`fp_loan_coupon`.`REPAY_ID` = `jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_ID`
								)
						)
					)
				)
			)
			LEFT JOIN `jinfu_user_pro`.`user_inf` `c` ON (
				(
					`c`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID`
				)
			)
		)
		LEFT JOIN `jinfu_loan_pro`.`ac_pay_recv_ord` `ord` ON (
			(
				`ord`.`ORD_ID` = (
					SELECT
						min(
							`jinfu_loan_pro`.`ac_pay_recv_ord`.`ORD_ID`
						)
					FROM
						`jinfu_loan_pro`.`ac_pay_recv_ord`
					WHERE
						(
							`jinfu_loan_pro`.`ac_pay_recv_ord`.`BIZ_ID` = `jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_ID`
						)
				)
			)
		)
	)
WHERE
		(
			(
				`jinfu_loan_pro`.`fp_repay_dtl`.`STATUS` = '1'
			)
			OR isnull(
				`jinfu_loan_pro`.`fp_repay_dtl`.`STATUS`
			)
		)
		AND (`jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID = 1 or `jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID is null)
ORDER BY
	`jinfu_loan_pro`.`fp_repay_dtl`.`REPAY_ID` ;

DROP VIEW IF EXISTS fp_repay_schd_real;

CREATE
VIEW `fp_repay_schd_real` AS
SELECT
	`jinfu_loan_pro`.`fp_repay_schd`.`SCHD_ID` AS `SCHD_ID`,
	`jinfu_loan_pro`.`fp_repay_schd`.`LOAN_ID` AS `LOAN_ID`,
	`jinfu_loan_pro`.`fp_repay_schd`.`ACCT_NO` AS `ACCT_NO`,
	`jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_NAME` AS `LOAN_NAME`,
	`jinfu_loan_pro`.`fp_repay_schd`.`DUE_DT` AS `DUE_DT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`PAY_DT` AS `PAY_DT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`SEQ_NO` AS `SEQ_NO`,
	`jinfu_loan_pro`.`fp_repay_schd`.`PRIN_PY_AMT` AS `PRIN_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`PRIN_PD_AMT` AS `PRIN_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`INTR_PY_AMT` AS `INTR_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`INTR_PD_AMT` AS `INTR_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`PRIN_FINE_PY_AMT` AS `PRIN_FINE_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`PRIN_FINE_PD_AMT` AS `PRIN_FINE_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`INTR_FINE_PY_AMT` AS `INTR_FINE_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`INTR_FINE_PD_AMT` AS `INTR_FINE_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`FEE_PY_AMT` AS `FEE_PY_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`FEE_PD_AMT` AS `FEE_PD_AMT`,
	`jinfu_loan_pro`.`fp_repay_schd`.`STATUS` AS `STATUS`,
	`jinfu_user_pro`.`store_inf`.`PROVINCE` AS `PROVINCE`,
	`jinfu_user_pro`.`store_inf`.`CITY` AS `CITY`,
	`jinfu_user_pro`.`store_inf`.`AREA` AS `AREA`,
	`c`.`USER_NAME` AS `USER_NAME`
FROM
	(
		(
			(
				`jinfu_loan_pro`.`fp_repay_schd`
				LEFT JOIN `jinfu_loan_pro`.`fp_loan_dtl` ON (
					(
						`jinfu_loan_pro`.`fp_repay_schd`.`LOAN_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`LOAN_ID`
					)
				)
			)
			LEFT JOIN `jinfu_user_pro`.`store_inf` ON (
				(
					`jinfu_user_pro`.`store_inf`.`STORE_ID` = (
						SELECT
							min(
								`jinfu_user_pro`.`store_inf`.`STORE_ID`
							)
						FROM
							`jinfu_user_pro`.`store_inf`
						WHERE
							(
								`jinfu_user_pro`.`store_inf`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID`
							)
					)
				)
			)
		)
		LEFT JOIN `jinfu_user_pro`.`user_inf` `c` ON (
			(
				`c`.`USER_ID` = `jinfu_loan_pro`.`fp_loan_dtl`.`USER_ID`
			)
		)
	)
WHERE
(`jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID = 1 or `jinfu_loan_pro`.`fp_loan_dtl`.FINANCE_SOURCE_ID is null) ;
