package com.xinyunlian.jinfu.api.dto.xyl;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by menglei on 2017年05月31日.
 */
public class RegisterOpenApiDto implements Serializable {

    private static final long serialVersionUID = -1L;

    @NotBlank(message = "手机号不能为空")
    private String mobile;//手机

    @NotBlank(message = "验证码不能为空")
    private String verifyCode;//验证码

    @NotBlank(message = "outId不能为空")
    private String outId;//外部关联id

    @NotBlank(message = "appId不能为空")
    private String appId;//

    @NotBlank(message = "签名不能为空")
    private String sign;//

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }
}
