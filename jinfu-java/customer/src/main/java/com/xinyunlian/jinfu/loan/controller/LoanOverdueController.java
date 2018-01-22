package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.service.PrivateAitouziContractService;
import com.xinyunlian.jinfu.loan.service.PrivateOverdueService;
import com.xinyunlian.jinfu.overdue.dto.OverdueDayDetailDto;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthDetailDto;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthPreviewDto;
import com.xinyunlian.jinfu.overdue.service.LoanOverdueService;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.Oneway;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author willwang
 */
@Controller
@RequestMapping(value = "loan/overdue")
public class LoanOverdueController {

    @Autowired
    private LoanOverdueService loanOverdueService;

    @Autowired
    private PrivateOverdueService privateOverdueService;

    @Autowired
    private PrivateAitouziContractService privateAitouziContractService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanOverdueController.class);

    /**
     * 随借随还逾期情况
     */
    @RequestMapping(value = "day/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto day(@RequestParam String loanId, @RequestParam(required = false) EFinanceSourceType financeSourceType) {
        String userId = SecurityContext.getCurrentUserId();
        if(financeSourceType == null){
            financeSourceType = EFinanceSourceType.OWN;
        }
        OverdueDayDetailDto overdueDayDetailDto = null;
        switch(financeSourceType){
            case OWN:
                overdueDayDetailDto = privateOverdueService.dayPreview(userId, loanId);
                break;
            case AITOUZI:
                overdueDayDetailDto = (OverdueDayDetailDto) privateAitouziContractService.overdue(loanId);
                break;
            default:
                break;
        }


        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), overdueDayDetailDto);
    }

    /**
     * 等额本息逾期情况
     */
    @RequestMapping(value = "month/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto month(@RequestParam String loanId, @RequestParam(required = false) EFinanceSourceType financeSourceType) {
        if(financeSourceType == null){
            financeSourceType = EFinanceSourceType.OWN;
        }

        List<OverdueMonthDetailDto> list = null;

        switch(financeSourceType){
            case OWN:
                list = loanOverdueService.month(loanId);
                break;
            case AITOUZI:
                list = (List<OverdueMonthDetailDto>) privateAitouziContractService.overdue(loanId);
                break;
            default:
                break;
        }

        if(list.size() > 0){
            for (OverdueMonthDetailDto overdueMonthDetailDto : list) {
                overdueMonthDetailDto.setShould(NumberUtil.roundTwo(overdueMonthDetailDto.getShould()));
                overdueMonthDetailDto.setFine(NumberUtil.roundTwo(overdueMonthDetailDto.getFine()));
            }
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 等额本息逾期还款预览
     * @param loanId
     * @return
     */
    @RequestMapping(value = "month/preview", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto monthPreview(@RequestParam String loanId, @RequestParam BigDecimal amt){
        List<OverdueMonthPreviewDto> list = privateOverdueService.monthPreview(loanId, amt);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

}
