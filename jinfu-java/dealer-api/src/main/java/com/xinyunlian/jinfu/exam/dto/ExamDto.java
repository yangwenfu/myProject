package com.xinyunlian.jinfu.exam.dto;

import com.xinyunlian.jinfu.exam.enums.EExamStatus;
import com.xinyunlian.jinfu.exam.enums.EExamType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by menglei on 2017年05月02日.
 */
public class ExamDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long examId;

    private String name;

    private EExamType type;

    private String url;

    private Date startTime;

    private Date endTime;

    private Integer passLine;

    private EExamStatus status;

    private String createOpId;

    private String createOpName;

    private Date createTs;

    private List<Long> examIds = new ArrayList<>();

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public EExamStatus getStatus() {
        return status;
    }

    public void setStatus(EExamStatus status) {
        this.status = status;
    }

    public String getCreateOpId() {
        return createOpId;
    }

    public void setCreateOpId(String createOpId) {
        this.createOpId = createOpId;
    }

    public String getCreateOpName() {
        return createOpName;
    }

    public void setCreateOpName(String createOpName) {
        this.createOpName = createOpName;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public List<Long> getExamIds() {
        return examIds;
    }

    public void setExamIds(List<Long> examIds) {
        this.examIds = examIds;
    }
}
