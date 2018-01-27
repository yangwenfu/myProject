package com.ylfin.wallet.portal.controller;

import javax.servlet.http.HttpSession;

import com.ylfin.wallet.portal.controller.req.RequestToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("登录令牌")
@RestController
public class TokenController {

	@ApiOperation("获取当前用户访问令牌")
	@PostMapping("/api/token")
	public RequestToken token(HttpSession session) {
		RequestToken token = new RequestToken();
		token.setToken(session.getId());
		return token;
	}
}
