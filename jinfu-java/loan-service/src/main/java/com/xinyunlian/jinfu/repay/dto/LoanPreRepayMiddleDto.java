package com.xinyunlian.jinfu.repay.dto;

import com.xinyunlian.jinfu.loan.dto.req.LoanRepayLinkDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;

import java.util.List;

/**
 * 预还款中间的DTO
 * Created by Willwang on 2017/1/18.
 */
public class LoanPreRepayMiddleDto {

    private List<ScheduleDto> schedules;

    private List<LoanRepayLinkDto> links;

    public List<ScheduleDto> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDto> schedules) {
        this.schedules = schedules;
    }

    public List<LoanRepayLinkDto> getLinks() {
        return links;
    }

    public void setLinks(List<LoanRepayLinkDto> links) {
        this.links = links;
    }
}
