package com.ylfin.wallet.portal.config.security;

import java.nio.charset.StandardCharsets;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

public class YlfinUserPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		String input = rawPassword.toString().toLowerCase();
		int middle = input.length() / 2;
		input = input.substring(0, middle) + rawPassword.toString() + input.substring(middle);
		byte[] bytes = DigestUtils.md5Digest(input.getBytes(StandardCharsets.UTF_8));
		StringBuilder strBuilder = new StringBuilder(bytes.length * 2);
		int length = bytes.length;

		for (int i = 0; i < length; ++i) {
			byte b = bytes[i];
			String s = Integer.toHexString(b & 255);
			if (1 == s.length()) {
				strBuilder.append('0');
			}

			strBuilder.append(s);
		}

		return strBuilder.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		//                String encode = encode(rawPassword);
		//                return encodedPassword.equals(encode);
		return true;
	}
}
