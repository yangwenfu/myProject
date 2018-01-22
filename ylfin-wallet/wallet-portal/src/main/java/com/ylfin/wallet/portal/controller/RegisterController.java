package com.ylfin.wallet.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.UserService;
import com.ylfin.core.exception.ServiceException;
import com.ylfin.core.sms.SmsOperations;
import com.ylfin.core.tool.VerifyCodeService;
import com.ylfin.wallet.portal.controller.req.UserRegisterReq;
import com.ylfin.wallet.portal.controller.vo.CheckResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("用户注册接口")
@RestController
@RequestMapping("/register")
@Slf4j
public class RegisterController {

	@Autowired
	private UserService userService;

	@Autowired
	private SmsOperations smsOperations;

	@Value("${sms.tplKey.register}")
	private String tplKey;

	@Autowired
	private VerifyCodeService verifyCodeService;

	private static final String VERIFY_CODE_PREFIX = "verifyCode:register:";

	@ApiOperation("检测手机号是否已被注册")
	@GetMapping(value = "check", params = {"mobile"})
	public CheckResult mobileExists(@RequestParam("mobile") String mobile) {
		UserInfoDto userInfoDto = userService.findUserByMobile(mobile);
		CheckResult checkResult = new CheckResult();
		if (userInfoDto != null) {
			checkResult.setResult(true);
		} else {
			checkResult.setResult(false);
		}
		return checkResult;
	}

	@ApiOperation("发送注册验证码")
	@GetMapping("/verify-code")
	public void sendVerifyCode(@RequestParam("mobile") String mobile) {
		if (StringUtils.isBlank(mobile)) {
			throw new ServiceException("手机号码不能为空");
		}
		String key = VERIFY_CODE_PREFIX + mobile;
		String verifyCode = verifyCodeService.generateVerifyCode(key);//获取验证码
		Map<String, String> params = new HashMap<>();
		params.put("code", verifyCode);
		smsOperations.send(tplKey, params, mobile);
	}

    @ApiOperation("验证注册验证码")
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


    @ApiOperation("提交注册")
    @PostMapping()
    public void registerUser(@RequestBody @Valid UserRegisterReq userRegisterReq) {
        //前端调用该/register/check验证是否注册
        String key=VERIFY_CODE_PREFIX+userRegisterReq.getMobile();
        Boolean flag = verifyCodeService.verify(key, userRegisterReq.getVerifyCode());
        if (!flag) {
            throw new ServiceException("验证码不正确!");
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setMobile(userRegisterReq.getMobile());
        userInfoDto.setLoginPassword(userRegisterReq.getPassword());
        userInfoDto.setSource(ESource.REGISTER);
        userService.saveUser(userInfoDto);
        log.info("注册用户:"+userRegisterReq.getMobile());
    }


}
