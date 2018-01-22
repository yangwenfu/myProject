package com.xinyunlian.jinfu.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by menglei on 2017-01-10.
 */
public class MemberNoDto implements Serializable {

    private static final long serialVersionUID = -8720366343952539719L;

    private String memberName;

    private String memberMobile;

    private String memberCertno;

    private String acctNo;//银行卡号

    private String bankNo;//联行号

    private String bankName;//发卡行名称

    private String ip;

    private String gps;

    private String orgNo;

    private List<Biz> bizList;

    public static class Biz {

        private String bizcode;

        private String rate;

        public String getBizcode() {
            return bizcode;
        }

        public void setBizcode(String bizcode) {
            this.bizcode = bizcode;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemberCertno() {
        return memberCertno;
    }

    public void setMemberCertno(String memberCertno) {
        this.memberCertno = memberCertno;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public List<Biz> getBizList() {
        return bizList;
    }

    public void setBizList(List<Biz> bizList) {
        this.bizList = bizList;
    }
}
