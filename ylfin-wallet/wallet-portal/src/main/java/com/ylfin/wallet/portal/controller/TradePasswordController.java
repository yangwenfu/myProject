package com.ylfin.wallet.portal.controller;

import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.ylfin.core.auth.AuthenticationAdapter;
import com.ylfin.core.exception.ServiceException;
import com.ylfin.wallet.portal.CurrentUser;
import com.ylfin.wallet.portal.controller.vo.CheckResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("支付密码接口")
@RestController
@RequestMapping("/api/trade-password")
@Slf4j
public class TradePasswordController {

	@Autowired
	private AuthenticationAdapter<CurrentUser, String> authenticationAdapter;

	@Autowired
	private UserService userService;



	@ApiOperation("检测是否设置过支付密码")
	@GetMapping("/check")
	public CheckResult exists() {
		UserInfoDto userInfoDto = userService.findUserByUserId(authenticationAdapter.getCurrentUserId());
		CheckResult checkResult = new CheckResult();
		if (userInfoDto == null) {
			throw new ServiceException("当前用户不存在");
		}
		if (StringUtils.isNotBlank(userInfoDto.getDealPassword())) {
			checkResult.setResult(true);
		} else {
			checkResult.setResult(false);
		}
		return checkResult;
	}

    @ApiOperation("修改支付密码")
    @PutMapping
    public void modifyPassword(@RequestParam String origPassword, @RequestParam String newPassword) {
        String currentUserId = authenticationAdapter.getCurrentUserId();
        if (StringUtils.isBlank(currentUserId)){
            throw new ServiceException("用户未登录!");
        }
        if (StringUtils.isBlank(origPassword)){
            throw new ServiceException("原支付密码不能为空!");
        }
        if (StringUtils.isBlank(newPassword)){
            throw new ServiceException("新支付密码为空!不做修改");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(currentUserId);
        if (userInfoDto == null){
            throw new ServiceException("当前用户不存在!");
        }
        if (StringUtils.isBlank(userInfoDto.getDealPassword())){
            throw new ServiceException("未曾设置支付密码!");
        }
	   Boolean b = userService.verifyDealPassword(currentUserId,origPassword);//验证原支付密码
        if (!b){
            throw new ServiceException("原支付密码不正确!");
        }
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setUserId(currentUserId);
        passwordDto.setDealPassword(newPassword);
        userService.updateDealPassword(passwordDto);
        log.info("用户: "+userInfoDto.getMobile()+" ,修改支付密碼");
    }

    @ApiOperation("设置支付密码")
    @PutMapping("/set-deal-password")
    public void setDealPassword(@RequestParam String dealPassword){
        //用exists()判断有没有设置过支付密码
        String currentUserId = authenticationAdapter.getCurrentUserId();
        if (StringUtils.isBlank(currentUserId)){
            throw new ServiceException("用户未登录!");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(currentUserId);
        if (userInfoDto == null){
            throw new ServiceException("当前用户不存在!");
        }
        if (StringUtils.isBlank(dealPassword)){
            throw new ServiceException("支付密码不能为空");
        }
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setUserId(currentUserId);
        passwordDto.setDealPassword(dealPassword);
        userService.updateDealPassword(passwordDto);
        log.info("用户: "+userInfoDto.getMobile()+" ,设置支付密碼");
    }




}
