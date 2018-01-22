package com.xinyunlian.jinfu.point.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
@Entity
@Table(name = "user_scoreboard")
public class UserScoreboardPo extends BaseMaintainablePo{

    private static final long serialVersionUID = 2476707653041089888L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "TOTAL_SCORE")
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
