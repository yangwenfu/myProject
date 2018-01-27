package com.xinyunlian.jinfu.contract.dto;

import java.io.Serializable;
import java.util.List;
/**
 * Created by dongfangchao on 2017/2/10/0010.
 */
public class BsAutoSignFoppReq implements Serializable{
    private static final long serialVersionUID = -6061229313093357801L;

    private String email;

    private String signid;

    private List<ContractBestSignCfgDto> bestSignCfgList;

    private String openflag;

    private String sealname;

    private String noticeUrls;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignid() {
        return signid;
    }

    public void setSignid(String signid) {
        this.signid = signid;
    }

    public List<ContractBestSignCfgDto> getBestSignCfgList() {
        return bestSignCfgList;
    }

    public void setBestSignCfgList(List<ContractBestSignCfgDto> bestSignCfgList) {
        this.bestSignCfgList = bestSignCfgList;
    }

    public String getOpenflag() {
        return openflag;
    }

    public void setOpenflag(String openflag) {
        this.openflag = openflag;
    }

    public String getSealname() {
        return sealname;
    }

    public void setSealname(String sealname) {
        this.sealname = sealname;
    }

    public String getNoticeUrls() {
        return noticeUrls;
    }

    public void setNoticeUrls(String noticeUrls) {
        this.noticeUrls = noticeUrls;
    }
}