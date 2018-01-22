package com;

import com.xinyunlian.jinfu.common.util.EncryptUtil;

/**
 * @author Sephy
 * @since: 2017-06-26
 */
public class EncryptUtilTest {

	public static void main(String[] args) throws Exception {
		String string = EncryptUtil.encryptMd5("96e79218965eb72c92a549dd5a330112", "96e79218965eb72c92a549dd5a330112");
        System.out.println(string);
    }
}
