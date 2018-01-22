package com.xinyunlian.jinfu.point.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
public class UserScoreboardDto implements Serializable {
    private static final long serialVersionUID = 5829948307783747635L;

    private Long id;

    private String userId;

    private Long totalScore;

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

    public Long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }
}
