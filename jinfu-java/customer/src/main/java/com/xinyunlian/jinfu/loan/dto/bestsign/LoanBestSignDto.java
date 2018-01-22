package com.xinyunlian.jinfu.loan.dto.bestsign;

import java.io.Serializable;

/**
 * @author willwang
 */
public class LoanBestSignDto implements Serializable{

    private String applId;

    private String signUrl;

    private String returnUrl;

    private String contractName;

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
