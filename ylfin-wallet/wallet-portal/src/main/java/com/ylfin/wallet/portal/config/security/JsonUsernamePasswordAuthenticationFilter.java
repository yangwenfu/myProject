package com.ylfin.wallet.portal.config.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ylfin.core.tool.JacksonUtils;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (isJsonRequest(request)) {

			try {
				Map<String, Object> map = JacksonUtils.jsonToObject(request.getInputStream(), Map.class);
				String username = (String) map.get(getUsernameParameter());
				String password = (String) map.get(getPasswordParameter());

				if (username == null) {
					username = "";
				}

				if (password == null) {
					password = "";
				}

				username = username.trim();

				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
						username, password);

				// Allow subclasses to set the "details" property
				setDetails(request, authRequest);

				return this.getAuthenticationManager().authenticate(authRequest);
			} catch (IOException e) {
				throw new BadCredentialsException("无法解析用户名密码.");
			}
		} else {
			return super.attemptAuthentication(request, response);
		}
	}

	private boolean isJsonRequest(HttpServletRequest request) {
		return request.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE);
	}
}