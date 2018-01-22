package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.share.dto.RecommendDto;
import com.xinyunlian.jinfu.share.service.RecommendService;
import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.enums.EOperationType;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.OperationLogService;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by KimLL on 2016/8/31.
 */
@Controller
@RequestMapping(value = "register")
public class RegisterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private OperationLogService logService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private ApiBaiduService apiBaiduService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private RiskUserInfoService  riskUserInfoService;

    /**
     * 验证手机号
     *
     * @param mobile
     * @return
     */
    @ApiOperation(value = "验证手机号是否存在", notes = "传入手机号mobile")
    @RequestMapping(value = "/mobile/verify", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> verifyMobile(@RequestParam String mobile) {
        UserInfoDto userInfoDto = userService.findUserByMobile(mobile);
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("mobile.not.exist"));
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("mobile.verify.pass"));
    }

    /**
     * 获取短信验证码
     *
     * @param mobile
     * @return
     */
    @ApiOperation(value = "获取短信验证码", notes = "传入手机号mobile")
    @RequestMapping(value = "/mobile/code", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getVerifyCode(@RequestParam String mobile, @RequestParam ESmsSendType type) {
        UserInfoDto userInfoDto = userService.findUserByMobile(mobile);
        if (type == ESmsSendType.REGISTER) {
            if (userInfoDto != null) {
                return ResultDtoFactory.toNack(MessageUtil.getMessage("mobile.exist"));
            }
        } else if (type == ESmsSendType.FORGET || type == ESmsSendType.LOGIN) {
            if (userInfoDto == null) {
                return ResultDtoFactory.toNack(MessageUtil.getMessage("mobile.not.exist"));
            }
        }

        String verifyCode = "";
        verifyCode = smsService.getVerifyCode(mobile, type);

        System.out.println(verifyCode);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("sms.send.success"));
    }

    /**
     * 验证手机短信验证码
     *
     * @param mobile
     * @param verifyCode
     * @return
     */
    @ApiOperation(value = "验证短信验证码", notes = "传入手机号mobile和验证码verifyCode")
    @RequestMapping(value = "/mobile/confirm", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> confirmMobile(@RequestParam String mobile,
                                           @RequestParam String verifyCode, @RequestParam ESmsSendType type) {

        boolean flag = smsService.confirmVerifyCode(mobile, verifyCode, type);
        if (!flag) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("sms.code.right"));
    }

    /**
     * 用户注册
     *
     * @param passwordDto
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户注册", notes = "用户对象传入手机号mobile和加密后密码newPassword其他属性设为空;参数verifyCode为短信验证码")
    public ResultDto<Object> registerUser(@RequestBody PasswordDto passwordDto) {
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

        userInfoDto = userService.saveUser(userInfoDto);
        smsService.clearVerifyCode(passwordDto.getMobile(), ESmsSendType.REGISTER);
        //记录注册日志
        passwordDto.setUserId(userInfoDto.getUserId());
        logService.saveLog(passwordDto, EOperationType.REGISTER);
        //用户注册合同
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.ZCXY);
        contractService.saveContract(userContractDto, userInfoDto);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.register.success"));
    }

    /**
     * 分享注册
     *
     * @param passwordDto
     * @return
     */
    @RequestMapping(value = "/{base64UserId}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户注册", notes = "用户对象传入手机号mobile和加密后密码newPassword其他属性设为空;参数verifyCode为短信验证码")
    public ResultDto<Object> registerUser(@RequestBody PasswordDto passwordDto,@PathVariable String base64UserId) {
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
        userInfoDto = userService.saveUser(userInfoDto);
        smsService.clearVerifyCode(passwordDto.getMobile(), ESmsSendType.REGISTER);

        //添加推荐信息
        RecommendDto recommendDto = new RecommendDto();
        recommendDto.setUserId(userInfoDto.getUserId());
        recommendDto.setRefereeUserId(EncryptUtil.getFromBase64(base64UserId));
        recommendService.add(recommendDto);

        //记录注册日志
        passwordDto.setUserId(userInfoDto.getUserId());
        logService.saveLog(passwordDto, EOperationType.REGISTER);
        //用户注册合同
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.ZCXY);
        contractService.saveContract(userContractDto, userInfoDto);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.register.success"));
    }

    /**
     * 获得注册协议
     *
     * @return
     */
    @RequestMapping(value = "contract/ZCXY", method = RequestMethod.GET)
    @ResponseBody
    public String getContractZCXY() {
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.ZCXY);
        userContractDto = contractService.getContract(userContractDto);
        return userContractDto.getContent();
    }


}
