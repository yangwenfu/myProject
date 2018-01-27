package com.xinyunlian.jinfu.dict.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
public class ScoreDictDto implements Serializable{
    private static final long serialVersionUID = 2769208814837538190L;

    private Long id;

    private String scoreCode;

    private Long scoreValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScoreCode() {
        return scoreCode;
    }

    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }

    public Long getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Long scoreValue) {
        this.scoreValue = scoreValue;
    }
}
