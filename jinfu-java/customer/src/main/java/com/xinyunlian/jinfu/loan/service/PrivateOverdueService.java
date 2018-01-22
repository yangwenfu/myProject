package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.enums.ERepayType;
import com.xinyunlian.jinfu.overdue.dto.OverdueDayDetailDto;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthPreviewDto;
import com.xinyunlian.jinfu.overdue.service.LoanOverdueService;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.repay.service.RepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Willwang on 2017/1/6.
 */
@Service
public class PrivateOverdueService {

    @Autowired
    private LoanOverdueService loanOverdueService;

    @Autowired
    private RepayService repayService;

    /**
     * 随借随还逾期概况
     * @param loanId
     * @return
     */
    public OverdueDayDetailDto dayPreview(String userId, String loanId){
        OverdueDayDetailDto overdueDayDetailDto = new OverdueDayDetailDto();

        RepayReqDto request = new RepayReqDto();
        request.setLoanId(loanId);
        request.setRepayType(ERepayType.DAY);
        LoanCalcDto loanCalcDto = repayService.calc(userId, request);

        overdueDayDetailDto.setSurplus(NumberUtil.roundTwo(loanCalcDto.getSurplus().add(loanCalcDto.getCapital())));
        overdueDayDetailDto.setFine(NumberUtil.roundTwo(loanCalcDto.getFine()));
        overdueDayDetailDto.setDays(loanCalcDto.getFineDays());
        return overdueDayDetailDto;
    }

    /**
     * 等额本息逾期还款预览
     */
    public List<OverdueMonthPreviewDto> monthPreview(String loanId, BigDecimal amt){
        List<OverdueMonthPreviewDto> list = loanOverdueService.monthPreview(loanId, amt, null);

        for (OverdueMonthPreviewDto overdueMonthPreviewDto : list) {
            overdueMonthPreviewDto.setCapital(NumberUtil.roundTwo(overdueMonthPreviewDto.getCapital()));
            overdueMonthPreviewDto.setInterest(NumberUtil.roundTwo(overdueMonthPreviewDto.getInterest()));
            overdueMonthPreviewDto.setFine(NumberUtil.roundTwo(overdueMonthPreviewDto.getFine()));
            overdueMonthPreviewDto.setSurplus(NumberUtil.roundTwo(overdueMonthPreviewDto.getSurplus()));
            overdueMonthPreviewDto.setSum(NumberUtil.roundTwo(overdueMonthPreviewDto.getSum()));
        }

        return list;
    }
}
