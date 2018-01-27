package com.xinyunlian.jinfu.loan.dto.user;

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
