package com.xinyunlian.jinfu.loan.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.calc.service.LoanCalcService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.repay.RepayHistoryItemDto;
import com.xinyunlian.jinfu.loan.service.PrivateCalcService;
import com.xinyunlian.jinfu.loan.service.PrivateRepayService;
import com.xinyunlian.jinfu.overdue.service.LoanOverdueService;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.service.PayRecvOrdService;
import com.xinyunlian.jinfu.repay.dto.LoanRepayRespDto;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.repay.service.RepayService;
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

@Controller
@RequestMapping(value="loan/repay")
public class LoanRepayController {

    @Autowired
    private PrivateRepayService privateRepayService;

    @Autowired
    private LoanOverdueService loanOverdueService;


    private static final Logger LOGGER = LoggerFactory.getLogger(LoanRepayController.class);

    /**
     * 发起还款
     * @param request
     * @return
     */
    @RequestMapping(value="start", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto start(@RequestBody RepayReqDto request){
        Map<String, String> params = privateRepayService.cashierRepayStart(request);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), params);
    }

    /**
     * 主动关闭还款单
     * @param repayId
     * @return
     */
    @RequestMapping(value="close", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto close(@RequestParam String repayId){
        privateRepayService.close(repayId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 收银台状态查询
     * @param repayId
     * @return
     */
    @RequestMapping(value="detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto detail(@RequestParam String repayId){
        LoanRepayRespDto loanRepayRespDto = privateRepayService.detail(repayId);
        loanRepayRespDto = privateRepayService.fixStatus(loanRepayRespDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanRepayRespDto);
    }

    /**
     * 还款记录
     * @param loanId
     * @return
     */
    @RequestMapping(value="history", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto history(@RequestParam String loanId){
        List<RepayHistoryItemDto> list = privateRepayService.list(loanId);
        list = privateRepayService.fixStatus(list);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 逾期全部还清的金额计算
     */
    @RequestMapping(value = "allin", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto allin(@RequestParam String loanId){
        BigDecimal sum = loanOverdueService.all(loanId);
        sum = NumberUtil.roundTwo(sum);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), sum);
    }

    /**
     * 还款异步回调
     * @param request
     * @return
     */
    @RequestMapping(value="callback")
    @ResponseBody
    public String callback(HttpServletRequest request){
        return privateRepayService.cashierCallback(request);
    }

}
