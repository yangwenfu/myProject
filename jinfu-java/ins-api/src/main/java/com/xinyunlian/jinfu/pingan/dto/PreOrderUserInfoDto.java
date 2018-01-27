package com.xinyunlian.jinfu.pingan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class PreOrderUserInfoDto implements Serializable {
    private static final long serialVersionUID = -495578032757879357L;

    private Long id;

    private String preInsuranceOrderNo;

    private String tobaccoCertificateNo;

    private Long storeId;

    private String storeName;

    private String userName;

    private String linkmanName;

    private String mobile;

    private String provinceGbCode;

    private String cityGbCode;

    private String countyGbCode;

    private String detailAddress;

    private Long provinceId;

    private Long cityId;

    private Long countyId;

    private String provinceName;

    private String cityName;

    private String countyName;

    private String email;

    //保险起期
    private Date insuranceBeginDate;

    //保额
    private BigDecimal totalInsuredAmount;

    //保费
    private BigDecimal totalActualPreium;

    //身份证号
    private String idCardNo;

    //平安的产品编码
    private String productCode;

    //平安店铺保险种编码
    private String planCode;

    private String applyPolicyNo;

    private String policyNo;

    private Boolean policyFileDownloaded;

    private String insuredGradeCode;

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvinceGbCode() {
        return provinceGbCode;
    }

    public void setProvinceGbCode(String provinceGbCode) {
        this.provinceGbCode = provinceGbCode;
    }

    public String getCityGbCode() {
        return cityGbCode;
    }

    public void setCityGbCode(String cityGbCode) {
        this.cityGbCode = cityGbCode;
    }

    public String getCountyGbCode() {
        return countyGbCode;
    }

    public void setCountyGbCode(String countyGbCode) {
        this.countyGbCode = countyGbCode;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getInsuranceBeginDate() {
        return insuranceBeginDate;
    }

    public void setInsuranceBeginDate(Date insuranceBeginDate) {
        this.insuranceBeginDate = insuranceBeginDate;
    }

    public BigDecimal getTotalInsuredAmount() {
        return totalInsuredAmount;
    }

    public void setTotalInsuredAmount(BigDecimal totalInsuredAmount) {
        this.totalInsuredAmount = totalInsuredAmount;
    }

    public BigDecimal getTotalActualPreium() {
        return totalActualPreium;
    }

    public void setTotalActualPreium(BigDecimal totalActualPreium) {
        this.totalActualPreium = totalActualPreium;
    }

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreInsuranceOrderNo() {
        return preInsuranceOrderNo;
    }

    public void setPreInsuranceOrderNo(String preInsuranceOrderNo) {
        this.preInsuranceOrderNo = preInsuranceOrderNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getApplyPolicyNo() {
        return applyPolicyNo;
    }

    public void setApplyPolicyNo(String applyPolicyNo) {
        this.applyPolicyNo = applyPolicyNo;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Boolean getPolicyFileDownloaded() {
        return policyFileDownloaded;
    }

    public void setPolicyFileDownloaded(Boolean policyFileDownloaded) {
        this.policyFileDownloaded = policyFileDownloaded;
    }

    public String getInsuredGradeCode() {
        return insuredGradeCode;
    }

    public void setInsuredGradeCode(String insuredGradeCode) {
        this.insuredGradeCode = insuredGradeCode;
    }
}
