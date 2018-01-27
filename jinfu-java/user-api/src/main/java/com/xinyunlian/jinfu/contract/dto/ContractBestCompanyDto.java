package com.xinyunlian.jinfu.contract.dto;

import java.io.Serializable;

/**
 * Created by jll on 2017/2/8/0008.
 */
public class ContractBestCompanyDto implements Serializable {
    private static final long serialVersionUID = -1437359567370165022L;

    private String name;

    private String email;

    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}