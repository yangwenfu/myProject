package com.ylfin.wallet.portal.controller;

import com.ylfin.wallet.portal.controller.vo.AccountInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("虚拟账户接口")
@RestController
@RequestMapping("/api/account")
public class AccountController {

	@ApiOperation("返回当前账户信息, 如果返回 null, 表示未开户")
	@GetMapping("/info")
	public AccountInfo getAccountInfo() {
		// TODO 查询余额
		return new AccountInfo();
	}
}
