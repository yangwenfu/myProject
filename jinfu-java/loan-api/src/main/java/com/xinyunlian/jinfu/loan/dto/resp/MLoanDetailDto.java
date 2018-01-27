package com.xinyunlian.jinfu.loan.dto.resp;

import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;

import java.io.Serializable;

/**
 * Created by Willwang on 2016/12/9.
 */
public class MLoanDetailDto implements Serializable {

    private String mobile;

    private String userName;

    private String idCardNo;

    private String applDate;

    private ELoanCustomerStatus status;

    private LoanDtlDto loan;

    private LoanProductDetailDto product;

    private Long promoId;

    private String promoName;

    private String promoDesc;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LoanDtlDto getLoan() {
        return loan;
    }

    public void setLoan(LoanDtlDto loan) {
        this.loan = loan;
    }

    public LoanProductDetailDto getProduct() {
        return product;
    }

    public void setProduct(LoanProductDetailDto product) {
        this.product = product;
    }

    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getPromoDesc() {
        return promoDesc;
    }

    public void setPromoDesc(String promoDesc) {
        this.promoDesc = promoDesc;
    }

    public ELoanCustomerStatus getStatus() {
        return status;
    }

    public void setStatus(ELoanCustomerStatus status) {
        this.status = status;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getApplDate() {
        return applDate;
    }

    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }
}
