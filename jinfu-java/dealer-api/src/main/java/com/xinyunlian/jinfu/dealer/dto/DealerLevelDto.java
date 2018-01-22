package com.xinyunlian.jinfu.dealer.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月05日.
 */
public class DealerLevelDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long levelId;

    private String levelName;

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
