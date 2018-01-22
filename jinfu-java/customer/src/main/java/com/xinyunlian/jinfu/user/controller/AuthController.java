package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.entity.id.IdUtil;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.authc.ESourceType;
import com.xinyunlian.jinfu.common.security.authc.dto.UserInfo;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.user.dto.*;
import com.xinyunlian.jinfu.user.enums.EOperationType;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.OperationLogService;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.PublicKey;
import java.util.Map;

/**
 * Created by JL on 2016/8/19.
 */
@Controller
@RequestMapping(value = "user/auth")
@Api(description = "用户认证相关")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final static String STORE_INFO_REDEIS_SUFFIX = "_OauthStoreInfo";

    private static PublicKey AIO_RSA_PUB_KEY;

    @Autowired
    private OperationLogService logService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCacheManager cacheManager;

    @Autowired
    private SmsService smsService;


    /**
     * 登录接口
     *
     * @param signInDto
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> login(@RequestBody SignInDto signInDto, HttpServletRequest httpServletRequest) {
        try {
            SecurityContext.login(signInDto.getUserName(), signInDto.getPassword(), ESourceType.JINFU_WEB);
            //记录登录日志
            RiskControlDto riskControlDto = new RiskControlDto();
            riskControlDto.setIp(httpServletRequest.getRemoteAddr());
            signInDto.setContent(riskControlDto);
            logService.saveLog(signInDto, EOperationType.LOGIN);
        } catch (IncorrectCredentialsException e) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.login.error"));
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.login.success"));
    }

    @ApiOperation(value = "手机短信登陆接口")
    @RequestMapping(value = "/smsLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> smsLogin(@RequestBody SignInDto signInDto, HttpServletRequest httpServletRequest) {
        try {
            boolean flag = smsService.confirmVerifyCode(signInDto.getUserName(), signInDto.getVerifyCode(), ESmsSendType.LOGIN);
            if (!flag) {
                return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
            }
            UserInfoDto userInfoDto = userService.findUserByMobile(signInDto.getUserName());
            SecurityContext.login(userInfoDto.getUserId(), ESourceType.JINFU_WEB);

            //记录登录日志
            RiskControlDto riskControlDto = new RiskControlDto();
            riskControlDto.setIp(httpServletRequest.getRemoteAddr());
            signInDto.setContent(riskControlDto);
            logService.saveLog(signInDto, EOperationType.LOGIN);

            smsService.clearVerifyCode(signInDto.getUserName(), ESmsSendType.LOGIN);
        } catch (IncorrectCredentialsException e) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.login.error"));
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.login.success"));
    }

    /**
     * 手机登陆接口
     *
     * @param signInDto
     * @return
     */
    @RequestMapping(value = "/loginApp", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> loginApp(@RequestBody SignInDto signInDto) {
        try {
            UserInfo userInfo = SecurityContext.login(signInDto.getUserName(), signInDto.getPassword(), ESourceType.JINFU_APP);
            //记录登录日志
            logService.saveLog(signInDto, EOperationType.LOGIN);
            return ResultDtoFactory.toAck(MessageUtil.getMessage("user.login.success"), userInfo.getToken());
        } catch (IncorrectCredentialsException e) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.login.error"));
        }

    }


    /**
     * 外部跳转认证
     *
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    @RequestMapping(value = "/outerRSA", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> outerRSA(@RequestBody Map<String, String> map) throws UnsupportedEncodingException, URISyntaxException {
        if (AIO_RSA_PUB_KEY == null) {
            File keyFile = new File(this.getClass().getResource(AppConfigUtil.getConfig("aio.rsa.path")).toURI().getPath());
            AIO_RSA_PUB_KEY = RSAEncodeUtils.getPEMPublicKey(keyFile);
        }
        String ciphertext = map.get("rsa");
        LOGGER.debug("加密信息：{}", ciphertext);
        byte[] bytes = RSAEncodeUtils.decryptByPublicKey(Base64.decodeBase64(ciphertext), AIO_RSA_PUB_KEY);
        String result = new String(bytes, ApplicationConstant.ENCODING);
        LOGGER.debug("解密信息：{}", result);
        // 1.解密信息转userInfo  2.userInfo判断是否存在    3.存在返回token,不存在返回用户信息让用户进行激活
        OuterRsaDto outerRsaDto = JsonUtil.toObject(OuterRsaDto.class, result);
        //发送时间超过两小时的加密信息返回错误
//        if ((System.currentTimeMillis() - Long.valueOf(outerRsaDto.getSendtime())) > 60 * 60 * 2 * 1000) {
//            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "加密信息已过期");
//        }
        StoreInfDto storeInfDto = outerRsaDto.getStoreInfo();
        UserInfoDto userInfoDto = userService.getUserInfoByTobaccoNo(storeInfDto.getTobaccoCertificateNo());
        if (userInfoDto != null) {
            UserInfo userInfo = SecurityContext.login(userInfoDto.getUserId(), ESourceType.AIO_APP);
            return ResultDtoFactory.toAck(MessageUtil.getMessage("user.login.success"), userInfo.getToken());
        } else {
            //跳转到激活页
            String storeNo = IdUtil.produceUUID();
            cacheManager.getCache(CacheType.DEFAULT).put(storeNo + STORE_INFO_REDEIS_SUFFIX, storeInfDto);
            UserActiveDto activeDto = ConverterService.convert(storeInfDto, UserActiveDto.class);
            activeDto.setAccess(storeNo);
            activeDto.setPassword(RandomUtil.getMixStr(6));
            return ResultDtoFactory.toNack("用户未激活", activeDto);
        }
    }


    /**
     * 退出登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultDto<String> logout() {
        SecurityContext.logout();
        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.logout.success"));
    }

}
