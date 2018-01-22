package com.xinyunlian.jinfu.contract.dto;
import java.io.Serializable;
/**
 * Created by dongfangchao on 2017/2/8/0008.
 */
public class BsSendUser implements Serializable{
    private static final long serialVersionUID = 5422089004008954086L;
    private String email;
    private String emailcontent;
    private String name;
    private String mobile;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmailcontent() {
        return emailcontent;
    }
    public void setEmailcontent(String emailcontent) {
        this.emailcontent = emailcontent;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}