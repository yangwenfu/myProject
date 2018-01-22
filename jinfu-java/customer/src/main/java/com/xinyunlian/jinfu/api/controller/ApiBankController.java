package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.dto.BankCardOpenApi;
import com.xinyunlian.jinfu.api.dto.CardBinOpenApi;
import com.xinyunlian.jinfu.api.dto.MobileOpenApi;
import com.xinyunlian.jinfu.api.dto.resp.BankCardOpenApiResp;
import com.xinyunlian.jinfu.api.validate.OpenApi;
import com.xinyunlian.jinfu.bank.dto.BankCardBinDto;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.bank.dto.CardBinDto;
import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KimLL on 2016/8/26.
 */
@Controller
@RequestMapping(value = "open-api/bank")
public class ApiBankController {
    @Autowired
    private BankService bankService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRealAuthService userRealAuthService;
    @Autowired
    private ContractService contractService;

    /**
     * 添加银行卡
     * @param bankCardOpenApi
     * @return
     */
    @OpenApi
    @RequestMapping(value = "/addBankCard", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加银行卡")
    public ResultDto<Object> addBankCard(@RequestBody @Valid BankCardOpenApi bankCardOpenApi,
                                         BindingResult result) {
        if (result.hasErrors()){
            return ResultDtoFactory.toNack("input.param.invalid",result.getFieldError().getDefaultMessage());
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(bankCardOpenApi.getUserId());
        if(userInfoDto == null){
            return ResultDtoFactory.toNack("user.not.exist");
        }

        BankCardDto bankCardDto = ConverterService.convert(bankCardOpenApi,BankCardDto.class);
        bankCardDto.setUserId(bankCardDto.getUserId());
        UserRealAuthDto userRealAuthDto = new UserRealAuthDto();
        userRealAuthDto.setIdCardNo(bankCardDto.getIdCardNo());
        userRealAuthDto.setBankCardNo(bankCardDto.getBankCardNo());
        userRealAuthDto.setName(bankCardDto.getBankCardName());
        userRealAuthDto.setPhone(bankCardDto.getMobileNo());
        boolean real = userRealAuthService.realAuth(userRealAuthDto);
        if(real == false){
            return ResultDtoFactory.toNack("user.certify.fail");
        }
        try {
            bankService.saveWithUserName(bankCardDto);
        }catch (BizServiceException e){
            if(e.getError() == EErrorCode.BANK_CARD_NO_IS_EXIST){
                return ResultDtoFactory.toNack("bank.card.is.exist");
            }else if(e.getError() == EErrorCode.USER_NAME_AND_BANK_USER_NAME_NOT_SAME){
                return ResultDtoFactory.toNack("username.not.same");
            }else if(e.getError() == EErrorCode.USER_ID_CARD_AND_BANK_ID_CARD_NOT_SAME){
                return ResultDtoFactory.toNack("id.card.not.same");
            }
        }

        //生成用户支付合同
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.ZFXY);
        contractService.saveContract(userContractDto, userInfoDto);

        return ResultDtoFactory.toAck("common.operate.success");
    }

    /**
     *  获取个人银行卡信息
     * @param mobileOpenApiDto
     * @return
     */
    @OpenApi
    @RequestMapping(value = "/getBankCard", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取个人银行卡信息")
    public ResultDto<Object> getBankCard(@RequestBody MobileOpenApi mobileOpenApiDto) {
        if(StringUtils.isEmpty(mobileOpenApiDto.getMobile())){
            return ResultDtoFactory.toNack("input.param.invalid","手机号不可为空");
        }
        UserInfoDto userInfoDto = userService.findUserByMobile(mobileOpenApiDto.getMobile());
        if(userInfoDto == null){
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.not.exist"));
        }
        List<BankCardDto> bankCardDtoList = bankService.findByUserId(userInfoDto.getUserId());

        List<BankCardOpenApiResp> bankCardOpenApiResps = new ArrayList<>();
        List<BankInfDto> bankSupportList = bankService.findBySupport();

        bankCardDtoList.forEach(bankCardDto -> bankSupportList.forEach(bankSupport ->{
            if(StringUtils.equalsIgnoreCase(bankCardDto.getBankCode(), bankSupport.getBankCode())){
                BankCardOpenApiResp bankCardOpenApiResp = ConverterService.convert(bankCardDto,BankCardOpenApiResp.class);
                bankCardOpenApiResp.setBankCode(bankSupport.getBankCode());
                bankCardOpenApiResps.add(bankCardOpenApiResp);
            }
        }));

        return  ResultDtoFactory.toAck("common.operate.success",bankCardOpenApiResps);
    }

    /**
     * 通过银行卡获取银行卡所属银行(支持代收代付的)
     * @param bankCardOpenApi
     * @return
     */
    @OpenApi
    @RequestMapping(value = "/getCardBinSupport", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "卡bin接口")
    public ResultDto<Object> getCardBinSupport(@RequestBody BankCardOpenApi bankCardOpenApi) {
        if(StringUtils.isEmpty(bankCardOpenApi.getBankCardNo())){
            return ResultDtoFactory.toNack("input.param.invalid","银行卡号不可为空");
        }

        BankCardBinDto bankCardBinDto = bankService.findByNumLengthAndBin(bankCardOpenApi.getBankCardNo());
        CardBinOpenApi cardBinDto = ConverterService.convert(bankCardBinDto, CardBinOpenApi.class);
        if(bankCardBinDto != null){
            if(null == bankCardBinDto.getBankId()) {
                return ResultDtoFactory.toNack("bankcard.not.support");
            }
            if(!StringUtils.equals("借记卡",bankCardBinDto.getCardType())){
                return  ResultDtoFactory.toNack("bankcard.not.debitcard");
            }
            BankInfDto bankInfDto = bankService.getBank(bankCardBinDto.getBankId());
            cardBinDto.setBankShortName(bankInfDto.getBankCode());
            cardBinDto.setCardType(ECardType.DEBIT);
            cardBinDto.setBankLogo(bankInfDto.getBankLogo());
            cardBinDto.setBankName(bankInfDto.getBankName());
            return  ResultDtoFactory.toAck("common.operate.success",cardBinDto);
        }else{
            return  ResultDtoFactory.toNack("bankcard.error");
        }
    }

}
