package com.xinyunlian.jinfu.point.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
public class ScoreContinuityDto implements Serializable {

    private static final long serialVersionUID = 7537988092042830759L;

    private String userId;
    private Long start;
    private Long step;
    private Integer continuityDaysFixed;
    private String source;
    private String tranSeq;

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public Integer getContinuityDaysFixed() {
        return continuityDaysFixed;
    }

    public void setContinuityDaysFixed(Integer continuityDaysFixed) {
        this.continuityDaysFixed = continuityDaysFixed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
