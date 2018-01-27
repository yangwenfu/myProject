package com.xinyunlian.jinfu.exam.dto;

import com.xinyunlian.jinfu.exam.enums.EExamType;

import java.io.Serializable;

/**
 * Created by menglei on 2017/05/02.
 */

public class FirstExamDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long examId;

    private String name;

    private EExamType type;

    private String url;

    private String startTime;

    private String endTime;

    private Integer passLine;

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EExamType getType() {
        return type;
    }

    public void setType(EExamType type) {
        this.type = type;
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

    public Integer getPassLine() {
        return passLine;
    }

    public void setPassLine(Integer passLine) {
        this.passLine = passLine;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
