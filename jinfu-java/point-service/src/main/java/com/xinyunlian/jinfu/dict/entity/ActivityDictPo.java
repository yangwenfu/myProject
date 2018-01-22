package com.xinyunlian.jinfu.dict.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
@Entity
@Table(name = "activity_dict")
public class ActivityDictPo extends BaseMaintainablePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACTIVITY_CODE")
    private String activityCode;

    @Column(name = "ACTIVITY_NAME")
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
