package com.ylfin.wallet.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class WalletPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletPortalApplication.class, args);
	}
}
