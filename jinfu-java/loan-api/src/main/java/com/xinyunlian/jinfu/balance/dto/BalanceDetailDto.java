package com.xinyunlian.jinfu.balance.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Willwang on 2017/5/23.
 */
public class BalanceDetailDto implements Serializable{

    private BalanceDetailInteriorDto detail;

    private BalanceDetailLoanDto loan;

    private List<BalanceDetailScheduleDto> schedules;

    public BalanceDetailInteriorDto getDetail() {
        return detail;
    }

    public void setDetail(BalanceDetailInteriorDto detail) {
        this.detail = detail;
    }

    public BalanceDetailLoanDto getLoan() {
        return loan;
    }

    public void setLoan(BalanceDetailLoanDto loan) {
        this.loan = loan;
    }

    public List<BalanceDetailScheduleDto> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<BalanceDetailScheduleDto> schedules) {
        this.schedules = schedules;
    }
}
