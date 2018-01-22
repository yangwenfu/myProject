package com.xinyunlian.jinfu.api.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月23日.
 */
public class ApiUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String error;

    private String msg;

    private String mobile;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
