package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by KimLL on 2016/8/31.
 */
@Controller
@RequestMapping(value = "forget")
public class ForgetController {
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;

    /**
     * 忘记密码验证手机短信验证码
     *
     * @param mobile
     * @param verifyCode
     * @return
     */
    @ApiOperation(value = "验证短信验证码", notes = "传入手机号mobile和验证码verifyCode")
    @RequestMapping(value = "/mobile/confirm", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> confirmMobile(@RequestParam String mobile, @RequestParam String verifyCode) {
        boolean flag = smsService.confirmVerifyCode(mobile, verifyCode, ESmsSendType.FORGET);
        if (!flag) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("sms.code.right"));
    }

    /**
     * 重置密码
     *
     * @param passwordDto
     * @return
     */
    @ApiOperation(value = "重置密码", notes = "用户对象传入手机号mobile和加密后密码newPassword其他属性设为空;参数;verifyCode为短信验证码")
    @RequestMapping(value = "/reset/password", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> resetPassword(@RequestBody PasswordDto passwordDto) {
        boolean flag = smsService.confirmVerifyCode(passwordDto.getMobile(), passwordDto.getVerifyCode(), ESmsSendType.FORGET);
        if (!flag) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
        }
        UserInfoDto userInfoDto = userService.findUserByMobile(passwordDto.getMobile());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.not.exist"));
        }
        passwordDto.setUserId(userInfoDto.getUserId());
        userService.updatePassword(passwordDto);
        smsService.clearVerifyCode(passwordDto.getMobile(), ESmsSendType.FORGET);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }
}
