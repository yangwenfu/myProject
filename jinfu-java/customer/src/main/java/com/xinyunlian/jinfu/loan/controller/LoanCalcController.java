package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.loan.dto.loan.LoanAllCouponDto;
import com.xinyunlian.jinfu.loan.enums.ERepayType;
import com.xinyunlian.jinfu.loan.service.PrivateCalcService;
import com.xinyunlian.jinfu.loan.service.PrivatePromoService;
import com.xinyunlian.jinfu.promo.dto.AllCouponDto;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.repay.service.RepayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author willwang
 */
@Controller
@RequestMapping(value = "loan/calc")
public class LoanCalcController {

    @Autowired
    private RepayService repayService;

    @Autowired
    private PrivateCalcService privateCalcService;

    @Autowired
    private PrivatePromoService privatePromoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanCalcController.class);

    /**
     * 提前还全部计算
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public ResultDto all(@RequestParam String loanId) {
        String userId = SecurityContext.getCurrentUserId();

        RepayReqDto request = new RepayReqDto();
        request.setRepayType(ERepayType.PRE_ALL);
        request.setLoanId(loanId);
        LoanCalcDto loanCalcDto = repayService.calc(userId, request);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanCalcDto);
    }

    /**
     * 提前还本期计算
     */
    @RequestMapping(value = "current", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public ResultDto current(@RequestParam String loanId) {
        String userId = SecurityContext.getCurrentUserId();

        RepayReqDto request = new RepayReqDto();
        request.setRepayType(ERepayType.PRE_CURRENT);
        request.setLoanId(loanId);
        LoanCalcDto loanCalcDto = repayService.calc(userId, request);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanCalcDto);
    }

    /**
     * 随借随还计算
     */
    @RequestMapping(value = "any", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public ResultDto any(@RequestParam String loanId, @RequestParam BigDecimal capital) {
        String userId = SecurityContext.getCurrentUserId();

        RepayReqDto request = new RepayReqDto();
        request.setRepayType(ERepayType.DAY);
        request.setLoanId(loanId);
        request.setAmt(capital);
        LoanCalcDto loanCalcDto = repayService.calc(userId, request);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanCalcDto);
    }

    /**
     * 统一计算方法
     */
    @RequestMapping(value = "start", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto start(@RequestBody RepayReqDto request){
        String userId = SecurityContext.getCurrentUserId();
        LoanCalcDto loanCalcDto = privateCalcService.calc(userId, request);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanCalcDto);
    }

    /**
     * 获取还款可用的优惠券列表
     */
    @RequestMapping(value = "coupon", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto coupon(@RequestParam String loanId){
        String userId = SecurityContext.getCurrentUserId();
        AllCouponDto allCouponDto = privatePromoService.listLoanCoupons(userId, loanId);
        LoanAllCouponDto loanAllCouponDto = privatePromoService.convertLoanAllCouponDto(allCouponDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanAllCouponDto);
    }

}
