package com.xinyunlian.jinfu.bank.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardBinDto;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.bank.dto.CardBinDto;
import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MaskUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.finaccbankcard.service.FinAccBankCardService;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by KimLL on 2016/8/26.
 */
@Controller
@RequestMapping(value = "user/bank")
public class BankController {
    @Autowired
    private UserService userService;
    @Autowired
    private BankService bankService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserRealAuthService userRealAuthService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private FinAccBankCardService  finAccBankCardService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private YMMemberService ymMemberService;

    /**
    * 添加银行卡
     * @param bankCardDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> save(@RequestBody BankCardDto bankCardDto) {
        bankCardDto.setUserId(SecurityContext.getCurrentUserId());
        boolean flag = smsService.confirmVerifyCode(bankCardDto.getMobileNo(), bankCardDto.getVerifyCode(), ESmsSendType.BANKCARD);
        if(!flag){
            return ResultDtoFactory.toNack("验证码错误");
        }
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
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.ZFXY);
        contractService.saveContract(userContractDto, userInfoDto);

        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 获得用户支付协议
     *
     * @return
     */
    @RequestMapping(value = "contract/zfxy", method = RequestMethod.GET)
    @ResponseBody
    public String getContractZFXY() {
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.ZFXY);
        userContractDto = contractService.getContract(userContractDto);
        return userContractDto.getContent();
    }

    /**
     * 删除银行卡
     * @param bankCardId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> delete(@RequestParam Long bankCardId) {
        BankCardDto bankCardDto = bankService.getBankCard(bankCardId);
        //检查理财是否绑定银行卡
        if(finAccBankCardService.checkBankCardExist(bankCardDto.getBankCardNo())){
            return  ResultDtoFactory.toNack("您购买了理财产品，无法删除银行卡");
        }
        //检查云码是否绑定银行卡
        if(ymMemberService.findByBankCardId(bankCardId).size() > 0){
            return  ResultDtoFactory.toNack("您绑定了云码，无法删除银行卡");
        }
        //检查小贷是否绑定银行卡
        List<LoanDtlDto> loanDtlDtos = loanService.findByBankCardId(bankCardId);
        if(!CollectionUtils.isEmpty(loanDtlDtos)){
            for(LoanDtlDto loanDtlDto : loanDtlDtos) {
                if (loanDtlDto.getLoanStat() != ELoanStat.PAID) {
                    return ResultDtoFactory.toNack("您购买了贷款产品，无法删除银行卡");
                }
            }
        }
        bankService.deleteBankCard(bankCardId);
        return ResultDtoFactory.toAck("删除成功");
    }

    /**
     * 获取个人银行卡信息
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> get() {
        List<BankCardDto> bankCardDtoList = bankService.findByUserId(SecurityContext.getCurrentUserId());
        bankCardDtoList.forEach(bankCardDto -> {
            bankCardDto.setBankCardNo(MaskUtil.maskMiddleValue(0, 4, bankCardDto.getBankCardNo()));
            bankCardDto.setBankCardName(MaskUtil.maskFirstName(bankCardDto.getBankCardName()));
            bankCardDto.setMobileNo(MaskUtil.maskMiddleValue(3, 4, bankCardDto.getMobileNo()));
        });
        return  ResultDtoFactory.toAck("获取成功",bankCardDtoList);
    }

    /**
     * 通过银行卡获取银行卡所属银行
     * @return
     */
    @RequestMapping(value = "/getCardBin", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getCardBin(@RequestParam String cardNo) {
        BankCardBinDto bankCardBinDto = bankService.findByNumLengthAndBin(cardNo);
        CardBinDto cardBinDto = ConverterService.convert(bankCardBinDto, CardBinDto.class);
        if(bankCardBinDto != null){
            if(null == bankCardBinDto.getBankId()) {
                return ResultDtoFactory.toNack("对不起，暂不支持该银行卡，请更换银行卡");
            }
            if(!StringUtils.equals("借记卡",bankCardBinDto.getCardType())){
                return  ResultDtoFactory.toNack("对不起，暂仅支持借记卡，请更换银行卡");
            }
            BankInfDto bankInfDto = bankService.getBank(bankCardBinDto.getBankId());
            cardBinDto.setBankShortName(bankInfDto.getBankCode());
            cardBinDto.setCardType(ECardType.DEBIT);
            cardBinDto.setBankLogo(bankInfDto.getBankLogo());
            cardBinDto.setBankName(bankInfDto.getBankName());
            return  ResultDtoFactory.toAck("获取成功",cardBinDto);
        }else{
            return  ResultDtoFactory.toNack("卡号输入错误，请核对卡号");
        }
    }


    /**
     * 通过银行卡获取银行卡所属银行(支持代收代付的，目前只给小贷用)
     * @return
     */
    @RequestMapping(value = "/getCardBinSupport", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getCardBinSupport(@RequestParam String cardNo) {
        BankCardBinDto bankCardBinDto = bankService.findByNumLengthAndBin(cardNo);
        CardBinDto cardBinDto = ConverterService.convert(bankCardBinDto, CardBinDto.class);
        if(bankCardBinDto != null){
            if(null == bankCardBinDto.getBankId()) {
                return ResultDtoFactory.toNack("对不起，暂不支持该银行卡，请更换银行卡");
            }
            if(!StringUtils.equals("借记卡",bankCardBinDto.getCardType())){
                return  ResultDtoFactory.toNack("对不起，暂仅支持借记卡，请更换银行卡");
            }
            BankInfDto bankInfDto = bankService.getBank(bankCardBinDto.getBankId());
            if(!bankInfDto.isSupport()){
                return  ResultDtoFactory.toNack("对不起，暂不支持该银行");
            }
            cardBinDto.setBankShortName(bankInfDto.getBankCode());
            cardBinDto.setBankName(bankInfDto.getBankName());
            cardBinDto.setCardType(ECardType.DEBIT);
            cardBinDto.setBankLogo(bankInfDto.getBankLogo());
            return  ResultDtoFactory.toAck("获取成功",cardBinDto);
        }else{
            return  ResultDtoFactory.toNack("卡号输入错误，请核对卡号");
        }
    }

}
