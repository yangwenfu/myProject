package com.xinyunlian.jinfu.shopkeeper.dto.my;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
public class SignInContinuityDto implements Serializable {
    private static final long serialVersionUID = -6983444080770652273L;

    @NotBlank(message = "qrcodeUrl can not be blank")
    private String qrcodeUrl;

    @NotBlank(message = "activityCode can not be blank")
    private String activityCode;

    @NotBlank(message = "initScoreCode can not be blank")
    private String initScoreCode;

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

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
