package com.xinyunlian.jinfu.shopkeeper.dto.my;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/7/0007.
 */
public class AlreadySignInConDto implements Serializable {
    private static final long serialVersionUID = -1369661172389273778L;

    @NotBlank(message = "activityCode can not be blank")
    private String activityCode;

    @NotBlank(message = "initScoreCode can not be blank")
    private String initScoreCode;

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getInitScoreCode() {
        return initScoreCode;
    }

    public void setInitScoreCode(String initScoreCode) {
        this.initScoreCode = initScoreCode;
    }
}
