package com.xinyunlian.jinfu.point.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
public class UserScoreChangelogDto implements Serializable{

    private static final long serialVersionUID = -5131468716349530680L;

    private Long id;

    private String userId;

    private Long changedScore;

    private String changedScoreCode;

    private String source;

    private String activityCode;

    private String tranSeq;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getChangedScore() {
        return changedScore;
    }

    public void setChangedScore(Long changedScore) {
        this.changedScore = changedScore;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTranSeq() {
        return tranSeq;
    }

    public void setTranSeq(String tranSeq) {
        this.tranSeq = tranSeq;
    }

    public String getChangedScoreCode() {
        return changedScoreCode;
    }

    public void setChangedScoreCode(String changedScoreCode) {
        this.changedScoreCode = changedScoreCode;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }
}
