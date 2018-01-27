package com.ylfin.wallet.portal.controller;

import com.ylfin.core.exception.ServiceException;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiIgnore
@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/occurs-error")
	public Object occursError() {
		throw new ServiceException("TEST_ERROR");
	}
}
