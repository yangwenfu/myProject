package com.ylfin.wallet.portal.controller.vo;

import lombok.Data;

/**
 *Create by yangwenfu on 2018/1/22
 */
@Data
public class UserDetail {
	private String userId;
	private String userName;
	private String userHeadPic;
	private Boolean identityAuth;
	private int bankCarNum;
	private Boolean dealPasswordIsExist;
}
