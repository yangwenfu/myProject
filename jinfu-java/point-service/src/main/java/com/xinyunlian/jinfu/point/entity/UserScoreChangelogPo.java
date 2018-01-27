package com.xinyunlian.jinfu.point.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
@Entity
@Table(name = "user_score_changelog")
public class UserScoreChangelogPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 6161557419316062919L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "CHANGED_SCORE")
    private Long changedScore;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "TRAN_SEQ")
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
}
