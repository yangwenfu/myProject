package com.xinyunlian.jinfu.api.dto;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by DongFC on 2016-11-07.
 */
public class InsureAcquireReq implements Serializable {
    private static final long serialVersionUID = -5567917086190571977L;

    private String agent_id;
    @NotBlank(message = "store_name必填")
    private String store_name;
    @NotBlank(message = "licence_no必填")
    private String licence_no;
    @NotBlank(message = "contact_name必填")
    private String contact_name;
    private String contact_no;
    @NotBlank(message = "province必填")
    private String province;
    @NotBlank(message = "province_code必填")
    private String province_code;
    @NotBlank(message = "city必填")
    private String city;
    @NotBlank(message = "city_code必填")
    private String city_code;
    @NotBlank(message = "town必填")
    private String town;
    @NotBlank(message = "town_code必填")
    private String town_code;
    @NotBlank(message = "detail_address必填")
    private String detail_address;

    private String serial_no;
    private String sign_type;
    private String client_id;
    private String sign_msg;

    public String signSrc() {
        return "serial_no=" + StringUtils.trimToEmpty(serial_no) 
                +"&sign_type=" + StringUtils.trimToEmpty(sign_type)
                + "&client_id=" + StringUtils.trimToEmpty(client_id)
                +"&agent_id=" + StringUtils.trimToEmpty(agent_id)
                +"&store_name=" + StringUtils.trimToEmpty(store_name)
                + "&licence_no=" + StringUtils.trimToEmpty(licence_no)
                + "&contact_name=" + StringUtils.trimToEmpty(contact_name)
                + "&contact_no=" + StringUtils.trimToEmpty(contact_no)
                + "&province=" + StringUtils.trimToEmpty(province)
                + "&province_code=" + StringUtils.trimToEmpty(province_code)
                + "&city=" + StringUtils.trimToEmpty(city)
                + "&city_code=" + StringUtils.trimToEmpty(city_code)
                + "&town=" + StringUtils.trimToEmpty(town)
                + "&town_code=" + StringUtils.trimToEmpty(town_code)
                + "&detail_address=" + StringUtils.trimToEmpty(detail_address);
    }
    
    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public String getLicence_no() {
        return licence_no;
    }

    public void setLicence_no(String licence_no) {
        this.licence_no = licence_no;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getSign_msg() {
        return sign_msg;
    }

    public void setSign_msg(String sign_msg) {
        this.sign_msg = sign_msg;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTown_code() {
        return town_code;
    }

    public void setTown_code(String town_code) {
        this.town_code = town_code;
    }
}
