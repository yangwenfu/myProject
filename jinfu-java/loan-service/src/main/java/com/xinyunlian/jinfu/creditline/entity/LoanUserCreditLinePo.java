package com.xinyunlian.jinfu.creditline.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.creditline.enums.ELoanUserCreditLineStatus;
import com.xinyunlian.jinfu.creditline.enums.converter.ELoanUserCreditLineStatusConverter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "FP_USER_CREDIT_LINE")
public class LoanUserCreditLinePo extends BaseMaintainablePo {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "CREDIT_LINE_TOTAL")
    private BigDecimal total;

    @Column(name = "CREDIT_LINE_AVALIABLE")
    private BigDecimal available;

    @Column(name = "CREDIT_LINE_DYNAMIC")
    private BigDecimal dynamic;

    @Column(name = "STATUS")
    @Convert(converter = ELoanUserCreditLineStatusConverter.class)
    private ELoanUserCreditLineStatus status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getDynamic() {
        return dynamic;
    }

    public void setDynamic(BigDecimal dynamic) {
        this.dynamic = dynamic;
    }

    public ELoanUserCreditLineStatus getStatus() {
        return status;
    }

    public void setStatus(ELoanUserCreditLineStatus status) {
        this.status = status;
    }
}
