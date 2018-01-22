package com.xinyunlian.jinfu.udesk.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2017/4/11.
 */
public class UserImportDto {
    private List<CustomerDto> customers = new ArrayList<>();

    private String email;

    private String timestamp;

    private String sign;

    public List<CustomerDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerDto> customers) {
        this.customers = customers;
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
