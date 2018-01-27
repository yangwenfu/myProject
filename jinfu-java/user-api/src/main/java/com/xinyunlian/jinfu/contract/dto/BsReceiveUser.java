package com.xinyunlian.jinfu.contract.dto;
import java.io.Serializable;
/**
 * Created by dongfangchao on 2017/2/8/0008.
 */
public class BsReceiveUser implements Serializable {
    private static final long serialVersionUID = 1613205252674756075L;

    private String email;

    private String name;

    private String mobile;

    private Boolean personal = true;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getPersonal() {
        return personal;
    }

    public void setPersonal(Boolean personal) {
        this.personal = personal;
    }
}