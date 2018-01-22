package com.xinyunlian.jinfu.repay.domain;

import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 按月等本等息
 * Created by JL on 2017/08/24.
 */
public class MonthAvgCapitalAvgInterest extends RepayMethod {


    private BigDecimal totalAmt;

    private BigDecimal capital;

    private BigDecimal interest;

    private BigDecimal fee;

    public MonthAvgCapitalAvgInterest(LoanDtlDto loanDtlDto, PromoDto promoDto, Date now) {
        super(loanDtlDto, promoDto, now);
        totalAmt = loanDtlDto.getLoanAmt();
        capital = totalAmt.divide(new BigDecimal(loanDtlDto.getTermLen()), 2, BigDecimal.ROUND_HALF_UP);
        interest = totalAmt.multiply(loanDtlDto.getLoanRt());
        fee = totalAmt.multiply(loanDtlDto.getServiceFeeMonthRt());
    }

    @Override
    public BigDecimal getCapital() {
        return capital.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal getInterest() {
        return interest.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getFee() {
        return fee.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<ScheduleDto> getRepaySchedule() {
        List<ScheduleDto> list = new ArrayList<>();
        BigDecimal surplus = totalAmt;
        for (int period = 1; period <= this.getLoanDtlDto().getTermLen(); period++) {
            surplus = surplus.subtract(capital);
            ScheduleDto schedule = new ScheduleDto();
            schedule.setLoanId(this.getLoanDtlDto().getLoanId());
            schedule.setAcctNo(this.getLoanDtlDto().getAcctNo());
            Date transferDate = getNow();
            if (this.getLoanDtlDto() != null && this.getLoanDtlDto().getTransferDate() != null) {
                transferDate = this.getLoanDtlDto().getTransferDate();
            }
            schedule.setDueDate(DateHelper.afterSeveralMonths(transferDate, period));
            schedule.setSeqNo(period);
            schedule.setShouldCapital(this.getCapital());
            schedule.setShouldInterest(this.getInterest());
            schedule.setShouldFee(this.getFee());
            schedule.setScheduleStatus(EScheduleStatus.NOTYET);
            list.add(schedule);
        }
        ScheduleDto last = list.get(this.getLoanDtlDto().getTermLen() - 1);
        last.setShouldCapital(last.getShouldCapital().add(surplus));
        return list;
    }

    @Override
    public BigDecimal getFine() {
        return null;
    }

}