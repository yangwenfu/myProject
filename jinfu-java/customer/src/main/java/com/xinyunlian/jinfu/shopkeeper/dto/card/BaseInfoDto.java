package com.xinyunlian.jinfu.shopkeeper.dto.card;

import com.xinyunlian.jinfu.user.enums.EHouseProperty;
import com.xinyunlian.jinfu.user.enums.EMarryStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by King on 2017/2/14.
 */
public class BaseInfoDto implements Serializable {
    private static final long serialVersionUID = -1038206671607876752L;
    private Long areaId;

    private Long provinceId;

    private Long cityId;

    private Long streetId;

    @NotEmpty
    private String province;

    @NotEmpty
    private String city;

    @NotEmpty
    private String area;

    @NotEmpty
    private String street;

    //住址
    @NotEmpty
    private String address;

    //婚姻状态
    @NotNull
    private EMarryStatus marryStatus;

    //住宅性质
    @NotNull
    private EHouseProperty houseProperty;

    /**
     * 结婚证照片
     */
    private String marryCertificatePic;

    /**
     * 结婚证照片Id
     */
    private Long marryCertificatePicId;

    /**
     * 户口本本人页照片
     */
    private String residenceBookletPic;

    /**
     * 户口本本人页照片Id
     */
    private Long residenceBookletPicId;

    /**
     * 户口本户主页照片
     */
    private String householderBookletPic;


    /**
     * 户口本户主页照片Id
     */
    private Long householderBookletPicId;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getMarryCertificatePic() {
        return marryCertificatePic;
    }

    public void setMarryCertificatePic(String marryCertificatePic) {
        this.marryCertificatePic = marryCertificatePic;
    }

    public Long getMarryCertificatePicId() {
        return marryCertificatePicId;
    }

    public void setMarryCertificatePicId(Long marryCertificatePicId) {
        this.marryCertificatePicId = marryCertificatePicId;
    }

    public String getResidenceBookletPic() {
        return residenceBookletPic;
    }

    public void setResidenceBookletPic(String residenceBookletPic) {
        this.residenceBookletPic = residenceBookletPic;
    }

    public Long getResidenceBookletPicId() {
        return residenceBookletPicId;
    }

    public void setResidenceBookletPicId(Long residenceBookletPicId) {
        this.residenceBookletPicId = residenceBookletPicId;
    }

    public String getHouseholderBookletPic() {
        return householderBookletPic;
    }

    public void setHouseholderBookletPic(String householderBookletPic) {
        this.householderBookletPic = householderBookletPic;
    }

    public Long getHouseholderBookletPicId() {
        return householderBookletPicId;
    }

    public void setHouseholderBookletPicId(Long householderBookletPicId) {
        this.householderBookletPicId = householderBookletPicId;
    }
}
