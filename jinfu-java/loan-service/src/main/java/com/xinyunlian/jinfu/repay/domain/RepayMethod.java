package com.xinyunlian.jinfu.repay.domain;

import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.product.enums.EFineType;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by JL on 2016/11/21.
 */
public abstract class RepayMethod {

    /**
     * 贷款信息
     */
    private LoanDtlDto loanDtlDto;

    /**
     * 营销信息
     */
    private PromoDto promoDto;

    /**
     * 当前时间
     */
    private Date now;

    /**
     * 罚息类型
     */
    private EFineType fineType;

    /**
     * 罚息值
     */
    private BigDecimal fineValue;

    public RepayMethod(LoanDtlDto loanDtlDto, PromoDto promoDto, Date now) {
        this.promoDto = promoDto;
        this.loanDtlDto = loanDtlDto;
        this.now = now;
    }

    public BigDecimal getFineValue() {
        return fineValue;
    }

    public void setFineValue(BigDecimal fineValue) {
        this.fineValue = fineValue;
    }

    public Date getNow() {
        return now;
    }

    public EFineType getFineType() {
        return fineType;
    }

    public void setFineType(EFineType fineType) {
        this.fineType = fineType;
    }

    public void setRt(BigDecimal rt) {
        this.loanDtlDto.setLoanRt(rt);
    }

    public BigDecimal getLoanAmt() {
        return loanDtlDto.getLoanAmt();
    }

    public BigDecimal getSurplusCapital(){
        return loanDtlDto.getLoanAmt().subtract(loanDtlDto.getRepayedAmt());
    }

    public BigDecimal getRt() {
        return loanDtlDto.getLoanRt();
    }

    public EIntrRateType getIntrRateType() {
        return loanDtlDto.getIntrRateType();
    }

    public void setIntrRateType(EIntrRateType intrRateType) {
        loanDtlDto.setIntrRateType(intrRateType);
    }

    public ETermType getTermType() {
        return loanDtlDto.getTermType();
    }

    public void setTermType(ETermType termType) {
        loanDtlDto.setTermType(termType);
    }

    public Integer getTermLen() {
        return loanDtlDto.getTermLen();
    }

    public void setTermLen(Integer termLen) {
        loanDtlDto.setTermLen(termLen);
    }

    public PromoDto getPromoDto() {
        return promoDto;
    }

    public LoanDtlDto getLoanDtlDto() {
        return loanDtlDto;
    }

    public abstract BigDecimal getCapital();

    public abstract BigDecimal getInterest();

    public abstract List<ScheduleDto> getRepaySchedule();

    public abstract BigDecimal getFine();
}
