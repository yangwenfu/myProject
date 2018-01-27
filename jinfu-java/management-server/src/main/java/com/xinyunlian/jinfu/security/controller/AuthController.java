package com.xinyunlian.jinfu.security.controller;

import javax.validation.Valid;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.filter.HttpContextHolder;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.authc.ESourceType;
import com.xinyunlian.jinfu.common.security.authc.SecurityService;
import com.xinyunlian.jinfu.common.security.authc.StatelessAuthcFilter;
import com.xinyunlian.jinfu.common.security.authc.dto.UserInfo;
import com.xinyunlian.jinfu.common.util.CookieUtils;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.web.WebUtil;
import com.xinyunlian.jinfu.server.dto.MgtUserInfoDto;
import com.xinyunlian.jinfu.server.dto.SignInDto;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.enums.EMgtSmsType;
import com.xinyunlian.jinfu.user.enums.EMgtUserStatus;
import com.xinyunlian.jinfu.user.service.MgtSmsService;
import com.xinyunlian.jinfu.user.service.MgtUserService;

/**
 * Created by dongfangchao on 2017/1/23/0023.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MgtUserService mgtUserService;

    @Autowired
    private MgtSmsService mgtSmsService;

    /**
     * 登录
     *
     * @param signInDto
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultDto<Object> login(@RequestBody @Valid SignInDto signInDto, BindingResult result) {

        try {
            if (result.hasErrors()){
                return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
            }

            MgtUserDto userDto = null;
            userDto = mgtUserService.getMgtUserInfByLoginId(signInDto.getLoginId());
            if (userDto == null){
                userDto = mgtUserService.getMgtUserByMobile(signInDto.getLoginId());
                if (userDto == null){
                    return ResultDtoFactory.toNack("用户名不存在");
                }
                signInDto.setLoginId(userDto.getLoginId());
            }
            if (userDto.getStatus() == EMgtUserStatus.FROZEN){
                return ResultDtoFactory.toNack("用户已被禁用");
            }
            if (userDto.getStatus() == EMgtUserStatus.DELETE){
                return ResultDtoFactory.toNack("用户已被删除");
            }

            if (StringUtils.isEmpty(userDto.getMobile())){
                return ResultDtoFactory.toNack("用户手机号没有配置，请联系管理员");
            }

            if (StringUtils.isEmpty(signInDto.getVerificationCode())){
                String verifyCode = mgtSmsService.getVerifyCode(userDto.getMobile(), EMgtSmsType.LOGIN_PWD);
                LOGGER.debug(userDto.getMobile() + " 验证码：" + verifyCode);
                return ResultDtoFactory.toAck("短信已发送");
            }else{
                Boolean checkResult = mgtSmsService.checkVerifyCode(userDto.getMobile(), signInDto.getVerificationCode(), EMgtSmsType.LOGIN_PWD);
                if (!checkResult){
                    return ResultDtoFactory.toNack("验证码错误");
                }

                UserInfo userInfo = SecurityContext.login(signInDto.getLoginId(), signInDto.getPassword(), ESourceType.MANAGEMENT);
                userInfo = securityService.getRolePermission(userInfo);

                MgtUserInfoDto mgtUserInfoDto = ConverterService.convert(userInfo, MgtUserInfoDto.class);
                mgtUserInfoDto.setName(userDto.getName());
                mgtUserInfoDto.setMobile(userDto.getMobile());
                mgtUserInfoDto.setPermissionCodes(userInfo.getPermissionCodes());
                mgtUserInfoDto.setRoleCodes(userInfo.getRoleCodes());

                LOGGER.debug("返回的权限信息：" + JsonUtil.toJson(mgtUserInfoDto));
                mgtSmsService.clearVerifyCode(userDto.getMobile(), EMgtSmsType.LOGIN_PWD);

                CookieUtils.removeCookie(HttpContextHolder.getResponse(), StatelessAuthcFilter.TOKEN_NAME, WebUtil.getFullUrlBasedOn("/"), null);
                CookieUtils.addCookie(HttpContextHolder.getResponse(), StatelessAuthcFilter.TOKEN_NAME, userInfo.getToken(), -1, "/", null, null);
                return ResultDtoFactory.toAck("登录成功", mgtUserInfoDto);
            }
        } catch (IncorrectCredentialsException e) {
            return ResultDtoFactory.toNack("用户名或密码错误");
        }
    }

    /**
     * 不需要验证码登录
     * @param signInDto
     * @param result
     * @return
     */
    @RequestMapping(value = "/oldLogin", method = RequestMethod.POST)
    public ResultDto<Object> oldLogin(@RequestBody @Valid SignInDto signInDto, BindingResult result) {

        try {
            if (result.hasErrors()){
                return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
            }

            MgtUserDto userDto = null;
            userDto = mgtUserService.getMgtUserInfByLoginId(signInDto.getLoginId());
            if (userDto == null){
                userDto = mgtUserService.getMgtUserByMobile(signInDto.getLoginId());
                if (userDto == null){
                    return ResultDtoFactory.toNack("用户名不存在");
                }
                signInDto.setLoginId(userDto.getLoginId());
            }
            if (userDto.getStatus() == EMgtUserStatus.FROZEN){
                return ResultDtoFactory.toNack("用户已被禁用");
            }
            if (userDto.getStatus() == EMgtUserStatus.DELETE){
                return ResultDtoFactory.toNack("用户已被删除");
            }

            if (StringUtils.isEmpty(userDto.getMobile())){
                return ResultDtoFactory.toNack("用户手机号没有配置，请联系管理员");
            }

            UserInfo userInfo = SecurityContext.login(signInDto.getLoginId(), signInDto.getPassword(), ESourceType.MANAGEMENT);
            userInfo = securityService.getRolePermission(userInfo);

            MgtUserInfoDto mgtUserInfoDto = ConverterService.convert(userInfo, MgtUserInfoDto.class);
            mgtUserInfoDto.setName(userDto.getName());
            mgtUserInfoDto.setMobile(userDto.getMobile());
            mgtUserInfoDto.setPermissionCodes(userInfo.getPermissionCodes());
            mgtUserInfoDto.setRoleCodes(userInfo.getRoleCodes());

            LOGGER.debug("返回的权限信息：" + JsonUtil.toJson(mgtUserInfoDto));

            CookieUtils.removeCookie(HttpContextHolder.getResponse(), StatelessAuthcFilter.TOKEN_NAME, WebUtil.getFullUrlBasedOn("/"), null);
            CookieUtils.addCookie(HttpContextHolder.getResponse(), StatelessAuthcFilter.TOKEN_NAME, userInfo.getToken(), -1, "/", null, null);
            return ResultDtoFactory.toAck("登录成功", mgtUserInfoDto);
        } catch (IncorrectCredentialsException e) {
            return ResultDtoFactory.toNack("用户名或密码错误");
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultDto<String> logout() {
        SecurityContext.logout();
        return ResultDtoFactory.toAck("退出登录成功");
    }

    @RequestMapping(value = "/userInfo")
    public ResultDto<MgtUserInfoDto> userInfo() {
        if (SecurityContext.isAuthenticated()) {
            String currentUserId = SecurityContext.getCurrentUserId();
            MgtUserDto userInf = mgtUserService.getMgtUserInf(currentUserId);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(currentUserId);
            userInfo = securityService.getRolePermission(userInfo);
            MgtUserInfoDto mgtUserInfoDto = ConverterService.convert(userInfo, MgtUserInfoDto.class);
            mgtUserInfoDto.setName(userInf.getName());
            mgtUserInfoDto.setMobile(userInf.getMobile());
            mgtUserInfoDto.setPermissionCodes(userInfo.getPermissionCodes());
            mgtUserInfoDto.setRoleCodes(userInfo.getRoleCodes());
            return ResultDtoFactory.toAckData(mgtUserInfoDto);
        }
        return ResultDtoFactory.toNack("未登录");
    }
}
