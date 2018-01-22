package com.xinyunlian.jinfu.repay.dto;

import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 还款计算中间过程的返回对象
 * Created by Willwang on 2017/1/18.
 */
public class LoanRepayMiddleDto {

    private BigDecimal capital;

    private List<ScheduleDto> schedules;

    public LoanRepayMiddleDto(BigDecimal capital, List<ScheduleDto> schedules) {
        this.capital = capital;
        this.schedules = schedules;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public List<ScheduleDto> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDto> schedules) {
        this.schedules = schedules;
    }
}
