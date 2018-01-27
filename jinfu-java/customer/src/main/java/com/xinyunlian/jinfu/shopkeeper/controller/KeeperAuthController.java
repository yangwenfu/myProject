package com.xinyunlian.jinfu.shopkeeper.controller;

import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.authc.ESourceType;
import com.xinyunlian.jinfu.common.security.authc.dto.UserInfo;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.push.enums.PushObject;
import com.xinyunlian.jinfu.push.service.PushService;
import com.xinyunlian.jinfu.shopkeeper.dto.my.UserDto;
import com.xinyunlian.jinfu.shopkeeper.service.MyInfoService;
import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.RiskControlDto;
import com.xinyunlian.jinfu.user.dto.SignInDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.enums.EOperationType;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.user.service.OperationLogService;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by JL on 2016/8/19.
 */
@Controller
@RequestMapping(value = "shopkeeper/auth")
public class KeeperAuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeeperAuthController.class);

    @Autowired
    private MyInfoService myInfoService;
    @Autowired
    private PushService pushService;

    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private OperationLogService logService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private ApiBaiduService apiBaiduService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private RiskUserInfoService riskUserInfoService;
    @Autowired
    private DealerUserService dealerUserService;

    /**
     * 手机登陆接口
     *
     * @param signInDto
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> login(@RequestBody SignInDto signInDto) {

        UserInfo userInfo;
        UserDto userDto;

        try {
            userInfo = SecurityContext.login(signInDto.getUserName(), signInDto.getPassword(), ESourceType.JINFU_APP);
            //记录登录日志
            logService.saveLog(signInDto, EOperationType.LOGIN);
            userDto = myInfoService.getUser(userInfo.getUserId());
            userDto.setToken(userInfo.getToken());

        } catch (IncorrectCredentialsException e) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.login.error"));
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.login.success"), userDto);
    }

    @ApiOperation(value = "手机短信登陆接口")
    @RequestMapping(value = "/smsLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> smsLogin(@RequestBody SignInDto signInDto) {
        UserInfo userInfo;
        UserDto userDto;

        try {
            boolean flag = smsService.confirmVerifyCode(signInDto.getUserName(), signInDto.getVerifyCode(), ESmsSendType.LOGIN);
            if (!flag) {
                return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
            }
            UserInfoDto userInfoDto = userService.findUserByMobile(signInDto.getUserName());
            userInfo = SecurityContext.login(userInfoDto.getUserId(), ESourceType.JINFU_APP);
            //记录登录日志
            logService.saveLog(signInDto, EOperationType.LOGIN);
            userDto = myInfoService.getUser(userInfo.getUserId());
            userDto.setToken(userInfo.getToken());

            smsService.clearVerifyCode(signInDto.getUserName(), ESmsSendType.LOGIN);
        } catch (IncorrectCredentialsException e) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.login.error"));
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.login.success"), userDto);
    }

    /**
     * 手机退出登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logoutApp", method = RequestMethod.GET)
    public ResultDto<String> logout() {
        pushService.unBindRegistrationId(SecurityContext.getCurrentUserId(), PushObject.ZG);
        SecurityContext.logout();
        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.logout.success"));
    }

    /**
     * 用户注册
     *
     * @param passwordDto
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户注册", notes = "用户对象传入手机号mobile和加密后密码newPassword其他属性设为空;参数verifyCode为短信验证码")
    public ResultDto<Object> registerUser(@RequestBody PasswordDto passwordDto,
                                          @RequestHeader(value = "qd-source",required = false) String qdSource) {
        //记录登录日志
        if(!StringUtils.isEmpty(qdSource)){
            if(passwordDto.getContent() == null){
                RiskControlDto riskControlDto = new RiskControlDto();
                riskControlDto.setSource(qdSource);
                passwordDto.setContent(riskControlDto);
            }else{
                passwordDto.getContent().setSource(qdSource);
            }

        }

        UserInfoDto userInfoDto = userService.findUserByMobile(passwordDto.getMobile());
        if (userInfoDto != null) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("mobile.exist"));
        }
        boolean flag = smsService.confirmVerifyCode(passwordDto.getMobile(), passwordDto.getVerifyCode(), ESmsSendType.REGISTER);
        if (!flag) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
        }
        userInfoDto = new UserInfoDto();
        userInfoDto.setMobile(passwordDto.getMobile());
        userInfoDto.setLoginPassword(passwordDto.getNewPassword());
        userInfoDto.setSource(ESource.REGISTER);

        //ABTest
        if (passwordDto.getContent() != null && StringUtils.isNotEmpty(passwordDto.getContent().getGps())) {//没坐标不显示
            ApiBaiduDto apiBaiduDto = apiBaiduService.getArea(passwordDto.getContent().getGps());
            SysAreaInfDto sysAreaInfDto = new SysAreaInfDto();
            if (apiBaiduDto != null) {
                sysAreaInfDto = sysAreaInfService.getSysAreaByGbCode(apiBaiduDto.getGbCode());
                if (sysAreaInfDto == null) {
                    LOGGER.info("baidu gbcode error:" + JsonUtil.toJson(apiBaiduDto));
                }else {
                    try {
                        String[] pathArray = sysAreaInfDto.getTreePath().split(",");
                        if (pathArray != null && pathArray.length > 0) {
                            AuthLoginDto authLoginDto = new AuthLoginDto();
                            authLoginDto.setProvinceId(Long.parseLong(pathArray[1]));
                            authLoginDto.setCityId(Long.parseLong(pathArray[2]));
                            authLoginDto.setAreaId(sysAreaInfDto.getId());
                            if (riskUserInfoService.canSpider(authLoginDto)) {
                                if (authLoginDto.getCityId() != null) {
                                    long areaId = authLoginDto.getCityId().longValue();
                                    if (areaId == 2219 || areaId == 44051 || areaId == 44049 || areaId == 811
                                            || areaId == 825 || areaId == 899 || areaId == 913 || areaId == 2939
                                            || areaId == 2958 || areaId == 2973 || areaId == 2981 || areaId == 44057
                                            || areaId == 44054) {
                                        userInfoDto.setAbTest("A");
                                    }
                                    if (areaId == 941 && AppConfigUtil.isQAEnv()) {
                                        userInfoDto.setAbTest("A");
                                    }
                                }
                            }
                        }
                    }catch(Exception e){
                        LOGGER.info("keeper register gps to area error");
                    }
                }
            }
        }

        if(!StringUtils.isEmpty(passwordDto.getRecUser())) {
            DealerUserDto dealerUserDto = dealerUserService.findDealerUserByMobile(passwordDto.getRecUser());
            if(dealerUserDto != null){
                userInfoDto.setDealerUserId(dealerUserDto.getUserId());
            }
        }

        userInfoDto = userService.saveUser(userInfoDto);
        smsService.clearVerifyCode(passwordDto.getMobile(), ESmsSendType.REGISTER);
        //记录注册日志
        passwordDto.setUserId(userInfoDto.getUserId());
        logService.saveLog(passwordDto, EOperationType.REGISTER);
        //用户注册合同
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.ZCXY);
        contractService.saveContract(userContractDto, userInfoDto);

        UserInfo userInfo = SecurityContext.login(userInfoDto.getMobile(), userInfoDto.getLoginPassword(), ESourceType.JINFU_APP);
        logService.saveLog(passwordDto, EOperationType.LOGIN);
        UserInfoDto userRefresh = userService.findUserByUserId(userInfoDto.getUserId());

        UserDto userDto = ConverterService.convert(userRefresh,UserDto.class);
        userDto.setToken(userInfo.getToken());

        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.register.success"),userDto);
    }

}
