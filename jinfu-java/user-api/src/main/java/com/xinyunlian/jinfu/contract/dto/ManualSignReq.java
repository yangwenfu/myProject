package com.xinyunlian.jinfu.contract.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongfangchao on 2017/2/8/0008.
 */
public class ManualSignReq implements Serializable {
    private static final long serialVersionUID = -2143306166833392711L;

    private Boolean pc;

    private String returnUrl;

    private String signId;

    private String email;

    private List<ContractBestSignCfgDto> bestSignCfgList;

    public Boolean getPc() {
        return pc;
    }

    public void setPc(Boolean pc) {
        this.pc = pc;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ContractBestSignCfgDto> getBestSignCfgList() {
        return bestSignCfgList;
    }

    public void setBestSignCfgList(List<ContractBestSignCfgDto> bestSignCfgList) {
        this.bestSignCfgList = bestSignCfgList;
    }
}
