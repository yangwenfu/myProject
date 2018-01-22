package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MaskUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dto.depository.dto.LoanDepositoryRepayConfirmDto;
import com.xinyunlian.jinfu.loan.dto.repay.RepayHistoryItemDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.loan.service.PrivateCalcService;
import com.xinyunlian.jinfu.loan.service.PrivateRepayService;
import com.xinyunlian.jinfu.overdue.service.LoanOverdueService;
import com.xinyunlian.jinfu.repay.dto.LoanRepayRespDto;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.log4j.spi.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author willwang
 */

@RestController
@RequestMapping(value="loan/repay/depository")
public class LoanDepositoryRepayController {

    @Autowired
    private PrivateRepayService privateRepayService;

    @Autowired
    private PrivateCalcService privateCalcService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankService bankService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDepositoryRepayController.class);

    /**
     * 获取验证码
     * @param
     * @return
     */
    @RequestMapping(value="code", method = RequestMethod.GET)
    public ResultDto code(@RequestParam String loanId){

        String userId = SecurityContext.getCurrentUserId();

        LoanDtlDto loanDtlDto = loanService.get(loanId);

        if(!userId.equals(loanDtlDto.getUserId())){
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_YOURS, "不能操作非自己的贷款");
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(loanDtlDto.getUserId());

        smsService.getVerifyCode(userInfoDto.getMobile(), ESmsSendType.LOAN_DEPOSITORY_REPAY);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 发起还款
     * @param request
     * @return
     */
    @RequestMapping(value="start", method = RequestMethod.POST)
    public ResultDto start(@RequestBody RepayReqDto request){

        String userId = SecurityContext.getCurrentUserId();

        LoanDtlDto loanDtlDto = loanService.get(request.getLoanId());

        if(!userId.equals(loanDtlDto.getUserId())){
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_YOURS, "不能操作非自己的贷款");
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(loanDtlDto.getUserId());

        if(!smsService.confirmVerifyCode(userInfoDto.getMobile(), request.getCode(), ESmsSendType.LOAN_DEPOSITORY_REPAY)){
            throw new BizServiceException(EErrorCode.LOAN_DEPOSITORY_CODE_ERROR, "验证码错误");
        }

        privateRepayService.depositoryRepayStart(request);

        smsService.clearVerifyCode(userInfoDto.getMobile(), ESmsSendType.LOAN_DEPOSITORY_REPAY);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 确认还款，会返回银行卡信息等
     * @param request
     * @return
     */
    @RequestMapping(value="confirm", method = RequestMethod.POST)
    public ResultDto confirm(@RequestBody RepayReqDto request){

        String userId = SecurityContext.getCurrentUserId();

        LoanDtlDto loanDtlDto = loanService.get(request.getLoanId());

        if(!userId.equals(loanDtlDto.getUserId())){
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_YOURS, "不能操作非自己的贷款");
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(loanDtlDto.getUserId());
        BankCardDto bankCardDto = bankService.getBankCard(loanDtlDto.getBankCardId());

        LoanDepositoryRepayConfirmDto confirmDto = new LoanDepositoryRepayConfirmDto();

        confirmDto.setMobile(MaskUtil.getLastFour(userInfoDto.getMobile()));
        confirmDto.setBankCardNo(MaskUtil.maskMiddleValue(bankCardDto.getBankCardNo()));
        LoanCalcDto loanCalcDto = privateCalcService.calc(userId, request);
        confirmDto.setAmt(NumberUtil.roundTwo(loanCalcDto.getPromoTotal()));

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), confirmDto);
    }

}
