package com.xinyunlian.jinfu.contract.dto;
import java.io.Serializable;
/**
 * Created by dongfangchao on 2017/2/7/0007.
 */
public class BsRegUserReq implements Serializable {
    private static final long serialVersionUID = 431089375425870994L;
    private String email;
    private String mobile;
    private String name;
    private String userType;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
}