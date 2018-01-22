package com.xinyunlian.jinfu.pingan.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
@Entity
@Table(name = "pre_order_user_info")
public class PreOrderUserInfoPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 4616188667544257428L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PER_INSURANCE_ORDER_NO")
    private String preInsuranceOrderNo;

    @Column(name = "TOBACCO_CERTIFICATE_NO")
    private String tobaccoCertificateNo;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "LINKMAN_NAME")
    private String linkmanName;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "PROVINCE_GB_CODE")
    private String provinceGbCode;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "PROVINCE_NAME")
    private String provinceName;

    @Column(name = "CITY_GB_CODE")
    private String cityGbCode;

    @Column(name = "CITY_ID")
    private Long cityId;

    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "COUNTY_GB_CODE")
    private String countyGbCode;

    @Column(name = "COUNTY_ID")
    private Long countyId;

    @Column(name = "COUNTY_NAME")
    private String countyName;

    @Column(name = "DETAIL_ADDRESS")
    private String detailAddress;

    @Column(name = "EMAIL")
    private String email;

    //保险起期
    @Column(name = "INSURANCE_BEGIN_DATE")
    private Date insuranceBeginDate;

    //保额
    @Column(name = "TOTAL_INSURED_AMOUNT")
    private BigDecimal totalInsuredAmount;

    //保费
    @Column(name = "TOTAL_ACTUAL_PREIUM")
    private BigDecimal totalActualPreium;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PLAN_CODE")
    private String planCode;

    @Column(name = "APPLY_POLICY_NO")
    private String applyPolicyNo;

    @Column(name = "POLICY_NO")
    private String policyNo;

    @Column(name = "POLICY_FILE_DOWNLOADED")
    private Boolean policyFileDownloaded;

    @Column(name = "INSURED_GRADE_CODE")
    private String insuredGradeCode;

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

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
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

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityGbCode() {
        return cityGbCode;
    }

    public void setCityGbCode(String cityGbCode) {
        this.cityGbCode = cityGbCode;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyGbCode() {
        return countyGbCode;
    }

    public void setCountyGbCode(String countyGbCode) {
        this.countyGbCode = countyGbCode;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
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
