package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * @author Willwang
 */
public class VerifyDto implements Serializable {

    private String verificationPackage;

    public String getVerificationPackage() {
        return verificationPackage;
    }

    public void setVerificationPackage(String verificationPackage) {
        this.verificationPackage = verificationPackage;
    }
}
