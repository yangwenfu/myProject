package com.xinyunlian.jinfu.loan.repay.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.cashier.dto.LoanPayDto;
import com.xinyunlian.jinfu.cashier.dto.ProcessRetDto;
import com.xinyunlian.jinfu.cashier.service.LoanPayService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ERepayType;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.service.PayRecvOrdService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.repay.dto.resp.LoanCashierCallbackDto;
import com.xinyunlian.jinfu.repay.service.RepayService;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import com.xinyunlian.jinfu.service.toDto.PayRecvToDTOService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Willwang
 */
@Controller
@RequestMapping(value = "/loan/repay")
public class LoanRepayController {

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private RepayService repayService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private LoanPayService loanPayService;

    @Autowired
    private PayRecvOrdService payRecvOrdService;

    @Autowired
    private UserService userService;

    private final static Logger LOGGER = LoggerFactory.getLogger(LoanRepayController.class);

    /**
     * 根据时间计算本期应还金额
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/should", method = RequestMethod.GET)
    @RequiresPermissions({"MANUAL_WITHHOLD"})
    public ResultDto should(@RequestParam String scheduleId, @RequestParam String date, @RequestParam BigDecimal capital) {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("{}", scheduleService);
        }
        ScheduleDto schedule = scheduleService.get(scheduleId);

        LoanDtlDto loanDtlDto = loanService.get(schedule.getLoanId());
        LoanProductDetailDto product = loanApplService.getProduct(loanDtlDto.getApplId());

        if(!this.isValidDate(date)){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "时间格式错误");
        }

        RepayReqDto request = new RepayReqDto();
        request.setLoanId(loanDtlDto.getLoanId());
        request.setAmt(capital);
        request.setPeriod(schedule.getSeqNo());
        request.setForceDate(DateHelper.getDate(date));

        ERepayType repayType = null;
        if(product.getRepayMode() == ERepayMode.MONTH_AVE_CAP_PLUS_INTR){
            repayType = ERepayType.PERIOD;
        }else if(product.getRepayMode() == ERepayMode.INTR_PER_DIEM){
            repayType = ERepayType.DAY;
        }
        request.setRepayType(repayType);
        LoanCalcDto loanCalcDto = repayService.calc(null, request);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanCalcDto);
    }

    @ResponseBody
    @RequestMapping(value = "/manual", method = RequestMethod.GET)
    @RequiresPermissions({"MANUAL_WITHHOLD"})
    public ResultDto<PayRecvOrdDto> manual(@RequestParam String scheduleId, @RequestParam String date, @RequestParam String reason, @RequestParam BigDecimal capital) {
        ScheduleDto schedule = scheduleService.get(scheduleId);
        LoanDtlDto loanDtlDto = loanService.get(schedule.getLoanId());
        if(!this.isValidDate(date)){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "时间格式错误");
        }
        if(reason.length() > 60){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "原因过长");
        }

        LoanProductDetailDto product = loanApplService.getProduct(loanDtlDto.getApplId());

        RepayReqDto request = new RepayReqDto();
        request.setLoanId(loanDtlDto.getLoanId());
        request.setAmt(capital);
        request.setPeriod(schedule.getSeqNo());
        request.setForceDate(DateHelper.getDate(date));

        ERepayType repayType = null;
        if(product.getRepayMode() == ERepayMode.MONTH_AVE_CAP_PLUS_INTR){
            repayType = ERepayType.PERIOD;
        }else if(product.getRepayMode() == ERepayMode.INTR_PER_DIEM){
            repayType = ERepayType.DAY;
        }
        request.setRepayType(repayType);

        repayService.withhold(null, EPrType.RECEIVE, request);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    private boolean isValidDate(String str) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = formatter.parse(str);
            return str.equals(formatter.format(date));
        }catch(Exception e){
            return false;
        }
    }
}
