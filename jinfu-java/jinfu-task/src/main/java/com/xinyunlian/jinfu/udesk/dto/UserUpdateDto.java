package com.xinyunlian.jinfu.udesk.dto;

/**
 * Created by King on 2017/4/14.
 */
public class UserUpdateDto {
    private CustomerUpdateDto customer;

    private String type;

    private String content;

    private String email;

    private String timestamp;

    private String sign;

    public CustomerUpdateDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerUpdateDto customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
