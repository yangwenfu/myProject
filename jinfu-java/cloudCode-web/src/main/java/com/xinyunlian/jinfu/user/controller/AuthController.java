package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.authc.ESourceType;
import com.xinyunlian.jinfu.common.security.authc.dto.UserInfo;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.user.dto.SignInDto;
import com.xinyunlian.jinfu.wechat.service.ApiWeChatService;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by JL on 2016/8/19.
 */
@Controller
@RequestMapping(value = "user/auth")
public class AuthController {

    @Autowired
    private ApiWeChatService apiWeChatService;
    @Autowired
    private YMUserInfoService yMUserInfoService;

    /**
     * 获取微信url
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWeChatUrl", method = RequestMethod.GET)
    public ResultDto<Object> getUrl(@RequestParam String url) {
        if (StringUtils.isNotEmpty(SecurityContext.getCurrentUserId())) {//已经登录
            return ResultDtoFactory.toAck("获取成功", null);
        }
        String urls = apiWeChatService.getAuthCodeUrl(url, "ylfin");
        return ResultDtoFactory.toAck("获取成功", urls);
    }

    /**
     * 登录
     *
     * @param signInDto
     * @return
     */
    @RequestMapping(value = "/weChatLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> login(@RequestBody SignInDto signInDto, BindingResult result) {
        UserInfo userInfo;
        if (result.hasErrors()){
            return ResultDtoFactory.toNack("参数不能为空");
        }
        String openId = apiWeChatService.getAuthOpenid(signInDto.getCode());
        if (StringUtils.isEmpty(openId)) {
            return ResultDtoFactory.toNack("access_token失效");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByOpenId(openId);
        if (yMUserInfoDto == null) {//不存在的用户，新增
            yMUserInfoDto = new YMUserInfoDto();
            yMUserInfoDto.setOpenId(openId);
            yMUserInfoDto = yMUserInfoService.addUserInfo(yMUserInfoDto);
        }
        try {
            userInfo = SecurityContext.login(yMUserInfoDto.getYmUserId(), yMUserInfoDto.getOpenId(), ESourceType.WECHAT);
        } catch (IncorrectCredentialsException e) {
            return ResultDtoFactory.toNack("用户名或密码错误", e);
        }
        return ResultDtoFactory.toAck("登录成功", userInfo);
    }

    /**
     * test登录(测试用)
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "/testLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> testLogin(@RequestParam String openId) {
        if (AppConfigUtil.isProdEnv()) {
            return ResultDtoFactory.toNack("test登录只能在qa环境使用");
        }
        UserInfo userInfo;
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByOpenId(openId);
        if (yMUserInfoDto == null) {//不存在的用户，新增
            yMUserInfoDto = new YMUserInfoDto();
            yMUserInfoDto.setOpenId(openId);
            yMUserInfoDto = yMUserInfoService.addUserInfo(yMUserInfoDto);
        }
        try {
            userInfo = SecurityContext.login(yMUserInfoDto.getYmUserId(), yMUserInfoDto.getOpenId(), ESourceType.WECHAT);
        } catch (IncorrectCredentialsException e) {
            return ResultDtoFactory.toNack("用户名或密码错误", e);
        }
        return ResultDtoFactory.toAck("登录成功", userInfo);
    }
}
