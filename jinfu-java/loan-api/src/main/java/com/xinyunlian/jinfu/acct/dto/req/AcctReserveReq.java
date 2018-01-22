package com.xinyunlian.jinfu.acct.dto.req;

import java.math.BigDecimal;

/**
 * @author willwang
 */
public class AcctReserveReq {

    private String acctNo;

    private BigDecimal creditLineRsrv;

    private String rsrvId;

    private String trxMemo;

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public BigDecimal getCreditLineRsrv() {
        return creditLineRsrv;
    }

    public void setCreditLineRsrv(BigDecimal creditLineRsrv) {
        this.creditLineRsrv = creditLineRsrv;
    }

    public String getTrxMemo() {
        return trxMemo;
    }

    public void setTrxMemo(String trxMemo) {
        this.trxMemo = trxMemo;
    }

    public AcctReserveReq() {
    }

    public AcctReserveReq(String acctNo, BigDecimal creditLineRsrv, String rsrvId) {
        this.acctNo = acctNo;
        this.creditLineRsrv = creditLineRsrv;
        this.rsrvId = rsrvId;
    }

    public String getRsrvId() {
        return rsrvId;
    }

    public void setRsrvId(String rsrvId) {
        this.rsrvId = rsrvId;
    }
}
