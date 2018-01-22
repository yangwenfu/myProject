package com.xinyunlian.jinfu.exam.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by menglei on 2017年05月04日.
 */
public class ExamSearchDto extends PagingDto<ExamDto> {

    private static final long serialVersionUID = 1L;

    private String name;

    private String type;//首考FIRST 常规ROUTINE

    private String status;//未开始UNSTART 进行中ING 结束OVER

    private String startTime;

    private String endTime;

    private String monthTime;

    private String monthStartTime;

    private String monthEndTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(String monthTime) {
        this.monthTime = monthTime;
    }

    public String getMonthStartTime() {
        return monthStartTime;
    }

    public void setMonthStartTime(String monthStartTime) {
        this.monthStartTime = monthStartTime;
    }

    public String getMonthEndTime() {
        return monthEndTime;
    }

    public void setMonthEndTime(String monthEndTime) {
        this.monthEndTime = monthEndTime;
    }
}
