package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.service.ApiYunMaService;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.store.dto.req.CertifyDto;
import com.xinyunlian.jinfu.store.service.CustomerUserService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by menglei on 2016年09月22日.
 */
@Controller
@RequestMapping(value = "open-api/store/user")
public class ApiUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomerUserService customerUserService;
    @Autowired
    private ApiYunMaService apiYunMaService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserRealAuthService userRealAuthService;
    @Autowired
    private BankService bankService;

    /**
     * 实名认证
     *
     * @param certifyDto
     * @return
     */
    @RequestMapping(value = "/certify", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certify(@RequestBody CertifyDto certifyDto) {
        customerUserService.certify(certifyDto);
        return ResultDtoFactory.toAck("实名认证成功");
    }

    /**
     * 根据openId获取用户信息
     *
     * @param openid
     * @return
     */
    @RequestMapping(value = "/getByOpenid", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getByOpenId(@RequestParam String openid) {
        String mobile = apiYunMaService.getUserMobile(openid);
        UserInfoDto userInfoDto = userService.findUserByMobile(mobile);
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        return ResultDtoFactory.toAck("获取成功", userInfoDto);
    }

//    /**
//     * 添加银行卡
//     * @param certifyDto
//     * @return
//     */
//    @RequestMapping(value = "/saveBankCard", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultDto<Object> saveBankCard(@RequestBody CertifyDto certifyDto) {
//        BankCardDto bankCardDto = ConverterService.convert(certifyDto, BankCardDto.class);
//        bankCardDto.setBankCardName(certifyDto.getUserName());
//        bankCardDto.setMobileNo(certifyDto.getMobile());
//
//        if (StringUtils.isNotEmpty(bankCardDto.getVerifyCode())) {
//            boolean flag = smsService.confirmVerifyCode(bankCardDto.getMobileNo(), bankCardDto.getVerifyCode(), ESmsSendType.BANKCARD);
//            if(!flag){
//                return ResultDtoFactory.toNack("验证码错误");
//            }
//        }
//
//        UserRealAuthDto userRealAuthDto = new UserRealAuthDto();
//        userRealAuthDto.setIdCardNo(bankCardDto.getIdCardNo());
//        userRealAuthDto.setBankCardNo(bankCardDto.getBankCardNo());
//        userRealAuthDto.setName(bankCardDto.getBankCardName());
//        userRealAuthDto.setPhone(bankCardDto.getMobileNo());
//        boolean real = userRealAuthService.realAuth(userRealAuthDto);
//        if(real == false){
//            return ResultDtoFactory.toNack("添加银行卡失败");
//        }
//        bankService.saveBankCard(bankCardDto);
//        smsService.clearVerifyCode(bankCardDto.getMobileNo(), ESmsSendType.BANKCARD);
//        return ResultDtoFactory.toAck("添加成功");
//    }

    /**
     * 添加银行卡
     * @param certifyDto
     * @return
     */
    @RequestMapping(value = "/saveBankCard", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> saveBankCard(@RequestBody CertifyDto certifyDto) {
        BankCardDto bankCardDto = ConverterService.convert(certifyDto, BankCardDto.class);
        bankCardDto.setBankCardName(certifyDto.getUserName());
        bankCardDto.setMobileNo(certifyDto.getMobile());

        if (StringUtils.isNotEmpty(bankCardDto.getVerifyCode())) {
            boolean flag = smsService.confirmVerifyCode(bankCardDto.getMobileNo(), bankCardDto.getVerifyCode(), ESmsSendType.BANKCARD);
            if(!flag){
                return ResultDtoFactory.toNack("验证码错误");
            }
        }

        UserRealAuthDto userRealAuthDto = new UserRealAuthDto();
        userRealAuthDto.setIdCardNo(bankCardDto.getIdCardNo());
        userRealAuthDto.setBankCardNo(bankCardDto.getBankCardNo());
        userRealAuthDto.setName(bankCardDto.getBankCardName());
        userRealAuthDto.setPhone(bankCardDto.getMobileNo());
        boolean real = userRealAuthService.realAuth(userRealAuthDto);
        if(real == false){
            return ResultDtoFactory.toNack("添加银行卡失败");
        }
        bankService.saveBankCard(bankCardDto);
        smsService.clearVerifyCode(bankCardDto.getMobileNo(), ESmsSendType.BANKCARD);
        return ResultDtoFactory.toAck("添加成功");
    }
}
