package com.xinyunlian.jinfu.exam.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.exam.enums.EExamStatus;
import com.xinyunlian.jinfu.exam.enums.EExamType;
import com.xinyunlian.jinfu.exam.enums.converter.EExamStatusConverter;
import com.xinyunlian.jinfu.exam.enums.converter.EExamTypeConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by menglei on 2017年05月02日.
 */
@Entity
@Table(name = "exam")
public class ExamPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAM_ID")
    private Long examId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    @Convert(converter = EExamTypeConverter.class)
    private EExamType type;

    @Column(name = "URL")
    private String url;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "PASS_LINE")
    private Integer passLine;

    @Column(name = "STATUS")
    @Convert(converter = EExamStatusConverter.class)
    private EExamStatus status;

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
}
