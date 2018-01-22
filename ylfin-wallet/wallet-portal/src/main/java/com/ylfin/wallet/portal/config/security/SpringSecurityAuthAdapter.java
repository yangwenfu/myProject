package com.ylfin.wallet.portal.config.security;

import com.ylfin.core.auth.AuthenticationAdapter;
import com.ylfin.wallet.portal.CurrentUser;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

class SpringSecurityAuthAdapter implements AuthenticationAdapter<CurrentUser, String> {

	@Override
	public String getCurrentUserId() {
		CurrentUser currentUser = getCurrentUserInfo();
		if (currentUser != null) {
			return currentUser.getUserId();
		}
		return null;
	}

	@Override
	public CurrentUser getCurrentUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof CurrentUser) {
			return (CurrentUser) principal;
		}
		return null;
	}
}
