package com.ylfin.wallet.portal.config.security;

import java.util.Collection;

import com.ylfin.wallet.portal.CurrentUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SpringSecurityUserAdapter extends User implements CurrentUser {

	private static final long serialVersionUID = 1007955580804484318L;

	private String userId;

	private String realName;

	private String mobile;

	public SpringSecurityUserAdapter(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public SpringSecurityUserAdapter(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public SpringSecurityUserAdapter(String username, String password, Collection<? extends GrantedAuthority> authorities, String userId) {
		super(username, password, authorities);
		this.userId = userId;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
