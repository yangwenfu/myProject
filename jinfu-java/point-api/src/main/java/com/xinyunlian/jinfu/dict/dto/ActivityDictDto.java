package com.xinyunlian.jinfu.dict.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
public class ActivityDictDto implements Serializable{
    private static final long serialVersionUID = 2769208814837538190L;

    private Long id;

    private String activityCode;

    private String activityName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
