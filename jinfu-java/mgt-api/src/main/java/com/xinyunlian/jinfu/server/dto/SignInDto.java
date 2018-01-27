package com.xinyunlian.jinfu.server.dto;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-09-05.
 */
public class SignInDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String loginId;

    private String password;

    private String verificationCode;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
