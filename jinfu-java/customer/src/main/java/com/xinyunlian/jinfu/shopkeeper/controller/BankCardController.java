package com.xinyunlian.jinfu.shopkeeper.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.creditline.service.LoanUserCreditLineService;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.shopkeeper.dto.my.BankCardEachDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KimLL on 2016/8/26.
 */
@Controller
@RequestMapping(value = "shopkeeper/bankCard")
public class BankCardController {
    @Autowired
    private BankService bankService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRealAuthService userRealAuthService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private LoanUserCreditLineService loanUserCreditLineService;

    /**
     * 添加银行卡
     * @param bankCardDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> save(@RequestBody BankCardDto bankCardDto) {
        bankCardDto.setUserId(SecurityContext.getCurrentUserId());
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        boolean flag = smsService.confirmVerifyCode(bankCardDto.getMobileNo(), bankCardDto.getVerifyCode(), ESmsSendType.BANKCARD);
        if(!flag){
            return ResultDtoFactory.toNack("验证码错误");
        }
        bankCardDto.setBankCardName(userInfoDto.getUserName());
        bankCardDto.setIdCardNo(userInfoDto.getIdCardNo());
        UserRealAuthDto userRealAuthDto = new UserRealAuthDto();
        userRealAuthDto.setIdCardNo(bankCardDto.getIdCardNo());
        userRealAuthDto.setBankCardNo(bankCardDto.getBankCardNo());
        userRealAuthDto.setName(bankCardDto.getBankCardName());
        userRealAuthDto.setPhone(bankCardDto.getMobileNo());
        boolean real = userRealAuthService.realAuth(userRealAuthDto);
        if(real == false){
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.certify.fail"));
        }
        bankService.saveBankCard(bankCardDto);
        smsService.clearVerifyCode(bankCardDto.getMobileNo(), ESmsSendType.BANKCARD);

        //生成用户支付合同
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.ZFXY);
        contractService.saveContract(userContractDto, userInfoDto);

        if("A".equals(userInfoDto.getAbTest())){
            loanUserCreditLineService.apply(SecurityContext.getCurrentUserId());
        }

        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 获取个人银行卡信息
     * @return
     */
    @RequestMapping(value = "/listSupport", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> listSupport() {
        List<BankCardDto> bankCardDtoList = bankService.findByUserId(SecurityContext.getCurrentUserId());
        List<BankCardEachDto> bankCardDtoSupports = new ArrayList<>();
        if(bankCardDtoList != null) {
            List<BankInfDto> bankSupportList = bankService.findBySupport();
            bankCardDtoList.forEach(bankCardDto -> bankSupportList.forEach(bankSupport ->{
                if(StringUtils.equalsIgnoreCase(bankCardDto.getBankCode(), bankSupport.getBankCode())){
                    BankCardEachDto bankCardEachDto = ConverterService.convert(bankCardDto,BankCardEachDto.class);
                    String bankNo = bankCardDto.getBankCardNo();
                    bankCardEachDto.setBankCardNo(bankNo.substring(bankNo.length()-4, bankNo.length()));
                    bankCardEachDto.setBankCode(bankSupport.getBankCode());
                    bankCardDtoSupports.add(bankCardEachDto);
                }
            }));
        }

        return  ResultDtoFactory.toAck("获取成功",bankCardDtoSupports);
    }

    /**
     * 获取个人银行卡信息
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> list() {
        List<BankCardDto> bankCardDtoList = bankService.findByUserId(SecurityContext.getCurrentUserId());
        bankCardDtoList.forEach(bankCardDto -> {
            if(!StringUtils.isEmpty(bankCardDto.getBankCardNo())){
                String bankNo = bankCardDto.getBankCardNo();
                if(!StringUtils.isEmpty(bankCardDto.getBankCode())){
                    bankCardDto.setBankCode(bankCardDto.getBankCode().toUpperCase());
                }
                bankCardDto.setBankCardNo(bankNo.substring(bankNo.length()-4, bankNo.length()));
            }

        });
        return  ResultDtoFactory.toAck("获取成功",bankCardDtoList);
    }

}
