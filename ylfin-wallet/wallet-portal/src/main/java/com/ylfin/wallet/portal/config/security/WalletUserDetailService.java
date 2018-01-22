package com.ylfin.wallet.portal.config.security;

import java.util.Collections;

import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class WalletUserDetailService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(WalletUserDetailService.class);

	private UserService userService;

	public WalletUserDetailService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserInfoDto userInfoDto = userService.findUserByMobileWithCredentials(username);
		if (userInfoDto == null) {
			logger.info("找不到手机号码为 {} 的用户", username);
			throw new UsernameNotFoundException(username);
		}
		SpringSecurityUserAdapter springSecurityUserAdapter = new SpringSecurityUserAdapter(userInfoDto.getMobile(), userInfoDto.getLoginPassword(), Collections.emptyList());
		springSecurityUserAdapter.setUserId(userInfoDto.getUserId());
		springSecurityUserAdapter.setMobile(userInfoDto.getMobile());
		springSecurityUserAdapter.setRealName(userInfoDto.getUserName());
		return springSecurityUserAdapter;
	}
}
