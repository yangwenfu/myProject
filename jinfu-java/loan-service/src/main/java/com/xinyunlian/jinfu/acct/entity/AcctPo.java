package com.xinyunlian.jinfu.acct.entity;

import com.xinyunlian.jinfu.acct.enums.EAcctStatus;
import com.xinyunlian.jinfu.acct.enums.converter.EAcctStatusEnumConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "AC_ACCT")
@EntityListeners(IdInjectionEntityListener.class)
public class AcctPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PROD_ID")
    private String productId;

    @Column(name = "STATUS")
    @Convert(converter = EAcctStatusEnumConverter.class)
    private EAcctStatus status;

    @Column(name = "CREDIT_LINE")
    private BigDecimal creditLine;

    @Column(name = "CREDIT_LINE_USED")
    private BigDecimal creditLineUsed;

    @Column(name = "CREDIT_LINE_RSRV")
    private BigDecimal creditLineRsrv;

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public EAcctStatus getStatus() {
        return status;
    }

    public void setStatus(EAcctStatus status) {
        this.status = status;
    }

    public BigDecimal getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(BigDecimal creditLine) {
        this.creditLine = creditLine;
    }

    public BigDecimal getCreditLineUsed() {
        return creditLineUsed;
    }

    public void setCreditLineUsed(BigDecimal creditLineUsed) {
        this.creditLineUsed = creditLineUsed;
    }

    public BigDecimal getCreditLineRsrv() {
        return creditLineRsrv;
    }

    public void setCreditLineRsrv(BigDecimal creditLineRsrv) {
        this.creditLineRsrv = creditLineRsrv;
    }
}
