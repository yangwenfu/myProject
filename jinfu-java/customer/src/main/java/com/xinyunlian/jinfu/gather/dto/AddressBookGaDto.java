package com.xinyunlian.jinfu.gather.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/3/15/0015.
 */
public class AddressBookGaDto implements Serializable {
    private static final long serialVersionUID = -4467141937988393695L;

    private String id;

    private String username;

    private String phoneNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

}
