package com.xinyunlian.jinfu.ins.dto;

import com.xinyunlian.jinfu.insurance.enums.EPerInsDealSource;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class PreOrderUserInfoReq implements Serializable {
    private static final long serialVersionUID = -495578032757879357L;

    private Long id;

    private String preInsuranceOrderNo;

    @NotBlank(message = "tobaccoCertificateNo can not be blank")
    private String tobaccoCertificateNo;

    @NotNull(message = "storeId can not be null")
    private Long storeId;

    @NotBlank(message = "storeName can not be blank")
    private String storeName;

    @NotBlank(message = "userName can not be blank")
    private String userName;

    @NotBlank(message = "linkmanName can not be blank")
    private String linkmanName;

    @NotBlank(message = "mobile can not be blank")
    private String mobile;

    @NotBlank(message = "provinceGbCode can not be blank")
    private String provinceGbCode;

    @NotBlank(message = "cityGbCode can not be blank")
    private String cityGbCode;

    @NotBlank(message = "countyGbCode can not be blank")
    private String countyGbCode;

    @NotBlank(message = "detailAddress can not be blank")
    private String detailAddress;

    @NotNull(message = "provinceId can not be null")
    private Long provinceId;

    @NotNull(message = "cityId can not be null")
    private Long cityId;

    @NotNull(message = "countyId can not be null")
    private Long countyId;

    @NotBlank(message = "provinceName can not be blank")
    private String provinceName;

    @NotBlank(message = "cityName can not be blank")
    private String cityName;

    @NotBlank(message = "countyName can not be blank")
    private String countyName;

    @NotBlank(message = "email can not be blank")
    private String email;

    //保险起期
    @NotNull(message = "insuranceBeginDate can not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date insuranceBeginDate;

    //保额
    @NotNull(message = "totalInsuredAmount can not be null")
    private BigDecimal totalInsuredAmount;

    //保费
    @NotNull(message = "totalActualPreium can not be null")
    private BigDecimal totalActualPreium;

    @NotNull(message = "dealType can not be null")
    private EPerInsDealType dealType;

    @NotNull(message = "dealSource can not be null")
    private EPerInsDealSource dealSource;

    @NotBlank(message = "productCode can not be blank")
    private String productCode;

    @NotNull(message = "planCodeList can not be blank")
    private List<String> planCodeList;

    @NotBlank(message = "insuredGradeCode can not be blank")
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

    public List<String> getPlanCodeList() {
        return planCodeList;
    }

    public void setPlanCodeList(List<String> planCodeList) {
        this.planCodeList = planCodeList;
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public EPerInsDealType getDealType() {
        return dealType;
    }

    public void setDealType(EPerInsDealType dealType) {
        this.dealType = dealType;
    }

    public EPerInsDealSource getDealSource() {
        return dealSource;
    }

    public void setDealSource(EPerInsDealSource dealSource) {
        this.dealSource = dealSource;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getInsuredGradeCode() {
        return insuredGradeCode;
    }

    public void setInsuredGradeCode(String insuredGradeCode) {
        this.insuredGradeCode = insuredGradeCode;
    }
}
