package com.xinyunlian.jinfu.oauth.dto;

import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import org.apache.commons.lang.StringUtils;

/**
 * Created by JL on 2016/10/27.
 */
public class B2bUserInfoDto extends OauthUserInfo {

    private String area_full_name, phone, licence_code, username, address, name_path, area_name, name, expires_in,
            area_gb_code, mobile, retail, shop_name;

    public String getArea_full_name() {
        return area_full_name;
    }

    public void setArea_full_name(String area_full_name) {
        this.area_full_name = area_full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicence_code() {
        return licence_code;
    }

    public void setLicence_code(String licence_code) {
        this.licence_code = licence_code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName_path() {
        return name_path;
    }

    public void setName_path(String name_path) {
        this.name_path = name_path;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getArea_gb_code() {
        return area_gb_code;
    }

    public void setArea_gb_code(String area_gb_code) {
        this.area_gb_code = area_gb_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRetail() {
        return retail;
    }

    public void setRetail(String retail) {
        this.retail = retail;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }


    @Override
    public StoreInfDto getStoreInfo() {
        StoreInfDto storeInfDto = new StoreInfDto();
        storeInfDto.setTobaccoCertificateNo(licence_code);
        storeInfDto.setStoreName(shop_name);
        if (StringUtils.isNotEmpty(name_path)) {
            String[] region = name_path.split(",");
            address = address.replace(region[0], "");
            address = address.replace(region[1], "");
            address = address.replace(region[2], "");
            storeInfDto.setProvince(region[0]);
            storeInfDto.setCity(region[1]);
            storeInfDto.setArea(region[2]);
        }
        storeInfDto.setAddress(address);
        return storeInfDto;
    }
}

