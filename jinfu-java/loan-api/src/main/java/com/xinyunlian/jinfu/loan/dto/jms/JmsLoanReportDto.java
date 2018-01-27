package com.xinyunlian.jinfu.loan.dto.jms;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by King on 2017/9/6.
 */
public class JmsLoanReportDto  implements Serializable {

    private String reportNo;

    private Date genTime;

    private String custName;

    private String mobileNum;

    private Double score;

    private LoanResult result;

    //贷款额度
    private Double loanQuota;

    private String refuseCode;

    private String applId;

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public LoanResult getResult() {
        return result;
    }

    public void setResult(LoanResult result) {
        this.result = result;
    }

    public Double getLoanQuota() {
        return loanQuota;
    }

    public void setLoanQuota(Double loanQuota) {
        this.loanQuota = loanQuota;
    }

    public String getRefuseCode() {
        return refuseCode;
    }

    public void setRefuseCode(String refuseCode) {
        this.refuseCode = refuseCode;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }
}
