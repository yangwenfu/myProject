package com.xinyunlian.jinfu.dict.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
@Entity
@Table(name = "score_dict")
public class ScoreDictPo extends BaseMaintainablePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SCORE_CODE")
    private String scoreCode;

    @Column(name = "SCORE_VALUE")
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
