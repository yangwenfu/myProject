package com.ylfin.wallet.portal.controller;

import com.ylfin.wallet.portal.controller.req.LoginReq;
import com.ylfin.wallet.portal.controller.req.RequestToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于输出 swagger 文档, 具体登录退出功能由 spring security 实现
 */
@Api("登录认证接口")
@ActiveProfiles("swagger")
@RestController
public class AuthController {

	@ApiOperation(value = "登录", response = RequestToken.class)
	@PostMapping("/login")
	public RequestToken login(@RequestBody LoginReq loginReq) {
		return null;
	}

	@ApiOperation("退出")
	@PostMapping("/logout")
	public void logout() {

	}
}
