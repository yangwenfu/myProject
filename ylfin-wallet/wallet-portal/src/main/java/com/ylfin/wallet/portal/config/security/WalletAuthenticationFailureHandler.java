package com.ylfin.wallet.portal.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ylfin.core.exception.ErrorCoded;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;

public class WalletAuthenticationFailureHandler extends ForwardAuthenticationFailureHandler {

	/**
	 * @param forwardUrl
	 */
	public WalletAuthenticationFailureHandler(String forwardUrl) {
		super(forwardUrl);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		String errorCode = null;
		String message = null;
		if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
			errorCode = "USERNAME_OR_PASSWORD_NOT_MATCH";
			message = "用户名或密码错误";
		} else if (exception instanceof LockedException) {
			errorCode = "ACCOUNT_LOCKED";
			message = "账户被锁定, 请联系客服";
		} else if (exception instanceof DisabledException) {
			errorCode = "ACCOUNT_Disabled";
			message = "帐号已失效";
		} else {
			errorCode = "LOGIN_FAILED";
			message = "登录失败, 请稍后再试";
		}

		request.setAttribute("javax.servlet.error.status_code", HttpStatus.INTERNAL_SERVER_ERROR.value());
		request.setAttribute(ErrorCoded.ATTR_ERROR_CODE, errorCode);
		request.setAttribute("javax.servlet.error.message", message);
		super.onAuthenticationFailure(request, response, exception);
	}
}
