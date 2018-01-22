package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerStatus;
import com.xinyunlian.jinfu.dealer.service.DealerService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.common.security.authc.ESourceType;
import com.xinyunlian.jinfu.user.dto.DealerUserInfoDto;
import com.xinyunlian.jinfu.user.dto.SignInDto;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by JL on 2016/8/19.
 */
@Controller
@RequestMapping(value = "user/auth")
public class AuthController {

    @Autowired
    private DealerUserService dealerUserService;
    @Autowired
    private DealerService dealerService;

    /**
     * 登录
     *
     * @param signInDto
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> login(@RequestBody SignInDto signInDto) {
        try {
            SecurityContext.login(signInDto.getUserName(), signInDto.getPassword(), ESourceType.DEALER_USER);
        } catch (IncorrectCredentialsException e) {
            return ResultDtoFactory.toNack("用户名或密码错误");
        }
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        if (dealerUserDto != null) {
            if (EDealerUserStatus.FROZEN.equals(dealerUserDto.getStatus())) {
                SecurityContext.logout();
                return ResultDtoFactory.toNack("您的帐号已被禁用");
            } else if (EDealerUserStatus.DELETE.equals(dealerUserDto.getStatus())) {
                SecurityContext.logout();
                return ResultDtoFactory.toNack("用户名或密码错误");
            }
            DealerDto dealerDto = dealerService.getDealerById(dealerUserDto.getDealerId());
            if (dealerDto == null || !EDealerStatus.NORMAL.equals(dealerDto.getStatus())) {
                SecurityContext.logout();
                return ResultDtoFactory.toNack("您的帐号已被禁用");
            }
        }
        DealerUserInfoDto dealerUserInfoDto = ConverterService.convert(dealerUserDto, DealerUserInfoDto.class);
        dealerUserInfoDto.setDealerName(dealerUserDto.getDealerDto().getDealerName());
        dealerUserInfoDto.setToken(SecurityContext.getCurrentToken());
        return ResultDtoFactory.toAck("登录成功", dealerUserInfoDto);
    }
}
