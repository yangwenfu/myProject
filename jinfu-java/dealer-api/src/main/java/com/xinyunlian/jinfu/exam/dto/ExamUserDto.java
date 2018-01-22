package com.xinyunlian.jinfu.exam.dto;

import com.xinyunlian.jinfu.exam.enums.EExamStatus;
import com.xinyunlian.jinfu.exam.enums.EExamUserStatus;

import java.io.Serializable;

/**
 * Created by menglei on 2017年05月02日.
 */
public class ExamUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String dealerId;

    private String userId;

    private Long examId;

    private Integer score;

    private EExamUserStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public EExamUserStatus getStatus() {
        return status;
    }

    public void setStatus(EExamUserStatus status) {
        this.status = status;
    }
}
