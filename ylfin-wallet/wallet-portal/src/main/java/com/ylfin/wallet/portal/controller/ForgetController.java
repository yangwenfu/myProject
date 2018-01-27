package com.ylfin.wallet.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.ylfin.core.exception.ServiceException;
import com.ylfin.core.sms.SmsOperations;
import com.ylfin.core.tool.VerifyCodeService;
import com.ylfin.wallet.portal.controller.req.ForgetPasswordReq;
import com.ylfin.wallet.portal.controller.vo.CheckResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *Create by yangwenfu on 2018/1/18
 */
@Api("用户忘记登陆密码接口")
@RestController
@RequestMapping("/forget")
public class ForgetController {
	@Autowired
	private UserService userService;

	@Autowired
	private SmsOperations smsOperations;

	@Autowired
	private VerifyCodeService verifyCodeService;

	@Value("${sms.tplKey.forget}")
	private String tplKey;

	private static final String VERIFY_CODE_PREFIX="verifyCode:forget:";

	@ApiOperation("发送忘记密码验证码")
	@GetMapping("/verify-code-forget")
	public void sendVerifyCodeByForgetPassword(@RequestParam("mobile") String mobile) {
		if (StringUtils.isBlank(mobile)){
			throw new ServiceException("手机号码不能为空");
		}
		String key=VERIFY_CODE_PREFIX+mobile;
		String verifyCode = verifyCodeService.generateVerifyCode(key);
		Map<String, String> params = new HashMap<>();
		params.put("code", verifyCode);
		smsOperations.send(tplKey, params, mobile);
	}


	@ApiOperation("验证忘记密码验证码")
	@GetMapping("/check-verify-code")
	public CheckResult checkVerifyCode(@RequestParam("mobile") String mobile ,@RequestParam("verifyCode") String verifyCode) {
		if (StringUtils.isBlank(mobile)) {
			throw new ServiceException("手机号码不能为空");
		}
		if (StringUtils.isBlank(verifyCode)) {
			throw new ServiceException("验证码不能为空");
		}
		String key=VERIFY_CODE_PREFIX+mobile;
		Boolean flag = verifyCodeService.verify(key, verifyCode);
		CheckResult checkResult = new CheckResult();
		checkResult.setResult(flag);
		return checkResult;
	}


	@ApiOperation("忘记登陆密码")
	@PostMapping("/forgetPassword")
	public void forgetPassword(@RequestBody @Valid ForgetPasswordReq forgetPasswordReq){
		UserInfoDto userInfoDto = userService.findUserByMobile(forgetPasswordReq.getMobile());
		if (userInfoDto == null || StringUtils.isBlank(userInfoDto.getUserId())) {
			throw new ServiceException("该手机用户不存在!");
		}
		//验证短信验证码
		String key=VERIFY_CODE_PREFIX+forgetPasswordReq.getMobile();
		Boolean flag = verifyCodeService.verify(key, forgetPasswordReq.getVerifyCode());
		if (!flag) {
			throw new ServiceException("验证码不正确!");
		}
		PasswordDto passwordDto = new PasswordDto();
		passwordDto.setUserId(userInfoDto.getUserId());
		passwordDto.setNewPassword(forgetPasswordReq.getNewPassword());
		passwordDto.setMobile(forgetPasswordReq.getMobile());
		passwordDto.setVerifyCode(forgetPasswordReq.getVerifyCode());
		userService.updatePassword(passwordDto);

	}
}
