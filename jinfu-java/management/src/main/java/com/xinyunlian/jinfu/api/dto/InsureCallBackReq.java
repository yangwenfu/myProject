package com.xinyunlian.jinfu.api.dto;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by DongFC on 2016-09-26.
 */
public class InsureCallBackReq {
    @NotBlank
    @Length(max = 32)
    private String serialno;
    @NotBlank
    private String sign_type;
    @NotBlank
    @Length(max = 32)
    private String client_id;
    @NotBlank
    @Length(max = 12)
    private String insurance_code;
    @NotBlank
    @Length(max = 20)
    private String policy_no;
    @NotBlank
    @Length(max = 32)
    private String store_name;
    @NotBlank
    @Length(max = 255)
    private String store_addr;
    @NotBlank
    @Length(max = 20)
    @Pattern(regexp = "[1-9]\\d*")
    private String total_premium;
    @NotBlank
    @Length(max = 20)
    @Pattern(regexp = "[1-9]\\d*")
    private String premium;
    @NotBlank
    @Length(max = 80)
    private String policy_holder;
    @NotBlank
    @Length(max = 80)
    private String insured_person;
    @NotBlank
    @Length(max = 12)
    private String phone;
    @NotBlank
    @Length(max = 50)
    private String licence_no;
    @NotBlank
    private String effective_date;
    @NotBlank
    private String expiry_date;
    @NotBlank
    @Length(max = 1024)
    private String signature;

    public String signSrc() {
        return "serialno=" + StringUtils.trimToEmpty(serialno)
                + "&sign_type=" + StringUtils.trimToEmpty(sign_type)
                + "&client_id=" + StringUtils.trimToEmpty(client_id)
                + "&insurance_code=" + StringUtils.trimToEmpty(insurance_code)
                + "&policy_no=" + StringUtils.trimToEmpty(policy_no)
                + "&store_name=" + StringUtils.trimToEmpty(store_name)
                + "&store_addr=" + StringUtils.trimToEmpty(store_addr)
                + "&total_premium=" + StringUtils.trimToEmpty(total_premium)
                + "&premium=" + StringUtils.trimToEmpty(premium)
                + "&policy_holder=" + StringUtils.trimToEmpty(policy_holder)
                + "&insured_person=" + StringUtils.trimToEmpty(insured_person)
                + "&phone=" + StringUtils.trimToEmpty(phone)
                + "&licence_no=" + StringUtils.trimToEmpty(licence_no)
                + "&effective_date=" + StringUtils.trimToEmpty(effective_date)
                + "&expiry_date=" + StringUtils.trimToEmpty(expiry_date);
    }

    public String getSerialno() {
        return serialno;
    }
    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }
    public String getSign_type() {
        return sign_type;
    }
    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getInsurance_code() {
        return insurance_code;
    }
    public void setInsurance_code(String insurance_code) {
        this.insurance_code = insurance_code;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getPolicy_no() {
        return policy_no;
    }

    public void setPolicy_no(String policy_no) {
        this.policy_no = policy_no;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_addr() {
        return store_addr;
    }

    public void setStore_addr(String store_addr) {
        this.store_addr = store_addr;
    }

    public String getTotal_premium() {
        return total_premium;
    }

    public void setTotal_premium(String total_premium) {
        this.total_premium = total_premium;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getPolicy_holder() {
        return policy_holder;
    }

    public void setPolicy_holder(String policy_holder) {
        this.policy_holder = policy_holder;
    }

    public String getInsured_person() {
        return insured_person;
    }

    public void setInsured_person(String insured_person) {
        this.insured_person = insured_person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicence_no() {
        return licence_no;
    }

    public void setLicence_no(String licence_no) {
        this.licence_no = licence_no;
    }

    public String getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(String effective_date) {
        this.effective_date = effective_date;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

}
