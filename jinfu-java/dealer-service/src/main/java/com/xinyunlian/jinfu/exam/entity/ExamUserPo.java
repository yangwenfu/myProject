package com.xinyunlian.jinfu.exam.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.exam.enums.EExamUserStatus;
import com.xinyunlian.jinfu.exam.enums.converter.EExamUserStatusConverter;

import javax.persistence.*;

/**
 * Created by menglei on 2017年05月02日.
 */
@Entity
@Table(name = "exam_user")
public class ExamUserPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "EXAM_ID")
    private Long examId;

    @Column(name = "SCORE")
    private Integer score;

    @Column(name = "STATUS")
    @Convert(converter = EExamUserStatusConverter.class)
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
