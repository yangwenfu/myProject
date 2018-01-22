package com.xinyunlian.jinfu.dto;

import java.io.Serializable;

/**
 * @author willwang
 */
public class RealAuthResponseDto implements Serializable {

    private Boolean pass;

    private String message;

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RealAuthResponseDto{" +
                "pass=" + pass +
                ", message='" + message + '\'' +
                '}';
    }
}
