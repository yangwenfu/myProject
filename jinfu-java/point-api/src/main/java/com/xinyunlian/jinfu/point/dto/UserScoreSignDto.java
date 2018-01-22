package com.xinyunlian.jinfu.point.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/4/0004.
 */
public class UserScoreSignDto implements Serializable {
    private static final long serialVersionUID = 1117093062765182920L;

    //明天可以获得的积分
    private Long tomorrowScore;

    //总的连续签到天数
    private Long continuityDays;

    //签到周期的第几天
    private Long continuityDaysOfCycle;

    public Long getTomorrowScore() {
        return tomorrowScore;
    }

    public void setTomorrowScore(Long tomorrowScore) {
        this.tomorrowScore = tomorrowScore;
    }

    public Long getContinuityDays() {
        return continuityDays;
    }

    public void setContinuityDays(Long continuityDays) {
        this.continuityDays = continuityDays;
    }

    public Long getContinuityDaysOfCycle() {
        return continuityDaysOfCycle;
    }

    public void setContinuityDaysOfCycle(Long continuityDaysOfCycle) {
        this.continuityDaysOfCycle = continuityDaysOfCycle;
    }
}
