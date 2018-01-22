package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.user.enums.EHouseProperty;
import com.xinyunlian.jinfu.user.enums.EIncomeSource;
import com.xinyunlian.jinfu.user.enums.EMarryStatus;
import com.xinyunlian.jinfu.user.enums.ESalaryMode;
import com.xinyunlian.jinfu.user.enums.converter.EHousePropertyConverter;
import com.xinyunlian.jinfu.user.enums.converter.EIncomeSourceConverter;
import com.xinyunlian.jinfu.user.enums.converter.EMarryStatusConverter;
import com.xinyunlian.jinfu.user.enums.converter.ESalaryModeConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户扩展Entity
 *
 * @author KimLL
 */
@Entity
@Table(name = "USER_EXT")
public class UserExtPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "STREET")
    private String street;

    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "CITY_ID")
    private Long cityId;

    @Column(name = "STREET_ID")
    private Long streetId;

    @Column(name = "address")
    private String address;

    @Column(name = "MARRY")
    @Convert(converter = EMarryStatusConverter.class)
    private EMarryStatus marryStatus;

    @Column(name = "HAS_HOUSE")
    private Boolean hasHouse;

    @Column(name = "HAS_OUT_HOUSE")
    private Boolean hasOutHouse;

    @Column(name = "LIVED")
    private Boolean lived;

    @Column(name = "TOBACCO_AUTH")
    private Boolean tobaccoAuth;

    @Column(name = "YITU_IS_PASS")
    private Boolean yituPass;

    @Column(name = "YITU_SIMILARITY")
    private Double yituSimilarity;

    @Column(name = "HOUSE_PROPERTY")
    @Convert(converter = EHousePropertyConverter.class)
    private EHouseProperty houseProperty;

    @Column(name="ID_EXPIRED_BEGIN")
    private Date idExpiredBegin;

    @Column(name="ID_EXPIRED_END")
    private Date idExpiredEnd;

    @Column(name="ID_AUTHORITY")
    private String idAuthority;

    @Column(name="BIRTH_DATE")
    private Date birthDate;

    @Column(name="SEX")
    private String sex;

    @Column(name="EDUCATION")
    private String education;

    @Column(name="NATION_PROVINCE_ID")
    private Long nationProvinceId;

    @Column(name="NATION_CITY_ID")
    private Long nationCityId;

    @Column(name="NATION_AREA_ID")
    private Long nationAreaId;

    @Column(name="NATION_PROVINCE")
    private String nationProvince;

    @Column(name="NATION_CITY")
    private String nationCity;

    @Column(name="NATION_AREA")
    private String nationArea;

    @Column(name="NATION")
    private String nation;

    @Column(name="NATION_ADDRESS")
    private String nationAddress;

    @Column(name="LIVE_ADDRESS_YEARS")
    private Integer liveAddressYears;

    @Column(name="ADDRESS_AS_NATION")
    private Boolean addressAsNation;

    @Column(name="LIVE_HERE_DATE")
    private Date liveHereDate;

    @Column(name="HAS_DL")
    private Boolean hasDl;

    @Column(name="DL_TYPE")
    private String dlType;

    @Column(name="FAMILY_NUM")
    private Integer familyNum;

    @Column(name="CHILDREN_NUM")
    private Integer childrenNum;

    @Column(name="SUPPORT_NUM")
    private Integer supportNum;

    @Column(name="SALARY_MODE")
    @Convert(converter = ESalaryModeConverter.class)
    private ESalaryMode salaryMode;

    @Column(name="COMPANY_ADD")
    private String companyAdd;

    @Column(name="COMPANY")
    private String company;

    @Column(name="COMPANY_TYPE")
    private String companyType;

    @Column(name="COMPANY_SIZE")
    private String companySize;

    @Column(name="POSITION")
    private String position;

    @Column(name="COMPANY_PHONE")
    private String companyPhone;

    @Column(name="EMPLOY_DATE")
    private Date employDate;

    @Column(name="INCOME_SOURCE")
    @Convert(converter = EIncomeSourceConverter.class)
    private EIncomeSource incomeSource;

    @Column(name="INCOME_MONTH")
    private BigDecimal incomeMonth;

    @Column(name="INCOME_YEAR")
    private BigDecimal incomeYear;

    @Column(name="INCOME_FAM_SOURCE")
    @Convert(converter = EIncomeSourceConverter.class)
    private EIncomeSource incomeFamSource;

    @Column(name="INCOME_FAM_MONTH")
    private BigDecimal incomeFamMonth;

    @Column(name="INCOME_FAM_YEAR")
    private BigDecimal incomeFamYear;

    @Column(name="LOAN_AMOUNT")
    private BigDecimal loanAmount;

    @Column(name="LOAN_NUM")
    private Integer loanNum;

    @Column(name="HOUSE_NUM")
    private Integer houseNum;

    @Column(name="CAR_NUM")
    private Integer carNum;

    @Column(name="CREDIT_CARD_NUM")
    private Integer creditCardNum;

    @Column(name="REPAY_MONTH")
    private BigDecimal repayMonth;

    @Column(name="SS_NAME")
    private String ssName;

    @Column(name="SS_PAY_BASE")
    private BigDecimal ssPayBase;

    @Column(name="SS_PAY_DATE")
    private Date ssPayDate;

    @Column(name="HF_NAME")
    private String hfName;

    @Column(name="HF_OPEN_DATE")
    private Date hfOpenDate;

    @Column(name="HF_PAY_DATE")
    private Date hfPayDate;

    @Column(name="HF_PAY_BASE")
    private BigDecimal hfPayBase;

    @Column(name="HF_QUERY_DATE")
    private Date hfQueryDate;

    @Column(name="HF_AMOUNT")
    private BigDecimal hfAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private UserInfoPo userInfoPo;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
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

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public Long getNationProvinceId() {
        return nationProvinceId;
    }

    public void setNationProvinceId(Long nationProvinceId) {
        this.nationProvinceId = nationProvinceId;
    }

    public Long getNationCityId() {
        return nationCityId;
    }

    public void setNationCityId(Long nationCityId) {
        this.nationCityId = nationCityId;
    }

    public Long getNationAreaId() {
        return nationAreaId;
    }

    public void setNationAreaId(Long nationAreaId) {
        this.nationAreaId = nationAreaId;
    }

    public Boolean getYituPass() {
        return yituPass;
    }

    public void setYituPass(Boolean yituPass) {
        this.yituPass = yituPass;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Double getYituSimilarity() {
        return yituSimilarity;
    }

    public void setYituSimilarity(Double yituSimilarity) {
        this.yituSimilarity = yituSimilarity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EMarryStatus getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(EMarryStatus marryStatus) {
        this.marryStatus = marryStatus;
    }



    public EHouseProperty getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(EHouseProperty houseProperty) {
        this.houseProperty = houseProperty;
    }

    public UserInfoPo getUserInfoPo() {
        return userInfoPo;
    }

    public void setUserInfoPo(UserInfoPo userInfoPo) {
        this.userInfoPo = userInfoPo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationAddress() {
        return nationAddress;
    }

    public void setNationAddress(String nationAddress) {
        this.nationAddress = nationAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Boolean getTobaccoAuth() {
        return tobaccoAuth;
    }

    public void setTobaccoAuth(Boolean tobaccoAuth) {
        this.tobaccoAuth = tobaccoAuth;
    }

    public Boolean getHasHouse() {
        return hasHouse;
    }

    public void setHasHouse(Boolean hasHouse) {
        this.hasHouse = hasHouse;
    }

    public Boolean getHasOutHouse() {
        return hasOutHouse;
    }

    public void setHasOutHouse(Boolean hasOutHouse) {
        this.hasOutHouse = hasOutHouse;
    }

    public Date getIdExpiredBegin() {
        return idExpiredBegin;
    }

    public void setIdExpiredBegin(Date idExpiredBegin) {
        this.idExpiredBegin = idExpiredBegin;
    }

    public Date getIdExpiredEnd() {
        return idExpiredEnd;
    }

    public void setIdExpiredEnd(Date idExpiredEnd) {
        this.idExpiredEnd = idExpiredEnd;
    }

    public String getIdAuthority() {
        return idAuthority;
    }

    public void setIdAuthority(String idAuthority) {
        this.idAuthority = idAuthority;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getNationProvince() {
        return nationProvince;
    }

    public void setNationProvince(String nationProvince) {
        this.nationProvince = nationProvince;
    }

    public String getNationCity() {
        return nationCity;
    }

    public void setNationCity(String nationCity) {
        this.nationCity = nationCity;
    }

    public String getNationArea() {
        return nationArea;
    }

    public void setNationArea(String nationArea) {
        this.nationArea = nationArea;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getLiveAddressYears() {
        return liveAddressYears;
    }

    public void setLiveAddressYears(Integer liveAddressYears) {
        this.liveAddressYears = liveAddressYears;
    }

    public Boolean getAddressAsNation() {
        return addressAsNation;
    }

    public void setAddressAsNation(Boolean addressAsNation) {
        this.addressAsNation = addressAsNation;
    }

    public Date getLiveHereDate() {
        return liveHereDate;
    }

    public void setLiveHereDate(Date liveHereDate) {
        this.liveHereDate = liveHereDate;
    }

    public Boolean getHasDl() {
        return hasDl;
    }

    public void setHasDl(Boolean hasDl) {
        this.hasDl = hasDl;
    }

    public String getDlType() {
        return dlType;
    }

    public void setDlType(String dlType) {
        this.dlType = dlType;
    }

    public Integer getFamilyNum() {
        return familyNum;
    }

    public void setFamilyNum(Integer familyNum) {
        this.familyNum = familyNum;
    }

    public Integer getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public Integer getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(Integer supportNum) {
        this.supportNum = supportNum;
    }

    public ESalaryMode getSalaryMode() {
        return salaryMode;
    }

    public void setSalaryMode(ESalaryMode salaryMode) {
        this.salaryMode = salaryMode;
    }

    public void setIncomeSource(EIncomeSource incomeSource) {
        this.incomeSource = incomeSource;
    }

    public void setIncomeFamSource(EIncomeSource incomeFamSource) {
        this.incomeFamSource = incomeFamSource;
    }

    public String getCompanyAdd() {
        return companyAdd;
    }

    public void setCompanyAdd(String companyAdd) {
        this.companyAdd = companyAdd;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public Date getEmployDate() {
        return employDate;
    }

    public void setEmployDate(Date employDate) {
        this.employDate = employDate;
    }

    public BigDecimal getIncomeMonth() {
        return incomeMonth;
    }

    public void setIncomeMonth(BigDecimal incomeMonth) {
        this.incomeMonth = incomeMonth;
    }

    public BigDecimal getIncomeYear() {
        return incomeYear;
    }

    public void setIncomeYear(BigDecimal incomeYear) {
        this.incomeYear = incomeYear;
    }

    public BigDecimal getIncomeFamMonth() {
        return incomeFamMonth;
    }

    public void setIncomeFamMonth(BigDecimal incomeFamMonth) {
        this.incomeFamMonth = incomeFamMonth;
    }

    public BigDecimal getIncomeFamYear() {
        return incomeFamYear;
    }

    public void setIncomeFamYear(BigDecimal incomeFamYear) {
        this.incomeFamYear = incomeFamYear;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanNum() {
        return loanNum;
    }

    public void setLoanNum(Integer loanNum) {
        this.loanNum = loanNum;
    }

    public Integer getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Integer houseNum) {
        this.houseNum = houseNum;
    }

    public Integer getCarNum() {
        return carNum;
    }

    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }

    public Integer getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(Integer creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public BigDecimal getRepayMonth() {
        return repayMonth;
    }

    public void setRepayMonth(BigDecimal repayMonth) {
        this.repayMonth = repayMonth;
    }

    public String getSsName() {
        return ssName;
    }

    public void setSsName(String ssName) {
        this.ssName = ssName;
    }

    public BigDecimal getSsPayBase() {
        return ssPayBase;
    }

    public void setSsPayBase(BigDecimal ssPayBase) {
        this.ssPayBase = ssPayBase;
    }

    public Date getSsPayDate() {
        return ssPayDate;
    }

    public void setSsPayDate(Date ssPayDate) {
        this.ssPayDate = ssPayDate;
    }

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public Date getHfOpenDate() {
        return hfOpenDate;
    }

    public void setHfOpenDate(Date hfOpenDate) {
        this.hfOpenDate = hfOpenDate;
    }

    public Date getHfPayDate() {
        return hfPayDate;
    }

    public void setHfPayDate(Date hfPayDate) {
        this.hfPayDate = hfPayDate;
    }

    public BigDecimal getHfPayBase() {
        return hfPayBase;
    }

    public void setHfPayBase(BigDecimal hfPayBase) {
        this.hfPayBase = hfPayBase;
    }

    public Date getHfQueryDate() {
        return hfQueryDate;
    }

    public void setHfQueryDate(Date hfQueryDate) {
        this.hfQueryDate = hfQueryDate;
    }

    public BigDecimal getHfAmount() {
        return hfAmount;
    }

    public void setHfAmount(BigDecimal hfAmount) {
        this.hfAmount = hfAmount;
    }

    public EIncomeSource getIncomeSource() {
        return incomeSource;
    }

    public EIncomeSource getIncomeFamSource() {
        return incomeFamSource;
    }

    public Boolean getLived() {
        return lived;
    }

    public void setLived(Boolean lived) {
        this.lived = lived;
    }
}


