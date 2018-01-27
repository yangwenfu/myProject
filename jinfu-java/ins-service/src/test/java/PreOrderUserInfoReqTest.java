import com.xinyunlian.jinfu.insurance.enums.EPerInsDealSource;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class PreOrderUserInfoReqTest implements Serializable {
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

    private Date insuranceBeginDate;

    private BigDecimal totalInsuredAmount;

    private BigDecimal totalActualPreium;

    private EPerInsDealType dealType;

    private EPerInsDealSource dealSource;

    private String productCode;

    private List<String> planCodeList;

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
}
